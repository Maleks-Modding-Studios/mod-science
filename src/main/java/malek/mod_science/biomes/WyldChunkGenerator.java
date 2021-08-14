package malek.mod_science.biomes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import malek.mod_science.ModConfig;
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
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.BiomeCoords;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ProtoChunk;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.carver.CarverContext;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.chunk.AquiferSampler;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.StructuresConfig;
import net.minecraft.world.gen.chunk.VerticalBlockSample;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

public class WyldChunkGenerator extends ChunkGenerator {
    public static final Codec<WyldChunkGenerator> CODEC = RecordCodecBuilder.create((instance) -> instance.group(BiomeSource.CODEC.fieldOf("biome_source").forGetter((generator) -> generator.biomeSource)).apply(instance, instance.stable(WyldChunkGenerator::of)));

    public static WyldChunkGenerator of(BiomeSource biomeSource) {
        return new WyldChunkGenerator(biomeSource);
    }

    private WyldChunkGenerator(BiomeSource biomeSource) {
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

    BlockPos.Mutable mutable = new BlockPos.Mutable(0, 0, 0);
    Random random = new Random();
    public static final float CHANCE_FOR_STAIR = 1F;
    @Override
    public void buildSurface(ChunkRegion region, Chunk chunk) {
        PlayerEntity player = WyldsDimension.world.getClosestPlayer(chunk.getPos().x * 16, 0, chunk.getPos().z * 16, 10000, false);
        double madness = Madness.get(player).getMadness();
        buildDesert(region, chunk);
        /*if(madness < Madness.getConfig().lowMadness.thresholdAmount) {
            buildPlane(region, chunk);
        }
        else if(madness < Madness.getConfig().mediumMadness.thresholdAmount) {
            buildDesert(region, chunk);
        }*/

    }
    public void carve(long seed, BiomeAccess access, Chunk chunk, GenerationStep.Carver carver) {
        BiomeAccess biomeAccess = access.withSource(this.populationSource);
        ChunkRandom chunkRandom = new ChunkRandom();
        ChunkPos chunkPos = chunk.getPos();
        CarverContext carverContext = new CarverContext(this);
        AquiferSampler aquiferSampler = this.createAquiferSampler(chunk);
        BitSet bitSet = ((ProtoChunk)chunk).getOrCreateCarvingMask(carver);

        for(int j = -8; j <= 8; ++j) {
            for(int k = -8; k <= 8; ++k) {
                ChunkPos chunkPos2 = new ChunkPos(chunkPos.x + j, chunkPos.z + k);
                GenerationSettings generationSettings = BuiltinRegistries.BIOME.get(new Identifier("minecraft:desert")).getGenerationSettings();
                List < Supplier < ConfiguredCarver <?>>> list = generationSettings.getCarversForStep(carver);
                ListIterator listIterator = list.listIterator();

                while(listIterator.hasNext()) {
                    int l = listIterator.nextIndex();
                    ConfiguredCarver<?> configuredCarver = (ConfiguredCarver)((Supplier)listIterator.next()).get();
                    chunkRandom.setCarverSeed(seed + (long)l, chunkPos2.x, chunkPos2.z);
                    if (configuredCarver.shouldCarve(chunkRandom)) {
                        Objects.requireNonNull(biomeAccess);
                        configuredCarver.carve(carverContext, chunk, biomeAccess::getBiome, chunkRandom, aquiferSampler, chunkPos2, bitSet);
                    }
                }
            }
        }

    }
    public void buildPlane(ChunkRegion region, Chunk chunk) {

    }
    public void buildDesert(ChunkRegion region, Chunk chunk) {
        carve(WyldsDimension.world.getSeed(), region.getBiomeAccess(), chunk, GenerationStep.Carver.AIR);
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
        return CompletableFuture.completedFuture(chunk);
    }

    @Override
    public int getHeight(int x, int z, Heightmap.Type heightmap, HeightLimitView world) {
        return 256;
    }

    @Override
    public VerticalBlockSample getColumnSample(int x, int z, HeightLimitView world) {
        return new VerticalBlockSample(0, new BlockState[0]);
    }
}