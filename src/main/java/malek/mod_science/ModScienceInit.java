package malek.mod_science;

import malek.mod_science.blocks.ModBlocks;
import malek.mod_science.blocks.blockentities.ModBlockEntities;
import malek.mod_science.entities.ModEntities;
import malek.mod_science.fluids.ModBuckets;
import malek.mod_science.fluids.ModFluidBlocks;
import malek.mod_science.fluids.ModFluids;
import malek.mod_science.generation.ModGeneration;
import malek.mod_science.items.ModBlockItems;
import malek.mod_science.items.ModItems;
import malek.mod_science.util.general.LoggerInterface;
import malek.mod_science.util.general.MatterCavitationChamberScreen;
import malek.mod_science.util.general.MatterCavitationChamberScreenHandler;
import malek.mod_science.util.general.ModCompatibility;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
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
import static malek.mod_science.blocks.blockentities.ModBlockEntities.MATTER_CHAMBER;
import static malek.mod_science.util.general.WorldUtil.toBinary;

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

        //ModFluids
        ModFluids.init();
        ModFluidBlocks.init();
        ModBuckets.init();

        for (int i1 = 0; i1 < 8; i1++) {
            String binary = toBinary(i1, 3);
            String[] nums = {binary};
            char[] num = nums[0].toCharArray();
            int x = 1 + (-2 * Integer.parseInt(String.valueOf(num[0])));
            int y = 1 + (-2 * Integer.parseInt(String.valueOf(num[1])));
            int z = 1 + (-2 * Integer.parseInt(String.valueOf(num[2])));
            if(x == y && y == z) {
                continue;
            }
            System.out.println("x : " +x +  " y : " + y + " z : " + z);
        }



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
