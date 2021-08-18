package malek.mod_science.worlds.dimensions;

import malek.mod_science.worlds.biomes.WyldChunkGenerator;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;

import static malek.mod_science.ModScience.MOD_ID;

public class WyldsDimension {
    // The dimension options refer to the JSON-file in the dimension subfolder of the datapack,
    // which will always share it's ID with the world that is created from it
    public static final RegistryKey<DimensionOptions> DIMENSION_KEY = RegistryKey.of(
            Registry.DIMENSION_KEY,
            new Identifier(MOD_ID, "wylds")
    );

    public static RegistryKey<World> WORLD_KEY = RegistryKey.of(
            Registry.WORLD_KEY,
            DIMENSION_KEY.getValue()
    );

    public static final RegistryKey<DimensionType> DIMENSION_TYPE_KEY = RegistryKey.of(
            Registry.DIMENSION_TYPE_KEY,
            new Identifier(MOD_ID, "wylds")
    );
    public static ServerWorld world;
    public static void init() {
        Registry.register(Registry.CHUNK_GENERATOR, new Identifier(MOD_ID, "wylds"), WyldChunkGenerator.CODEC);
        WORLD_KEY = RegistryKey.of(Registry.WORLD_KEY, new Identifier(MOD_ID, "wylds"));

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            world = server.getWorld(WORLD_KEY);
           /* ServerWorld overworld = server.getWorld(World.OVERWORLD);
            world = server.getWorld(WORLD_KEY);
            server.getWorlds().forEach( (world1) -> System.out.println(world1.getRegistryKey()));
            if (world == null) throw new AssertionError("Test world doesn't exist.");

            Entity entity = COW.create(overworld);

            if (!entity.world.getRegistryKey().equals(World.OVERWORLD)) throw new AssertionError("Entity starting world isn't the overworld");

            TeleportTarget target = new TeleportTarget(Vec3d.ZERO, new Vec3d(1, 1, 1), 45f, 60f);

            Entity teleported = FabricDimensions.teleport(entity, world, target);

            if (teleported == null) throw new AssertionError("Entity didn't teleport");

            if (!teleported.world.getRegistryKey().equals(WORLD_KEY)) throw new AssertionError("Target world not reached.");

            if (!teleported.getPos().equals(target.position)) throw new AssertionError("Target Position not reached.");

            */

        });
        //Registry.register(Registry.CHUNK_GENERATOR, new Identifier(MOD_ID, "white_void"), VoidChunkGenerator.CODEC);
    }


}
