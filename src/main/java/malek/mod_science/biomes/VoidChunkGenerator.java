package malek.mod_science.biomes;

import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import malek.mod_science.generation.genUtils.TheRoomFeature;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.structure.StructureManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.StructuresConfig;
import net.minecraft.world.gen.chunk.VerticalBlockSample;

public class VoidChunkGenerator extends ChunkGenerator {
    public static final Codec<VoidChunkGenerator> CODEC = RecordCodecBuilder.create((instance) ->
                                                                                             instance.group(
                                                                                                     BiomeSource.CODEC.fieldOf("biome_source")
                                                                                                                      .forGetter((generator) -> generator.biomeSource)
                                                                                             ).apply(instance, instance.stable(VoidChunkGenerator::of))
    );

    public static VoidChunkGenerator of(BiomeSource biomeSource) {
        return new VoidChunkGenerator(biomeSource);
    }

    private VoidChunkGenerator(BiomeSource biomeSource) {
        super(biomeSource, new StructuresConfig(Optional.empty(), Collections.emptyMap()));
    }

    @Override
    public Codec<? extends ChunkGenerator> getCodec() {
        return CODEC;
    }

    @Override
    public ChunkGenerator withSeed(long seed) {
        return this;
    }

    @Override
    public void buildSurface(ChunkRegion region, Chunk chunk) {

//        if(chunk.getPos().z == chunk.getPos().x && chunk.getPos().z == 0) {
//            for(int x = 0; x < 32; x++) {
//                for(int z = 0; z < 32; z++) {
//                    chunk.setBlockState(new BlockPos(x-16, 1, z-16), Blocks.QUARTZ_BLOCK.getDefaultState(), false);
//                }
//            }
//            chunk.setBlockState(new BlockPos(4, 1, 4), Blocks.WARPED_DOOR.getDefaultState(), false);
//            System.out.println(chunk.getPos());
//        }
    }

    @Override
    public void setStructureStarts(DynamicRegistryManager dynamicRegistryManager, StructureAccessor structureAccessor, Chunk chunk, StructureManager structureManager, long worldSeed) {
        super.setStructureStarts(dynamicRegistryManager,structureAccessor, chunk, structureManager, worldSeed);
    }

    @Override
    public void addStructureReferences(StructureWorldAccess structureWorldAccess, StructureAccessor accessor, Chunk chunk) {
        super.addStructureReferences(structureWorldAccess, accessor, chunk);
    }

    @Override
    public CompletableFuture<Chunk> populateNoise(Executor executor, StructureAccessor accessor, Chunk chunk) {
        return CompletableFuture.supplyAsync(() -> chunk);
    }

    @Override
    public int getHeight(int x, int z, Heightmap.Type heightmap, HeightLimitView world) {
        return 0;
    }

    @Override
    public VerticalBlockSample getColumnSample(int x, int z, HeightLimitView world) {
        return new VerticalBlockSample(0, new BlockState[0]);
    }
}