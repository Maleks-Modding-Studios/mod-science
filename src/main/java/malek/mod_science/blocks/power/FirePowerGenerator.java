package malek.mod_science.blocks.power;

import malek.mod_science.power.FirePowerPath;
import malek.mod_science.power.IFirePowerGenerator;
import net.minecraft.block.Block;

import java.util.Set;

public class FirePowerGenerator extends Block implements IFirePowerGenerator {
    public FirePowerGenerator(Settings settings) {
        super(settings);
    }

    @Override
    public Set<FirePowerPath> getFirePowerPaths() {
        return null;
    }

    @Override
    public double getFireLevel() {
        return 0;
    }

    @Override
    public double getFireMaxLevel() {
        return 0;
    }

    @Override
    public double getFireEfficiency() {
        return 0;
    }
}
