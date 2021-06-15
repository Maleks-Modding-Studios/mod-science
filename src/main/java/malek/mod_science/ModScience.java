package malek.mod_science;

import malek.mod_science.util.general.LoggerInterface;
import malek.mod_science.util.general.ModCompatibility;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public class ModScience implements LoggerInterface {
    public static final boolean ENABLE_DEBUG_LOGGING = true;
    public static final String MOD_ID = "mod_science";


    //Mod Compatibility
    public static ModCompatibility DimensionalDoorsCompat = new ModCompatibility("dimdoors");
    public static ModCompatibility ImmersivePortalsCompat = new ModCompatibility("imm_ptl_core");
    public static MinecraftServer server;

    //Server And World Stuff
    public static ServerWorld getWorld(RegistryKey<World> key) {
        return getServer().getWorld(key);
    }

    @NotNull
    public static MinecraftServer getServer() {
        if (server != null) {
            return server;
        }
        throw new UnsupportedOperationException("Accessed server too early!");
    }


    @Override
    public Logger getLogger() {
        return LogManager.getLogger();
    }
}
