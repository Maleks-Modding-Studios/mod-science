package malek.mod_science.util.general;

import malek.mod_science.ModScience;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface LoggerInterface {
    default void log(String message) {
        log(Level.INFO, message);
    }

    default void log(Level level, String message) {
        if (ModScience.ENABLE_DEBUG_LOGGING) {
            getLogger().log(level, message);
        }
    }

    default Logger getLogger() {
        return LogManager.getLogger();
    }

    default void normalLog(String message) {
        normalLog(Level.INFO, message);
    }

    default void normalLog(Level level, String message) {
        getLogger().log(level, message);
    }
}
