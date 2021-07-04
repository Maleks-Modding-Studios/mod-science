package malek.mod_science.power;

import net.minecraft.util.math.BlockPos;

public interface IPowerCarrier {
    double getFireEfficiency();
    double getLightEfficiency();
    double getArcEfficiency();
    double getTimeEfficiency();
}
