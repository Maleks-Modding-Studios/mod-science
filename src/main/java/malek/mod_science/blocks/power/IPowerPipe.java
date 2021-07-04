package malek.mod_science.blocks.power;

import malek.mod_science.power.IPowerCarrier;
import malek.mod_science.power.IPowerBlock;
import malek.mod_science.power.PowerBlockType;
import net.minecraft.block.Block;

public class IPowerPipe extends Block implements IPowerBlock, IPowerCarrier {

    public IPowerPipe(Settings settings) {
        super(settings);
    }

    @Override
    public double getFireEfficiency() {
        return 1;
    }

    @Override
    public double getLightEfficiency() {
        return 1;
    }

    @Override
    public double getArcEfficiency() {
        return 1;
    }

    @Override
    public double getTimeEfficiency() {
        return 1;
    }

    @Override
    public PowerBlockType getPowerType() {
        return PowerBlockType.PIPE;
    }
}
