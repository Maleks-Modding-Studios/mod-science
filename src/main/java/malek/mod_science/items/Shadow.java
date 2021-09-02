package malek.mod_science.items;

import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;

public class Shadow extends BucketItem {
    public static Fluid fluid;
    public Shadow(Fluid fluid, Settings settings) {
        super(fluid, settings);
        this.fluid = fluid;
    }
}
