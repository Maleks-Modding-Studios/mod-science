package malek.mod_science.power;

import net.minecraft.util.math.BlockPos;

import java.util.HashSet;
import java.util.Set;

public class FirePowerPath {
    int lowEff = 0;
    int mediumEff = 0;
    int highEff = 0;
    public BlockPos currentPos;
    public FirePowerPath(BlockPos currentPos) {
        this.currentPos = currentPos;
    }
    FirePowerPath(BlockPos pos, FirePowerPath path) {
        this.currentPos = pos;
        this.lowEff = path.lowEff;
        this.mediumEff = path.mediumEff;
        this.highEff = path.highEff;
    }
    public FirePowerPath copy(){
        FirePowerPath clone = new FirePowerPath(this.currentPos);
        clone.lowEff = lowEff;
        clone.mediumEff = mediumEff;
        clone.highEff = highEff;
        return clone;
    }
}
