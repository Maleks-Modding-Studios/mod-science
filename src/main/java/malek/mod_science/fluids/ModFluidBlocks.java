package malek.mod_science.fluids;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.Material;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static malek.mod_science.ModScience.MOD_ID;
import static malek.mod_science.fluids.ModFluids.*;

public class ModFluidBlocks {

    public static FluidBlock OIL;
    public static FluidBlock REWATER;
    public static FluidBlock ENDER_DEW;
    public static FluidBlock GLIMMER;
    public static FluidBlock WYLD_WATER;
    public static FluidBlock MAGNETICITE;

    public static void init() {
        REWATER =  Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "rewater"), new FluidBlock(STILL_REWATER, FabricBlockSettings.copy(Blocks.WATER)){});
        ENDER_DEW = Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "ender_dew"), new FluidBlock(STILL_ENDER_DEW, FabricBlockSettings.copy(Blocks.WATER)){});
        GLIMMER = Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "glimmer"), new FluidBlock(STILL_GLIMMER, FabricBlockSettings.of(Material.WATER).noCollision().strength(100.0F).dropsNothing().luminance(14)){});
        WYLD_WATER = Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "wyld_water"), new FluidBlock(STILL_WYLD_WATER, FabricBlockSettings.of(Material.WATER).noCollision().strength(100.0F).dropsNothing().luminance(5)){});
        OIL = Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "oil_water"), new FluidBlock(STILL_OIL, FabricBlockSettings.of(Material.WATER).noCollision().strength(100.0F).dropsNothing().luminance(0)){});
        MAGNETICITE = Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "magneticite"), new FluidBlock(STILL_MAGNETICITE, FabricBlockSettings.of(Material.WATER).noCollision().strength(100.0F).dropsNothing().luminance(0)){});
    }
}
