package malek.mod_science.fluids;

import malek.mod_science.fluids.ender_dew.EnderDew;
import malek.mod_science.fluids.rewater.Rewater;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static malek.mod_science.ModScience.MOD_ID;
import static malek.mod_science.ModScience.ModScienceId;

public class ModFluids {
    public static Rewater STILL_REWATER;
    public static Rewater FLOWING_REWATER;

    public static EnderDew STILL_ENDER_DEW;
    public static EnderDew FLOWING_ENDER_DEW;

    public static void init() {
        FLOWING_REWATER = Registry.register(Registry.FLUID, new Identifier(MOD_ID, "flowing_rewater"), new Rewater.Flowing());
        STILL_REWATER = Registry.register(Registry.FLUID, new Identifier(MOD_ID, "rewater"), new Rewater.Still());

        FLOWING_ENDER_DEW = Registry.register(Registry.FLUID, ModScienceId("flowing_ender_dew"), new EnderDew.Flowing());
        STILL_ENDER_DEW = Registry.register(Registry.FLUID, ModScienceId("still_ender_dew"), new EnderDew.Still());

    }
}
