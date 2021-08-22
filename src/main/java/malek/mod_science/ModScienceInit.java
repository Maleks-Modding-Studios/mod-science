package malek.mod_science;

import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import malek.mod_science.worlds.biomes.ModBiomes;
import malek.mod_science.blocks.ModBlocks;
import malek.mod_science.blocks.ModBlockEntities;
import malek.mod_science.client.commands.ModCommands;
import malek.mod_science.components.player.timepiece.TimePieceComponent;
import malek.mod_science.components.player.timepiece.TimePieceComponentImpl;
import malek.mod_science.worlds.dimensions.AbyssDimension;
import malek.mod_science.worlds.dimensions.LSpaceDimension;
import malek.mod_science.entities.clank.Clanks;
import malek.mod_science.worlds.dimensions.WyldsDimension;
import malek.mod_science.recipes.ModRecipes;
import malek.mod_science.worlds.dimensions.TheRoomDimension;
import malek.mod_science.statuseffects.ModStatusEffects;
import malek.mod_science.entities.ModEntities;
import malek.mod_science.fluids.ModBuckets;
import malek.mod_science.fluids.ModFluidBlocks;
import malek.mod_science.fluids.ModFluids;
import malek.mod_science.worlds.generation.ModGeneration;
import malek.mod_science.items.ModBlockItems;
import malek.mod_science.items.ModItems;
import malek.mod_science.screens.ModScreens;
import malek.mod_science.client.sounds.ModSounds;
import malek.mod_science.tags.ModScienceTags;
import malek.mod_science.util.general.LoggerInterface;
import malek.mod_science.util.general.MatterCavitationChamberScreenHandler;
import malek.mod_science.util.general.ModCompatibility;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.fabricmc.loader.api.FabricLoader;
//import net.malek.fluidinteractionapiluid .FluidInteractionGroup;
//import net.malek.fluidinteractionapi.FluidInteractionsRegistry;
//import net.malek.fluidinteractionapi.FluidSetBlockInteraction;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluids;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Supplier;

import static malek.mod_science.ModScience.MOD_ID;
import static malek.mod_science.blocks.ModBlockEntities.MATTER_CHAMBER;

public class ModScienceInit implements ModInitializer, LoggerInterface, EntityComponentInitializer {
    //Config Stuff
    private static final Supplier<Path> CONFIG_ROOT = () -> FabricLoader.getInstance().getConfigDir().resolve(MOD_ID).toAbsolutePath();
    private static final ConfigHolder<ModConfig> CONFIG_MANAGER = AutoConfig.register(ModConfig.class, ModConfig.SubRootJanksonConfigSerializer::new);
    public static final Set<ModCompatibility> MODS = new HashSet<>();
    public static final ScreenHandlerType<MatterCavitationChamberScreenHandler> MATTER_CAVITATION_CHAMBER_SCREEN;

    public static Path getConfigRoot() {
        return CONFIG_ROOT.get();
    }

    static {
        MATTER_CAVITATION_CHAMBER_SCREEN = ScreenHandlerRegistry.registerSimple(MATTER_CHAMBER, MatterCavitationChamberScreenHandler::new);
    }
//    public static final RegistryKey<DimensionOptions> DIMENSION_KEY = RegistryKey.of(
//            Registry.DIMENSION_KEY,
//            new Identifier(MOD_ID, "void")
//    );
//
//    public static RegistryKey<World> WORLD_KEY = RegistryKey.of(
//            Registry.WORLD_KEY,
//            DIMENSION_KEY.getValue()
//    );
//
//    public static final RegistryKey<DimensionType> DIMENSION_TYPE_KEY = RegistryKey.of(
//            Registry.DIMENSION_TYPE_KEY,
//            new Identifier(MOD_ID, "void_type")
//    );



