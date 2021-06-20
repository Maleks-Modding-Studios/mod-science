package malek.mod_science.mixin;

import com.mojang.authlib.GameProfile;
import malek.mod_science.components.player.madness.Madness;
import malek.mod_science.components.player.madness.Whispers;
import malek.mod_science.util.general.LoggerInterface;
import malek.mod_science.util.general.MixinUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.MessageType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
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

    @Inject(method = "tick", at = @At("HEAD"))
    public void tickMixin(CallbackInfo ci) {
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
    }
}
