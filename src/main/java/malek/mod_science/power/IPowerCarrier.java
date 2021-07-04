package malek.mod_science.power;

import net.minecraft.util.math.BlockPos;

public interface IPowerCarrier {
    int getFireEfficiency();
    int getLightEfficiency();
    int getArcEfficiency();
    int getTimeEfficiency();
}
