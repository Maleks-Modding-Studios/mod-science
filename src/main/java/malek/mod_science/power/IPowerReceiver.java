package malek.mod_science.power;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IPowerReceiver extends IPower {

    void markNetworkDirty();
    boolean isNetworkDirty();
    void findPaths();
    boolean wantsPower();
}
