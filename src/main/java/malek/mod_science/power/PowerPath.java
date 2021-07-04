package malek.mod_science.power;

import net.minecraft.util.math.BlockPos;

public class PowerPath {
    public BlockPos currentPos;
    public PowerEff fireEfficiency = new PowerEff();
    public PowerEff lightEfficiency = new PowerEff();
    public PowerEff arcEfficiency = new PowerEff();
    public PowerEff timeEfficiency = new PowerEff();
    public PowerPath(BlockPos currentPos) {
        this.currentPos = currentPos;
    }
    PowerPath(BlockPos pos, PowerPath path) {
        this.currentPos = pos;
        fireEfficiency = new PowerEff(path.fireEfficiency);
        lightEfficiency = new PowerEff(path.lightEfficiency);
        arcEfficiency = new PowerEff(path.arcEfficiency);
        timeEfficiency = new PowerEff(path.timeEfficiency);
    }
    @Override
    public String toString() {
        return "Position : "+ this.currentPos.toString() + " fireEff : [" + fireEfficiency.toString() + "], " + " lightEff : [" +lightEfficiency.toString() + "], "
                + " arcEff : [" + arcEfficiency.toString() + "], " + " timeEff : [" + timeEfficiency.toString() + "]";
    }
}
