package malek.mod_science.fluids;

import malek.mod_science.fluids.ender_dew.EnderDew;
import malek.mod_science.fluids.glimmer.Glimmer;
import malek.mod_science.fluids.magneticite.Magneticite;
import malek.mod_science.fluids.oil.Oil;
import malek.mod_science.fluids.rewater.Rewater;
import malek.mod_science.fluids.wyldwater.WyldWater;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static malek.mod_science.ModScience.MOD_ID;
import static malek.mod_science.ModScience.ModScienceId;

public class ModFluids {
    public static Oil FLOWING_OIL;
    public static Oil STILL_OIL;

    public static Rewater STILL_REWATER;
    public static Rewater FLOWING_REWATER;

    public static EnderDew STILL_ENDER_DEW;
    public static EnderDew FLOWING_ENDER_DEW;

    public static Glimmer STILL_GLIMMER;
    public static Glimmer FLOWING_GLIMMER;

    public static WyldWater STILL_WYLD_WATER;
    public static WyldWater FLOWING_WYLD_WATER;

    public static Magneticite STILL_MAGNETICITE;
    public static Magneticite FLOWING_MAGNETICITE;

    public static void init() {
        FLOWING_REWATER = Registry.register(Registry.FLUID, new Identifier(MOD_ID, "flowing_rewater"), new Rewater.Flowing());
        STILL_REWATER = Registry.register(Registry.FLUID, new Identifier(MOD_ID, "rewater"), new Rewater.Still());

        FLOWING_ENDER_DEW = Registry.register(Registry.FLUID, ModScienceId("flowing_ender_dew"), new EnderDew.Flowing());
        STILL_ENDER_DEW = Registry.register(Registry.FLUID, ModScienceId("still_ender_dew"), new EnderDew.Still());

        FLOWING_GLIMMER = Registry.register(Registry.FLUID, ModScienceId("flowing_glimmer"), new Glimmer.Flowing());
        STILL_GLIMMER = Registry.register(Registry.FLUID, ModScienceId("still_glimmer"), new Glimmer.Still());

        STILL_WYLD_WATER = Registry.register(Registry.FLUID, ModScienceId("still_wyld_water"), new WyldWater.Still());
        FLOWING_WYLD_WATER = Registry.register(Registry.FLUID, ModScienceId("flowing_wyld_water"), new WyldWater.Flowing());

        STILL_OIL = Registry.register(Registry.FLUID, ModScienceId("still_oil"), new Oil.Still());
        FLOWING_OIL = Registry.register(Registry.FLUID, ModScienceId("flowing_oil"), new Oil.Flowing());

        STILL_MAGNETICITE = Registry.register(Registry.FLUID, ModScienceId("still_magneticite"), new Magneticite.Still());
        FLOWING_MAGNETICITE = Registry.register(Registry.FLUID, ModScienceId("flowing_magneticite"), new Magneticite.Flowing());
    }
}
