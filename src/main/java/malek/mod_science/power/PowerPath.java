package malek.mod_science.power;

import net.minecraft.util.math.BlockPos;

public class PowerPath {
    int lowEff = 0;
    int mediumEff = 0;
    int highEff = 0;
    public BlockPos currentPos;
    public PowerPath(BlockPos currentPos) {
        this.currentPos = currentPos;
    }
    PowerPath(BlockPos pos, PowerPath path) {
        this.currentPos = pos;
        this.lowEff = path.lowEff;
        this.mediumEff = path.mediumEff;
        this.highEff = path.highEff;
    }
    public PowerPath copy(){
        PowerPath clone = new PowerPath(this.currentPos);
        clone.lowEff = lowEff;
        clone.mediumEff = mediumEff;
        clone.highEff = highEff;
        return clone;
    }
}
