package malek.mod_science.generation.genUtils;

import com.mojang.serialization.Codec;

import malek.mod_science.ModScience;
import malek.mod_science.ModScienceInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.structure.MarginedStructureStart;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.Pool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.VerticalBlockSample;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;
import org.apache.logging.log4j.Level;
import org.lwjgl.system.CallbackI;

public class TheRoomFeature extends StructureFeature<DefaultFeatureConfig> {
    public TheRoomFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public StructureStartFactory<DefaultFeatureConfig> getStructureStartFactory() {
        return TheRoomFeature.Start::new;
    }


    @Override
    protected boolean shouldStartAt(ChunkGenerator chunkGenerator, BiomeSource biomeSource, long seed, ChunkRandom chunkRandom, ChunkPos chunkPos, Biome biome, ChunkPos chunkPos2, DefaultFeatureConfig featureConfig, HeightLimitView heightLimitView) {
        BlockPos centerOfChunk = new BlockPos((chunkPos.x << 4) + 7, 0, (chunkPos.z << 4) + 7);
//
//        // Grab height of land. Will stop at first non-air block.
//        int landHeight = 1;
//
//        // Grabs column of blocks at given position. In overworld, this column will be made of stone, water, and air.
//        // In nether, it will be netherrack, lava, and air. End will only be endstone and air. It depends on what block
//        // the chunk generator will place for that dimension.
//        VerticalBlockSample columnOfBlocks = chunkGenerator.getColumnSample(centerOfChunk.getX(), centerOfChunk.getZ(), heightLimitView);
//
//        // Combine the column of blocks with land height and you get the top block itself which you can test.
//        BlockState topBlock = columnOfBlocks.getState(centerOfChunk.up(landHeight));
//        
//        // Now we test to make sure our structure is not spawning on water or other fluids.
//        // You can do height check instead too to make it spawn at high elevations.
//        ChunkPos target = new ChunkPos(0,0);
//        System.out.println("attempthing to gen");
//        System.out.println(target);
//        return (chunkPos.equals(target)); //landHeight > 100;

        System.out.println("why");
        return true;

        
    }



    /**
     * Handles calling up the structure's pieces class and height that structure will spawn at.
     */
    public static class Start extends MarginedStructureStart<DefaultFeatureConfig> {
        public Start(StructureFeature<DefaultFeatureConfig> structureIn, ChunkPos chunkPos, int referenceIn, long seedIn) {
            super(structureIn, chunkPos, referenceIn, seedIn);
        }

