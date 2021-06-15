package malek.mod_science;

import malek.mod_science.blocks.ModBlocks;
import malek.mod_science.generation.ModGeneration;
import malek.mod_science.items.ModItems;
import malek.mod_science.util.general.LoggerInterface;
import malek.mod_science.util.general.ModCompatibility;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

import static malek.mod_science.ModScience.MOD_ID;

public class ModScienceInit implements ModInitializer, LoggerInterface {
    //Config Stuff
    private static final Supplier<Path> CONFIG_ROOT =
            () -> FabricLoader.getInstance().getConfigDir().resolve(MOD_ID).toAbsolutePath();
    private static final ConfigHolder<ModConfig> CONFIG_MANAGER =
            AutoConfig.register(ModConfig.class, ModConfig.SubRootJanksonConfigSerializer::new);
    public static final Set<ModCompatibility> MODS = new HashSet<>();

    public static Path getConfigRoot() {
        return CONFIG_ROOT.get();
    }

    @Override
    public void onInitialize() {
        log("UwU Daddy");
        log(getConfig().madness.lowMadness.lowMadnessThresholdAmount + "");
        ServerLifecycleEvents.SERVER_STARTING.register((minecraftServer) -> {
            ModScience.server = minecraftServer;
        });
        initModCompat();
        ModBlocks.init();
        ModItems.init();
        ModGeneration.init();
    }

    public static ModConfig getConfig() {
        return CONFIG_MANAGER.get();
    }

    public static void initModCompat() {
        LogManager.getLogger().log(Level.INFO, "Mod Science Enabling Mod Compatibility");
        for (ModCompatibility mod : MODS) {
            if (FabricLoader.getInstance().isModLoaded(mod.getModID())) {
                mod.enable();
                LogManager.getLogger()
                          .log(Level.INFO, "Mod Science Enabling Mod Compatibility For : " + mod.getModID());
            }
        }
    }

    @Override
    public Logger getLogger() {
        return LogManager.getLogger();
    }
}
