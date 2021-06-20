package malek.mod_science.mixin;

import net.minecraft.entity.passive.TameableEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TameableEntity.class)
public class TameableEntityMixin {

    @Inject(method = "canBeLeashedBy", at = @At("HEAD"), cancellable = true)
    public void canBeLeashedByMixin(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }
}
