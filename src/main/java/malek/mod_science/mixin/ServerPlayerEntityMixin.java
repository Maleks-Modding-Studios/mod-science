package malek.mod_science.mixin;

import com.mojang.authlib.GameProfile;
import malek.mod_science.components.player.madness.Madness;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {
    @Shadow
    private Entity cameraEntity;

    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
        super(world, pos, yaw, profile);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tickMixin(CallbackInfo ci) {


        if (Madness.get((ServerPlayerEntity) (Object) this).isLow() && random.nextFloat() <= Madness.getConfig().lowMadness.randomSaturationGain.chance) {
            ((PlayerEntity) this.getCameraEntity()).getHungerManager().add(
                    Madness.getConfig().lowMadness.randomSaturationGain.saturationAmount + 1,
                    1.0F
            );
        }

    }

    @Shadow
    public abstract Entity getCameraEntity();
}