    @Override
    public void onInitialize() {

        log("Initializing Mod Science. Have fun playing our mod!");
        log(getConfig().madness.lowMadness.thresholdAmount + " is the current Low Madness threshold amount");
        log(getConfig().madness.mediumMadness.thresholdAmount + " is the current Medium Madness threshold amount");
        ServerLifecycleEvents.SERVER_STARTING.register(ModScience::setServer);
        ServerLifecycleEvents.SERVER_STOPPED.register(ModScience::clearServer);

        new Thread(ModScienceInit::initModCompat).start();
        // Yes -Platy
        GeckoLib.initialize();
        ModItems.init();
        ModBlockEntities.init();
        ModBlocks.init();
        ModBlockItems.init();
        ModGeneration.init();
        ModEntities.init();
        ModStatusEffects.init();
        Clanks.init();





        //ModFluids
        ModFluids.init();
        ModFluidBlocks.init();
        ModBuckets.init();
        ModSounds.init();


        ModScreens.init();


        ModRecipes.init();


        ModScienceTags.init();
        ModBiomes.init();
        //IS GOOD
        TheRoomDimension.init();
        LSpaceDimension.init();
        WyldsDimension.init();
        AbyssDimension.init();
        ModCommands.init();
        //initFluidInteractions();
    }

    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(TimePieceComponent.entityKey, TimePieceComponentImpl::new, RespawnCopyStrategy.ALWAYS_COPY);
    }

    public static ModConfig getConfig() {
        return CONFIG_MANAGER.get();
    }

    public static void initModCompat() {
        LogManager.getLogger().log(Level.INFO, "Mod Science is Enabling Mod Compatibility");
        for (ModCompatibility mod : MODS) {
            if (FabricLoader.getInstance().isModLoaded(mod.getModID())) {
                mod.enable();
                LogManager.getLogger().log(Level.INFO, "Mod Science Enabling Mod Compatibility For : " + mod.getModID());
            }
        }
    }

