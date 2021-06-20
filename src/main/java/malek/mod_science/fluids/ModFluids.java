package malek.mod_science.fluids;

import malek.mod_science.fluids.Rewater.Rewater;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static malek.mod_science.ModScience.MOD_ID;

public class ModFluids {
    public static Rewater STILL_REWATER;
    public static Rewater FLOWING_REWATER;
    public static void init() {
        FLOWING_REWATER = Registry.register(Registry.FLUID, new Identifier(MOD_ID, "flowing_rewater"), new Rewater.Flowing());
        STILL_REWATER = Registry.register(Registry.FLUID, new Identifier(MOD_ID, "rewater"), new Rewater.Still());

    }
}
