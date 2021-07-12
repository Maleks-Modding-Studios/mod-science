package malek.mod_science.blocks.blockentities;

import alexiil.mc.lib.attributes.fluid.FixedFluidInv;

public interface FluidInvGetter {
    FixedFluidInv getFluidInv();
    void markDirty();
}
