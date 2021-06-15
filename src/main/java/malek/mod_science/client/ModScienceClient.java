package malek.mod_science.client;

import malek.mod_science.util.general.LoggerInterface;
import net.fabricmc.api.ClientModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@net.fabricmc.api.Environment(net.fabricmc.api.EnvType.CLIENT)
public class ModScienceClient implements ClientModInitializer, LoggerInterface {
    @Override
    public void onInitializeClient() {

    }

    @Override
    public Logger getLogger() {
        return LogManager.getLogger();
    }
}
