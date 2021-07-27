package malek.mod_science.mixin;


import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import malek.mod_science.tags.ModScienceTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.fluid.Fluid;
import net.minecraft.tag.FluidTags;
import net.minecraft.tag.Tag;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityTickMixin {

    @Shadow
    public abstract World getEntityWorld();

    @Shadow
    public abstract boolean isInLava();

    @Shadow
    public abstract int getId();

    @Shadow
    public abstract void setGlowing(boolean glowing);


    @Shadow
    protected boolean firstUpdate;

    @Shadow
    protected Object2DoubleMap<Tag<Fluid>> fluidHeight;

    @Inject(method = "baseTick", at = @At("TAIL"), cancellable = true)
    public void setGlowing(CallbackInfo ci) {
        World world = this.getEntityWorld();
        Entity entity = this.getEntityWorld().getEntityById(this.getId());
        if (!world.isClient) {
            if (!this.firstUpdate && this.fluidHeight.getDouble(ModScienceTags.GLIMMER) > 0.0D) {
                ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 600, 0));
            }
        }
    }
}
