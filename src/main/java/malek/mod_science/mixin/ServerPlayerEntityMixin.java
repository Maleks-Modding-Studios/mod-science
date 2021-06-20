package malek.mod_science.mixin;

import com.mojang.authlib.GameProfile;
import malek.mod_science.components.player.madness.Madness;
import malek.mod_science.util.general.LoggerInterface;
import malek.mod_science.util.general.PlayerUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.MessageType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
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

import java.util.ArrayList;
import java.util.List;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity implements LoggerInterface {
    @Shadow
    public abstract ServerWorld getServerWorld();

    @Shadow
    public abstract void sendMessage(Text message, boolean actionBar);



    private static List<Text> whisperText = new ArrayList<>();

    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
        super(world, pos, yaw, profile);
        whisperText.add(new LiteralText(this.getDisplayName().asString() + " seems lost in thought"));
        whisperText.add(new LiteralText(this.getDisplayName().asString() + " cackled quietly"));
        whisperText.add(new LiteralText(this.getDisplayName().asString() + " is fiddling with their " + this.getMainHandStack().getName()));
        whisperText.add(new LiteralText(this.getDisplayName().asString() + "is muttering something under their breath"));
    }





    @Inject(method = "tick", at = @At("HEAD"))
    public void tickMixin(CallbackInfo ci) {

        //log(PlayerUtil.getPlayerFromUuid(((ServerPlayerEntity) (Object) this).getUuid()).getX() + "");
        if (Madness.get((ServerPlayerEntity) (Object) this).isLow() && random.nextFloat() <= Madness.getConfig().lowMadness.randomSaturationGain.chance) {
            ((PlayerEntity) this.getCameraEntity()).getHungerManager().add(
                    Madness.getConfig().lowMadness.randomSaturationGain.saturationAmount + 1,
                    1.0F
            );
        }
        int playerGetter;
        int stringSelector;
        if (Madness.get((ServerPlayerEntity) (Object) this).isMedium()){
            Box ServerPlayerInRadius = new Box(this.getX()-5,this.getY()-5, this.getZ()-5, this.getX()+5, this.getY()+5, this.getZ()+5);
            List<Entity> playersToChat = this.getServerWorld().getOtherEntities(null, ServerPlayerInRadius, (entity) -> entity instanceof PlayerEntity);

            for (Entity entity : playersToChat) {
                playerGetter = ((int) (Math.random() * 0 - 100));
                stringSelector = ((int) (Math.random() * 0 - 4));
                if (playerGetter == 5) {
                    this.getServer().getPlayerManager().broadcastChatMessage(whisperText.get(stringSelector), MessageType.CHAT, entity.getUuid());

                }
            }
        }


    }

    @Override
    public Logger getLogger() {
        return LogManager.getLogger();
    }

    @Shadow
    public abstract Entity getCameraEntity();
}