//    public static void initFluidInteractions() {
//        ArrayList<BlockState> realityBlockStates = new ArrayList<>();
//        realityBlockStates.add(ModBlocks.REALITY_BLOCK_WHITE.getDefaultState());
//        realityBlockStates.add(ModBlocks.REALITY_BLOCK_GREEN.getDefaultState());
//        realityBlockStates.add(ModBlocks.REALITY_BLOCK_PURPLE.getDefaultState());
//        realityBlockStates.add(ModBlocks.REALITY_BLOCK_CYAN.getDefaultState());
//        realityBlockStates.add(ModBlocks.REALITY_BLOCK_ORANGE.getDefaultState());
//        realityBlockStates.add(ModBlocks.REALITY_BLOCK_YELLOW.getDefaultState());
//        realityBlockStates.add(ModBlocks.REALITY_BLOCK_RED.getDefaultState());
//        realityBlockStates.add(ModBlocks.REALITY_BLOCK_BLUE.getDefaultState());
////        FluidInteractionGroup flowingWater = new FluidSetBlockInteraction(Fluids.FLOWING_WATER, (world, pos) -> {
////            Random random = new Random();
////            int i = random.nextInt(7);
////            if(i == 0) {
////                ((World)world).setBlockState((BlockPos) pos, realityBlockStates.get(i));
////            }
////        });
////        FluidInteractionGroup stillWater = new FluidSetBlockInteraction(Fluids.WATER, (world, pos) -> {
////            Random random = new Random();
////            int i = random.nextInt(7);
////            if(i == 0) {
////                ((World)world).setBlockState((BlockPos) pos, realityBlockStates.get(i));
////            }
////        });
////
////        FluidInteractionGroup flowingLava = new FluidSetBlockInteraction(Fluids.FLOWING_LAVA, (world, pos) -> {
////            Random random = new Random();
////            int i = random.nextInt(7);
////            if(i == 0) {
////                ((World)world).setBlockState((BlockPos) pos, realityBlockStates.get(i));
////            }
////        });
////
////        FluidInteractionGroup stillLava = new FluidSetBlockInteraction(Fluids.LAVA, (world, pos) -> {
////            Random random = new Random();
////            int i = random.nextInt(7);
////            if(i == 0) {
////                ((World)world).setBlockState((BlockPos) pos, realityBlockStates.get(i));
////            }
////        });
//        FluidInteractionGroup flowingWater = new FluidSetBlockInteraction(Fluids.FLOWING_WATER, ModBlocks.REALITY_BLOCK_BLUE.getDefaultState());
//        FluidInteractionGroup stillWater = new FluidSetBlockInteraction(Fluids.WATER, ModBlocks.REALITY_BLOCK_BLUE.getDefaultState());
//        FluidInteractionGroup stillLava = new FluidSetBlockInteraction(Fluids.LAVA, ModBlocks.REALITY_BLOCK_BLUE.getDefaultState());
//        FluidInteractionGroup flowingLava = new FluidSetBlockInteraction(Fluids.FLOWING_LAVA, ModBlocks.REALITY_BLOCK_BLUE.getDefaultState());
//        FluidInteractionsRegistry.addInteraction(ModFluids.FLOWING_GLIMMER, stillWater);
//        FluidInteractionsRegistry.addInteraction(ModFluids.FLOWING_GLIMMER, flowingWater);
//        FluidInteractionsRegistry.addInteraction(ModFluids.FLOWING_GLIMMER, stillLava);
//        FluidInteractionsRegistry.addInteraction(ModFluids.FLOWING_GLIMMER, flowingLava);
//
//        FluidInteractionsRegistry.addInteraction(ModFluids.STILL_GLIMMER, stillWater);
//        FluidInteractionsRegistry.addInteraction(ModFluids.STILL_GLIMMER, flowingWater);
//        FluidInteractionsRegistry.addInteraction(ModFluids.STILL_GLIMMER, stillLava);
//        FluidInteractionsRegistry.addInteraction(ModFluids.STILL_GLIMMER, flowingLava);
//
//
//        FluidInteractionsRegistry.addInteraction(ModFluids.FLOWING_REWATER, stillWater);
//        FluidInteractionsRegistry.addInteraction(ModFluids.FLOWING_REWATER, flowingWater);
//        FluidInteractionsRegistry.addInteraction(ModFluids.FLOWING_REWATER, stillLava);
//        FluidInteractionsRegistry.addInteraction(ModFluids.FLOWING_REWATER, flowingLava);
//
//        FluidInteractionsRegistry.addInteraction(ModFluids.STILL_REWATER, stillWater);
//        FluidInteractionsRegistry.addInteraction(ModFluids.STILL_REWATER, flowingWater);
//        FluidInteractionsRegistry.addInteraction(ModFluids.STILL_REWATER, stillLava);
//        FluidInteractionsRegistry.addInteraction(ModFluids.STILL_REWATER, flowingLava);
//
//
//        FluidInteractionsRegistry.addInteraction(ModFluids.FLOWING_ENDER_DEW, stillWater);
//        FluidInteractionsRegistry.addInteraction(ModFluids.FLOWING_ENDER_DEW, flowingWater);
//        FluidInteractionsRegistry.addInteraction(ModFluids.FLOWING_ENDER_DEW, stillLava);
//        FluidInteractionsRegistry.addInteraction(ModFluids.FLOWING_ENDER_DEW, flowingLava);
//
//        FluidInteractionsRegistry.addInteraction(ModFluids.STILL_ENDER_DEW, stillWater);
//        FluidInteractionsRegistry.addInteraction(ModFluids.STILL_ENDER_DEW, flowingWater);
//        FluidInteractionsRegistry.addInteraction(ModFluids.STILL_ENDER_DEW, stillLava);
//        FluidInteractionsRegistry.addInteraction(ModFluids.STILL_ENDER_DEW, flowingLava);
//
//
//        FluidInteractionsRegistry.addInteraction(ModFluids.FLOWING_WYLD_WATER, stillWater);
//        FluidInteractionsRegistry.addInteraction(ModFluids.FLOWING_WYLD_WATER, flowingWater);
//        FluidInteractionsRegistry.addInteraction(ModFluids.FLOWING_WYLD_WATER, stillLava);
//        FluidInteractionsRegistry.addInteraction(ModFluids.FLOWING_WYLD_WATER, flowingLava);
//
//        FluidInteractionsRegistry.addInteraction(ModFluids.STILL_WYLD_WATER, stillWater);
//        FluidInteractionsRegistry.addInteraction(ModFluids.STILL_WYLD_WATER, flowingWater);
//        FluidInteractionsRegistry.addInteraction(ModFluids.STILL_WYLD_WATER, stillLava);
//        FluidInteractionsRegistry.addInteraction(ModFluids.STILL_WYLD_WATER, flowingLava);
//    }

    @Override
    public Logger getLogger() {
        return LogManager.getLogger();
    }
}
