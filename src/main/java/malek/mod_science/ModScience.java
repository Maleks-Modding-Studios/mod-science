package malek.mod_science;

import malek.mod_science.util.general.LoggerInterface;
import malek.mod_science.util.general.ModCompatibility;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.function.Supplier;

public class ModScience implements LoggerInterface {
    public static final boolean ENABLE_DEBUG_LOGGING = true;
    public static final String MOD_ID = "mod_science";


    //Mod Compatibility
    public static ModCompatibility DimensionalDoorsCompat = new ModCompatibility("dimdoors");
    public static ModCompatibility ImmersivePortalsCompat = new ModCompatibility("imm_ptl_core");

    //Config Stuff
    private static final Supplier<Path> CONFIG_ROOT = () -> FabricLoader.getInstance().getConfigDir().resolve("dimdoors").toAbsolutePath();
    private static final ConfigHolder<ModConfig> CONFIG_MANAGER = AutoConfig.register(ModConfig.class, ModConfig.SubRootJanksonConfigSerializer::new);
    public static ModConfig getConfig() {
        return CONFIG_MANAGER.get();
    }
    public static Path getConfigRoot() {
        return CONFIG_ROOT.get();
    }

    //Server And World Stuff
    public static ServerWorld getWorld(RegistryKey<World> key) {
        return getServer().getWorld(key);
    }
    public static MinecraftServer server;
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
