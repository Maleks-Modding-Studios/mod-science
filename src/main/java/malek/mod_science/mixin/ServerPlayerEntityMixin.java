package malek.mod_science.mixin;

import com.mojang.authlib.GameProfile;
import malek.mod_science.components.player.madness.Madness;
import malek.mod_science.components.player.madness.Whispers;
import malek.mod_science.components.player.timeout.Timeout;
import malek.mod_science.dimensions.LSpaceDimension;
import malek.mod_science.dimensions.TheRoomDimension;
import malek.mod_science.items.ModItems;
import malek.mod_science.sounds.ModSounds;
import malek.mod_science.util.general.LoggerInterface;
import malek.mod_science.util.general.MixinUtil;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.MessageType;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity implements LoggerInterface {
    @Shadow
    public abstract ServerWorld getServerWorld();



    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
        super(world, pos, yaw, profile);
    }

    boolean play = false;
    long time = System.currentTimeMillis();
    private static int CHECK = 10;
    int check = 0;
    boolean playedSound = false;
    long musicTime = System.currentTimeMillis();



    @Inject(method = "tick", at = @At("HEAD"))
    public void tickMixin(CallbackInfo ci) {
        ServerPlayerEntity player = MixinUtil.cast(this);
        if (world.getRegistryKey().equals(LSpaceDimension.WORLD_KEY)) {
            player.setNoGravity(true);
            player.setOnGround(true);
            //player.setSwimming(true);
            //player.setPose(EntityPose.SWIMMING);
        }
        if (this.getMainHandStack().isOf(ModItems.DARKWYN_INGOT)) {
            this.setFireTicks(1);//no touchy
        }
        if (Madness.get(MixinUtil.cast(this)).isLow() && random.nextFloat() <= Madness.getConfig().lowMadness.randomSaturationGain.chance) {
            this.getHungerManager().add(Madness.getConfig().lowMadness.randomSaturationGain.saturationAmount + 1, 1.0F);
        }

        if (Madness.get(MixinUtil.cast(this)).isMedium()) {
            Box whisperRadius = new Box(this.getX() - 5, this.getY() - 5, this.getZ() - 5, this.getX() + 5, this.getY() + 5, this.getZ() + 5);
            // Ignore the MixinUtil.cast used here. With God as my witness I shall commit terrible sins.
            List<ServerPlayerEntity> eavesdroppingPlayers = MixinUtil.cast(this.getServerWorld().getOtherEntities(null, whisperRadius, e -> e instanceof ServerPlayerEntity && e != this));

            for (ServerPlayerEntity entity : eavesdroppingPlayers) {
                if (this.random.nextInt(10000) == 0) {
                    entity.sendMessage(Whispers.getWhisperText(this.random.nextInt(4), MixinUtil.cast(this), this.getMainHandStack()), MessageType.CHAT, entity.getUuid());
                }
            }
        }
        if (getServerWorld().getRegistryKey().equals(TheRoomDimension.WORLD_KEY)) {
            ServerPlayerEntity playerEntity = (ServerPlayerEntity) (Object) this;
            playerEntity.getAbilities().invulnerable = true;
            playerEntity.sendAbilitiesUpdate();
            playerEntity.getServer().getPlayerManager().sendToAll(new PlayerListS2CPacket(PlayerListS2CPacket.Action.UPDATE_GAME_MODE, new ServerPlayerEntity[]{playerEntity}));
            check++;
            if (check == CHECK) {
                if (!playedSound && play) {
                    if (Timeout.TIMEOUT.get(playerEntity).isTimeOutOver()) {
                        getServerWorld().playSound(null, playerEntity.getBlockPos(), ModSounds.ELEVATOR_DING, SoundCategory.MASTER, 0.9F, 1f);
                        playedSound = true;
                    }
                }
                if (System.currentTimeMillis() - musicTime > 260000 || !play) {
                    getServerWorld().playSound(null, playerEntity.getBlockPos(), ModSounds.ELEVATOR_MUSIC, SoundCategory.MASTER, 0.2F, 1f);
                    play = true;
                    musicTime = System.currentTimeMillis();
                    getServerWorld().playSound(null, playerEntity.getBlockPos(), ModSounds.ELEVATOR_DING, SoundCategory.MASTER, 0.5F, 1f);
                }
                check = 0;
            }
        }else{
            ServerPlayerEntity playerEntity = (ServerPlayerEntity) (Object) this;
            playerEntity.getAbilities().invulnerable = false;
            playerEntity.sendAbilitiesUpdate();
            playerEntity.getServer().getPlayerManager().sendToAll(new PlayerListS2CPacket(PlayerListS2CPacket.Action.UPDATE_GAME_MODE, new ServerPlayerEntity[]{playerEntity}));

            playerEntity.getAbilities().invulnerable = true;
            playerEntity.sendAbilitiesUpdate();

        }

    }
    public int getSign(int value) {
        return value < 0 ? -1 : 1;
    }
    @Inject(method = "teleport", at = @At("HEAD"), cancellable = true)
    public void teleportMixin(ServerWorld targetWorld, double x, double y, double z, float yaw, float pitch, CallbackInfo ci) {
        ServerPlayerEntity player = MixinUtil.cast(this);
        if (player.getServerWorld().getRegistryKey() == TheRoomDimension.WORLD_KEY) {
            if (Timeout.TIMEOUT.get(player).isTimeOutOver()) {
                time = System.currentTimeMillis();
                Timeout.TIMEOUT.get(player).setTimeOut(0);
                playedSound = false;
                play = false;
            } else {
                ci.cancel();
            }
        }
        if(targetWorld.getRegistryKey() == TheRoomDimension.WORLD_KEY) {
            time = System.currentTimeMillis();
            musicTime = System.currentTimeMillis();
        }


    }

    private boolean isTimeToLetOut(ServerPlayerEntity player) {
        return Timeout.TIMEOUT.get(player).getTimeOut() * 1000 <= System.currentTimeMillis() - time;
    }
}
