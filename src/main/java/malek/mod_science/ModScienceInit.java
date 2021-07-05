package malek.mod_science;

import malek.mod_science.biomes.VoidChunkGenerator;
import malek.mod_science.blocks.ModBlocks;
import malek.mod_science.blocks.blockentities.ModBlockEntities;
import malek.mod_science.commands.ModCommands;
import malek.mod_science.dimensions.TheRoomDimension;
import malek.mod_science.entities.ModEntities;
import malek.mod_science.fluids.ModBuckets;
import malek.mod_science.fluids.ModFluidBlocks;
import malek.mod_science.fluids.ModFluids;
import malek.mod_science.generation.ModGeneration;
import malek.mod_science.items.ModBlockItems;
import malek.mod_science.items.ModItems;
import malek.mod_science.util.general.LoggerInterface;
import malek.mod_science.util.general.MatterCavitationChamberScreenHandler;
import malek.mod_science.util.general.ModCompatibility;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.fabricmc.fabric.api.tag.FabricTag;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.Entity;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
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
import static net.minecraft.entity.EntityType.COW;

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
    public static final RegistryKey<DimensionOptions> DIMENSION_KEY = RegistryKey.of(
            Registry.DIMENSION_KEY,
            new Identifier(MOD_ID, "void")
    );

    public static RegistryKey<World> WORLD_KEY = RegistryKey.of(
            Registry.WORLD_KEY,
            DIMENSION_KEY.getValue()
    );

    public static final RegistryKey<DimensionType> DIMENSION_TYPE_KEY = RegistryKey.of(
            Registry.DIMENSION_TYPE_KEY,
            new Identifier(MOD_ID, "void_type")
    );
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


        //ModBiomes.init();
        //ModDimensions.init();
        //TheRoomDimension.init();

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
        Registry.register(Registry.CHUNK_GENERATOR, new Identifier(MOD_ID, "void"), VoidChunkGenerator.CODEC);
        WORLD_KEY = RegistryKey.of(Registry.WORLD_KEY, new Identifier(MOD_ID, "the_void"));

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            ServerWorld overworld = server.getWorld(World.OVERWORLD);
            ServerWorld world = server.getWorld(WORLD_KEY);
              server.getWorlds().forEach( (world1) -> System.out.println(world1.getRegistryKey()));
               if (world == null) throw new AssertionError("Test world doesn't exist.");

            Entity entity = COW.create(overworld);

            if (!entity.world.getRegistryKey().equals(World.OVERWORLD)) throw new AssertionError("Entity starting world isn't the overworld");

            TeleportTarget target = new TeleportTarget(Vec3d.ZERO, new Vec3d(1, 1, 1), 45f, 60f);

            Entity teleported = FabricDimensions.teleport(entity, world, target);

            if (teleported == null) throw new AssertionError("Entity didn't teleport");

            if (!teleported.world.getRegistryKey().equals(WORLD_KEY)) throw new AssertionError("Target world not reached.");

            if (!teleported.getPos().equals(target.position)) throw new AssertionError("Target Position not reached.");
        });

//        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) ->
//                                                           dispatcher.register(literal("fabric_dimension_test").executes(TheRoomDimension.this::swapTargeted))
//        );


        ModCommands.init();

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
