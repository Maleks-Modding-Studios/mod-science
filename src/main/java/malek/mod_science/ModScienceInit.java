package malek.mod_science;

import malek.mod_science.blocks.ModBlocks;
import malek.mod_science.items.ModItems;
import malek.mod_science.util.general.LoggerInterface;
import malek.mod_science.util.general.ModCompatibility;
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

public class ModScienceInit implements ModInitializer, LoggerInterface {
    public static final Set<ModCompatibility> MODS = new HashSet<>();
    private static final Supplier<Path> CONFIG_ROOT = () -> FabricLoader.getInstance().getConfigDir().resolve("dimdoors").toAbsolutePath();



    @Override
    public void onInitialize() {
        log("UwU Daddy");
        ServerLifecycleEvents.SERVER_STARTING.register((minecraftServer) -> {
            ModScience.server = minecraftServer;
        });
        initModCompat();
        ModBlocks.init();
        ModItems.init();
    }

    public static void initModCompat() {
        LogManager.getLogger().log(Level.INFO, "Mod Science Enabling Mod Compatibility");
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
}
