package malek.mod_science.biomes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import malek.mod_science.ModConfig;
import malek.mod_science.blocks.ModBlocks;
import malek.mod_science.components.player.madness.Madness;
import malek.mod_science.dimensions.WyldsDimension;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructureManager;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.BiomeCoords;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ProtoChunk;
import net.minecraft.world.gen.BlockSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.carver.CarverContext;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.chunk.*;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

public class WyldChunkGenerator extends ChunkGenerator {
    /*public static final Codec<WyldChunkGenerator> CODEC = RecordCodecBuilder.create((instance) -> instance.group(BiomeSource.CODEC.fieldOf("biome_source").forGetter((generator) -> generator.biomeSource),
                                                                                                                  Codec.LONG.fieldOf("seed").stable().forGetter((noiseChunkGenerator) -> noiseChunkGenerator.seed)).apply(instance, instance.stable(WyldChunkGenerator::of)));*/
    /*public static final Codec<WyldChunkGenerator> CODEC = RecordCodecBuilder.create((instance) -> instance.group(BiomeSource.CODEC.fieldOf("biome_source").forGetter((noiseChunkGenerator) -> noiseChunkGenerator.populationSource), Codec.LONG.fieldOf("seed").stable().forGetter((noiseChunkGenerator) -> noiseChunkGenerator.seed),
                                                                                                                 ChunkGeneratorSettings.REGISTRY_CODEC.fieldOf("settings1").forGetter((noiseChunkGenerator) -> noiseChunkGenerator.settings1)).apply(instance, instance.stable(WyldChunkGenerator::of)));
   */
    public static final Codec<WyldChunkGenerator> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(BiomeSource.CODEC.fieldOf("biome_source").forGetter((noiseChunkGenerator) -> {
            return noiseChunkGenerator.populationSource;
        }), Codec.LONG.fieldOf("seed").stable().forGetter((noiseChunkGenerator) -> {
            return noiseChunkGenerator.seed;
        }), ChunkGeneratorSettings.REGISTRY_CODEC.fieldOf("settings").forGetter((noiseChunkGenerator) -> {
            return noiseChunkGenerator.settings1;
        })).apply(instance, instance.stable(WyldChunkGenerator::of));
    });
    public long seed;

    public Supplier<ChunkGeneratorSettings> settings1;
    public Supplier<ChunkGeneratorSettings> settings2;
    public Supplier<ChunkGeneratorSettings> settings3;

    public NoiseChunkGenerator netherGenerator;
    public NoiseChunkGenerator savannaGenerator;

    public static WyldChunkGenerator of(BiomeSource biomeSource, long seed,  Supplier<ChunkGeneratorSettings> settings1) {
        return new WyldChunkGenerator(biomeSource, seed, settings1, settings1, settings1);
    }
    public NoiseChunkGenerator desertGenerator;
    private WyldChunkGenerator(BiomeSource biomeSource, long seed,  Supplier<ChunkGeneratorSettings> settings1,  Supplier<ChunkGeneratorSettings> settings2,  Supplier<ChunkGeneratorSettings> settings3) {
        super(biomeSource, new StructuresConfig(Optional.empty(), Collections.emptyMap()));
        this.seed = seed;
        this.settings1 = settings1;
        desertGenerator = new NoiseChunkGenerator(biomeSource, seed, settings1);
//        desertGenerator = new NoiseChunkGenerator(biomeSource, seed, settings1);
//        netherGenerator = new NoiseChunkGenerator(biomeSource, seed, settings2);
//        savannaGenerator = new NoiseChunkGenerator(biomeSource, seed, settings3);

    }

    @Override
    public Codec<? extends ChunkGenerator> getCodec() {
        return CODEC;
    }

    @Override
    public ChunkGenerator withSeed(long seed) {
        return this;
    }

    BlockPos.Mutable mutable = new BlockPos.Mutable(0, 0, 0);
    Random random = new Random();
    public static final float CHANCE_FOR_STAIR = 1F;
    @Override
    public void buildSurface(ChunkRegion region, Chunk chunk) {
        PlayerEntity player = WyldsDimension.world.getClosestPlayer(chunk.getPos().x * 16, 0, chunk.getPos().z * 16, 10000, false);
        if(player != null) {
            madness = Madness.get(player).getMadness();
            if (madness <= Madness.getConfig().lowMadness.thresholdAmount) {
                buildPlane(region, chunk);
            } else if (madness <= Madness.getConfig().mediumMadness.thresholdAmount) {
                buildDesert(region, chunk);
            }
            else {
                System.out.println(Madness.get(player).getMadness());
            }
        }
        if(player == null) {
            buildPlane(region, chunk);
        }

    }

    public void buildPlane(ChunkRegion region, Chunk chunk) {
        for(int x = 0; x < 16; x++)
            for(int z = 0; z < 16; z++)
                chunk.setBlockState(chunk.getPos().getBlockPos(x, 67, z), ModBlocks.REALITY_BLOCK_WHITE.getDefaultState(), false);
    }
    public void buildDesert(ChunkRegion region, Chunk chunk) {
        desertGenerator.buildSurface(region, chunk);
    }

    public static int chunkMod(int val, int mod) {
        return val > 0 ? val % mod : ((mod - val) % mod);
    }

    @Override
    public void setStructureStarts(DynamicRegistryManager dynamicRegistryManager, StructureAccessor structureAccessor, Chunk chunk, StructureManager structureManager, long worldSeed) {
        super.setStructureStarts(dynamicRegistryManager, structureAccessor, chunk, structureManager, worldSeed);
    }

    @Override
    public void addStructureReferences(StructureWorldAccess structureWorldAccess, StructureAccessor accessor, Chunk chunk) {
        super.addStructureReferences(structureWorldAccess, accessor, chunk);
    }

    @Override
    public CompletableFuture<Chunk> populateNoise(Executor executor, StructureAccessor accessor, Chunk chunk) {
        if (madness <= Madness.getConfig().lowMadness.thresholdAmount) {
            return CompletableFuture.completedFuture(chunk);
        } else if (madness <= Madness.getConfig().mediumMadness.thresholdAmount) {
            return desertGenerator.populateNoise(executor, accessor, chunk);
        }
        else {
            System.out.println(madness);
            return CompletableFuture.completedFuture(chunk);
        }
        //return desertGenerator.populateNoise(executor, accessor, chunk);
    }
    double madness = 0.0;
    @Override
    public int getHeight(int x, int z, Heightmap.Type heightmap, HeightLimitView world) {


            if (madness <= Madness.getConfig().lowMadness.thresholdAmount) {
                return 10;
            } else if (madness <= Madness.getConfig().mediumMadness.thresholdAmount) {
                desertGenerator.getHeight(x, z, heightmap, world);
            }
            else {
                System.out.println(madness);
                return 10;
            }
            return 10;
       // return desertGenerator.getHeight(x, z, heightmap, world);
    }

    @Override
    public VerticalBlockSample getColumnSample(int x, int z, HeightLimitView world) {


            if (madness <= Madness.getConfig().lowMadness.thresholdAmount) {
                return new VerticalBlockSample(0, new BlockState[0]);
            } else if (madness <= Madness.getConfig().mediumMadness.thresholdAmount) {
               return desertGenerator.getColumnSample(x, z, world);
            }
            else {
                System.out.println(madness);
                return new VerticalBlockSample(0, new BlockState[0]);
            }

    }

    public int getSeaLevel() {
        return desertGenerator.getSeaLevel();
    }

    public int getMinimumY() {
        return desertGenerator.getMinimumY();
    }


    public int getHeightOnGround(int x, int z, Heightmap.Type heightmap, HeightLimitView world) {
        return desertGenerator.getHeightOnGround(x, z, heightmap, world);
    }

    public int getHeightInGround(int x, int z, Heightmap.Type heightmap, HeightLimitView world) {
        return this.getHeight(x, z, heightmap, world) - 1;
    }

    public int getSpawnHeight(HeightLimitView world) {
        return this.desertGenerator.getSpawnHeight(world);
    }

    public BiomeSource getBiomeSource() {
        return this.desertGenerator.getBiomeSource();
    }

    public int getWorldHeight() {
        return this.desertGenerator.getWorldHeight();
    }
    public void carve(long seed, BiomeAccess access, Chunk chunk, GenerationStep.Carver carver) {
        desertGenerator.carve(seed, access, chunk, carver);
    }

    public void populateBiomes(Registry<Biome> biomeRegistry, Chunk chunk) {
        desertGenerator.populateBiomes(biomeRegistry, chunk);
    }

}