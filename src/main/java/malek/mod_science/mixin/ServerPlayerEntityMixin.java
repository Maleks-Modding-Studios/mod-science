package malek.mod_science.mixin;

import malek.mod_science.ModScienceInit;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {
    @Shadow
    private Entity cameraEntity;
    private Random random = new Random();

    @Inject(method = "tick", at = @At("HEAD"))
    public void tickMixin(CallbackInfo ci) {

        int madnessSaturationTick = 0;
        if (random.nextFloat() <= ModScienceInit.getConfig().madness.lowMadness.randomSaturationGain.chance) {
            ((PlayerEntity) this.getCameraEntity()).getHungerManager().add(ModScienceInit
                                                                                   .getConfig().madness.lowMadness.randomSaturationGain.saturationAmount +
                                                                           1, 1.0F);
        }

    }

    @Shadow
    public abstract Entity getCameraEntity();
}
