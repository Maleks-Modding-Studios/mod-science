package malek.mod_science.mixin;
import malek.mod_science.util.general.LoggerInterface;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobEntity.class)
public class MobEntityMixin {
    @Shadow
    @Nullable
    private Entity holdingEntity;

    @Inject(method = "detachLeash", at = @At("HEAD"))
    public void thingyMixin(CallbackInfo info){
        assert this.holdingEntity==null;
    }
}
