package malek.mod_science.power;

import malek.mod_science.blocks.power.Efficiency;
import net.minecraft.util.math.BlockPos;

public interface IPowerCarrier {
    Efficiency getFireEfficiency();
    Efficiency getLightEfficiency();
    Efficiency getArcEfficiency();
    Efficiency getTimeEfficiency();
    Efficiency getFluidEfficiency();
    Efficiency getSapEfficiency();
}
