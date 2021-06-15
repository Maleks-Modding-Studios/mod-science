package malek.mod_science;

import malek.mod_science.util.general.LoggerInterface;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModScience implements ModInitializer, LoggerInterface {
    public static final boolean ENABLE_DEBUG_LOGGING = true;
    public static final String MOD_ID = "mod_science";
    @Override
    public void onInitialize() {
        log("HELLO WORLD!");
    }


    @Override
    public Logger getLogger() {
        return LogManager.getLogger();
    }
}
