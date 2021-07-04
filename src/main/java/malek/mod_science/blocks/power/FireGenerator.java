package malek.mod_science.blocks.power;

import malek.mod_science.power.IPowerBlock;
import malek.mod_science.power.PowerBlockType;
import net.minecraft.block.Block;

public class FireGenerator extends Block implements IPowerBlock {
    public FireGenerator(Settings settings) {
        super(settings);
    }


    @Override
    public PowerBlockType getPowerType() {
        return PowerBlockType.GENERATOR;
    }
}
