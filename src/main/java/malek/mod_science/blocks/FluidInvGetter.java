package malek.mod_science.blocks;

import alexiil.mc.lib.attributes.fluid.FluidVolumeUtil;
import alexiil.mc.lib.attributes.fluid.amount.FluidAmount;
import alexiil.mc.lib.attributes.fluid.impl.SimpleFixedFluidInv;
import alexiil.mc.lib.attributes.fluid.volume.FluidVolume;
import malek.mod_science.power.PowerPath;
import net.minecraft.world.World;

import java.util.Set;

public interface FluidInvGetter {
    SimpleFixedFluidInv getFluidInv();
    Set<PowerPath> getPowerPaths();
    FluidAmount getTransferRate();
    World getWorld();
    boolean wantsPower();
    default void tryPowerTransfer() {
        if(wantsPower()) {
            for (PowerPath path : getPowerPaths()) {
                if(path.fluidEfficiency.canCarry()) {
                    if(getWorld().getBlockEntity(path.currentPos) instanceof FluidInvGetter fluidBlockEntity) {
                        for(int i = 0; i < fluidBlockEntity.getFluidInv().getTankCount(); i++) {
                            FluidVolume movedCopy = FluidVolumeUtil.move(fluidBlockEntity.getFluidInv().getExtractable(), getFluidInv(), FluidAmount.of(1, 20));
                            if(!movedCopy.isEmpty()) {
                                fluidBlockEntity.markDirty();
                                markDirty();
                            }
                        }
                    }
                }
            }
        }
    }
    void markDirty();
}
