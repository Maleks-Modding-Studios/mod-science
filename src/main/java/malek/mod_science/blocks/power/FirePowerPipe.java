package malek.mod_science.blocks.power;

import malek.mod_science.power.FirePowerCarrier;
import net.minecraft.block.Block;

public class FirePowerPipe extends Block implements FirePowerCarrier {

    public FirePowerPipe(Settings settings) {
        super(settings);
    }

    @Override
    public double getFireEfficiency() {
        return 0;
    }
}