        @Override
        public void init(DynamicRegistryManager dynamicRegistryManager, ChunkGenerator chunkGenerator, StructureManager structureManager, ChunkPos chunkPos, Biome biome, DefaultFeatureConfig defaultFeatureConfig, HeightLimitView heightLimitView) {

            // Turns the chunk coordinates into actual coordinates we can use. (Gets center of that chunk)
            int x = (chunkPos.x << 4) + 7;
            int z = (chunkPos.z << 4) + 7;

            /*
             * We pass this into method_30419 to tell it where to generate the structure.
             * If method_30419's last parameter is true, blockpos's Y value is ignored and the
             * structure will spawn at terrain height instead. Set that parameter to false to
             * force the structure to spawn at blockpos's Y value instead. You got options here!
             */
            BlockPos.Mutable blockpos = new BlockPos.Mutable(x, 5, z);

            /*
             * If you are doing Nether structures, you'll probably want to spawn your structure on top of ledges.
             * Best way to do that is to use getColumnSample to grab a column of blocks at the structure's x/z position.
             * Then loop through it and look for land with air above it and set blockpos's Y value to it.
             * Make sure to set the final boolean in StructurePoolBasedGenerator.method_30419 to false so
             * that the structure spawns at blockpos's y value instead of placing the structure on the Bedrock roof!
             */
            //VerticalBlockSample blockView = chunkGenerator.getColumnSample(blockpos.getX(), blockpos.getZ(), heightLimitView);

            StructurePoolFeatureConfig structureSettingsAndStartPool = new StructurePoolFeatureConfig(() -> dynamicRegistryManager.get(Registry.STRUCTURE_POOL_KEY)
                                                                                                                                  // The path to the starting Template Pool JSON file to read.
                                                                                                                                  //
                                                                                                                                  // Note, this is "structure_tutorial:run_down_house/start_pool" which means
                                                                                                                                  // the game will automatically look into the following path for the template pool:
                                                                                                                                  // "resources/data/structure_tutorial/worldgen/template_pool/run_down_house/start_pool.json"
                                                                                                                                  // This is why your pool files must be in "data/<modid>/worldgen/template_pool/<the path to the pool here>"
                                                                                                                                  // because the game automatically will check in worldgen/template_pool for the pools.
                                                                                                                                  .get(ModScience.ModScienceId("the_r/start_pool")),

                                                                                                      // How many pieces outward from center can a recursive jigsaw structure spawn.
                                                                                                      // Our structure is only 1 piece outward and isn't recursive so any value of 1 or more doesn't change anything.
                                                                                                      // However, I recommend you keep this a decent value like 10 so people can use datapacks to add additional pieces to your structure easily.
                                                                                                      // But don't make it too large for recursive structures like villages or you'll crash server due to hundreds of pieces attempting to generate!
                                                                                                      10);

            // All a structure has to do is call this method to turn it into a jigsaw based structure!
            StructurePoolBasedGenerator.method_30419(
                    dynamicRegistryManager,
                    structureSettingsAndStartPool,
                    PoolStructurePiece::new,
                    chunkGenerator,
                    structureManager,
                    blockpos, // Position of the structure. Y value is ignored if last parameter is set to true.
                    this, // The class instance that holds the list that will be populated with the jigsaw pieces after this method.
                    this.random,
                    false, // Special boundary adjustments for villages. It's... hard to explain. Keep this false and make your pieces not be partially intersecting.
                    // Either not intersecting or fully contained will make children pieces spawn just fine. It's easier that way.
                    false, // Place at heightmap (top land). Set this to false for structure to be place at the passed in blockpos's Y value instead.
                    // Definitely keep this false when placing structures in the nether as otherwise, heightmap placing will put the structure on the Bedrock roof.
                    heightLimitView);
            // **THE FOLLOWING TWO LINES ARE OPTIONAL**
            //
            // Right here, you can do interesting stuff with the pieces in this.children such as offset the
            // center piece by 50 blocks up for no reason, remove repeats of a piece or add a new piece so
            // only 1 of that piece exists, etc. But you do not have access to the piece's blocks as this list
            // holds just the piece's size and positions. Blocks will be placed later in StructurePoolBasedGenerator.
            //
            // In this case, we do `piece.translate` to raise pieces up by 1 block so that the house is not right on
            // the surface of water or sunken into land a bit.
            //
            // Then we move the bounding box down by 1 by doing `piece.getBoundingBox().move` which will cause the
            // land formed around the structure to be lowered and not cover the doorstep. You can raise the bounding
            // box to force the structure to be buried as well. This bounding box stuff with land is only for structures
            // that you added to Structure.JIGSAW_STRUCTURES field handles adding land around the base of structures.
            //
            // By lifting the house up by 1 and lowering the bounding box, the land at bottom of house will now be
            // flush with the surrounding terrain without blocking off the doorstep.
            this.children.forEach(piece -> piece.translate(0, 1, 0));
            this.children.forEach(piece -> piece.getBoundingBox().move(0, -1, 0));

            // Sets the bounds of the structure once you are finished.
            this.setBoundingBoxFromChildren();

            // I use to debug and quickly find out if the structure is spawning or not and where it is.
            // This is returning the coordinates of the center starting piece.

        }

    }
}