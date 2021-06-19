package malek.mod_science.util.general;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;

public class EnvExecutor {
    public static void runWhenOn(EnvType env, Runnable toRun) {
        if (FabricLoader.getInstance().getEnvironmentType() == env) {
            toRun.run();
        }
    }
}
