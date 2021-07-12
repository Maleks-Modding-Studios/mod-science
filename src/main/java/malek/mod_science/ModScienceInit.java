package malek.mod_science;

import malek.mod_science.biomes.ModBiomes;
import malek.mod_science.blocks.ModBlocks;
import malek.mod_science.blocks.ModBlockEntities;
import malek.mod_science.commands.ModCommands;
import malek.mod_science.recipes.ModRecipes;
import malek.mod_science.dimensions.TheRoomDimension;
import malek.mod_science.effects.ModEffects;
import malek.mod_science.entities.ModEntities;
import malek.mod_science.fluids.ModBuckets;
import malek.mod_science.fluids.ModFluidBlocks;
import malek.mod_science.fluids.ModFluids;
import malek.mod_science.generation.ModGeneration;
import malek.mod_science.items.ModBlockItems;
import malek.mod_science.items.ModItems;
import malek.mod_science.screens.ModScreens;
import malek.mod_science.sounds.ModSounds;
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
import net.minecraft.screen.ScreenHandlerType;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

import static malek.mod_science.ModScience.MOD_ID;
import static malek.mod_science.blocks.ModBlockEntities.MATTER_CHAMBER;

public class ModScienceInit implements ModInitializer, LoggerInterface {
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
        ModItems.init();
        ModBlocks.init();
        ModBlockItems.init();
        ModGeneration.init();
        ModEntities.init();
        ModBlockEntities.init();
        ModEffects.init();

        //ModFluids
        ModFluids.init();
        ModFluidBlocks.init();
        ModBuckets.init();
        ModSounds.init();
        ModBiomes.init();
        ModScreens.init();
        ModRecipes.init();
        TheRoomDimension.init();
        ModCommands.init();
        ModScienceTags.init();

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
    @Override
    public Logger getLogger() {
        return LogManager.getLogger();
    }

//    @Override
//    public void onInitializeClient() {
//
//    }
}
