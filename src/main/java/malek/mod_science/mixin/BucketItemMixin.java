package malek.mod_science.mixin;

import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BucketItem.class)
public interface BucketItemMixin {
    @Accessor
    Fluid getFluid();
//doing a refactor of the codebase... W H Y ?
}
