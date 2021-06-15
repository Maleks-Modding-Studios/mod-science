package malek.mod_science;

import malek.mod_science.util.general.LoggerInterface;
import malek.mod_science.util.general.ModCompatibility;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.Set;

public class ModScienceInit implements ModInitializer, LoggerInterface {
    public static final Set<ModCompatibility> MODS = new HashSet<>();
    public static ModCompatibility DimensionalDoorsCompat = new ModCompatibility("dimdoors");
    public static ModCompatibility ImmersivePortalsCompat = new ModCompatibility("imm_ptl_core");

    @Override
    public void onInitialize() {
        log("UwU Daddy");
        initModCompat();
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
