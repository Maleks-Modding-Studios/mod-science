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

import static malek.mod_science.biomes.CubeType.*;

public class VoidChunkGenerator extends ChunkGenerator {
    public static final Codec<VoidChunkGenerator> CODEC = RecordCodecBuilder.create((instance) -> instance.group(BiomeSource.CODEC.fieldOf("biome_source").forGetter((generator) -> generator.biomeSource)).apply(instance, instance.stable(VoidChunkGenerator::of)));

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

    static BlockState SHELF = Blocks.BOOKSHELF.getDefaultState();

    private void buildCubeOfSize(Chunk chunk, BlockPos pos, int sizex, int sizey, int sizez) {
        for (int x = pos.getX(); x < pos.getX() + sizex; x++) {
            for (int z = pos.getZ(); z < pos.getZ() + sizez; z++) {
                for (int y = pos.getY(); y < pos.getY() + sizey; y++) {
                    chunk.setBlockState(new BlockPos(x, y, z), SHELF, false);
                }
            }
        }
    }

    private void buildSmallerCube(Chunk chunk, BlockPos pos) {
        buildCubeOfSize(chunk, pos, 5, 5, 5);
    }

    private void buildMiddleSmallerCube(Chunk chunk, BlockPos pos) {
        buildCubeOfSize(chunk, pos, 6, 5, 6);
    }

    public static CubeType[][][] cubeMap = {
            {{SMALL, MEDIUM, SMALL}, {MEDIUM, ABSENT, MEDIUM}, {SMALL, MEDIUM, SMALL}}, {{SMALL, ABSENT, SMALL}, {ABSENT, ABSENT, ABSENT}, {SMALL, ABSENT, SMALL}}, {{SMALL, MEDIUM, SMALL}, {MEDIUM, ABSENT, MEDIUM}, {SMALL, MEDIUM, SMALL}}
    };

    private void buildCube(Chunk chunk, int yStart) {
        //        for(int x = 0; x < 27; x++) {
        //            for(int z = 0; z < 27; z++) {
        //                for(int y = 0; y < 27; y++) {
        //                    if(!passesTest(x, y, z, 3)) {
        //                        if(!passesTest(x, y, z, 9))
        //                            chunk.setBlockState(new BlockPos(x, y+yStart, z), SHELF, false);
        //                    }
        //                }
        //            }
        //        }
    }

    private void buildMengerCube(Chunk chunk, int yStart) {
        int yPos = yStart;
        int zPos = 0;
        int xPos = 0;
        int xSize = 5;
        int ySize = 5;
        int zSize = 5;
        for (int y = 0; y < cubeMap.length; y++) {
            for (int z = 0; z < cubeMap[y].length; z++) {
                for (int x = 0; x < cubeMap[y][z].length; x++) {
                    CubeType type = cubeMap[y][z][x];
                    xSize = 5;
                    ySize = 5;
                    zSize = 5;
                    if (x == 2) {
                        xSize = 6;
                    }
                    if (z == 2) {
                        zSize = 6;
                    }
                    if (y == 2) {
                        ySize = 6;
                    }
                    switch (type) {
                        case ABSENT -> xPos += 5;
                        case MEDIUM, SMALL -> {
                            buildCubeOfSize(chunk, new BlockPos(xPos, yPos, zPos), xSize, ySize, zSize);
                            xPos += 5;
                        }
                    }
                }
                zPos += 5;
                xPos = 0;

            }
            zPos = 0;
            yPos += 5;
        }

    }

    private void buildBiggerMengerCube(Chunk chunk) {
    }

    BlockPos.Mutable mutable = new BlockPos.Mutable(0, 0, 0);

    @Override
    public void buildSurface(ChunkRegion region, Chunk chunk) {
        try {
            int x = chunk.getPos().x * 16;
            int z = chunk.getPos().z * 16;
            int testX = x;
            int testZ = z;
            int num = 16;

            for (int yt = chunk.getBottomY(); yt < chunk.getTopY(); yt++) {
                int y = yt;
                boolean passes = true;
                for (int x1 = 0; x1 < num; x1++) {
                    for (int z1 = 0; z1 < num; z1++) {
                        for (int y1 = 0; y1 < num; y1++) {
                            if (passesTest(x1 + testX, y1 + y, z1 + testZ, 3, 1) && passesTest(x1 + testX, y1 + y, z1 + testZ, 9, 3) && passesTest(x1 + testX, y1 + y, z1 + testZ, 27, 9) && passesTest(x1 + testX, y1 + y, z1 + testZ, 81, 27) && passesTest(x1 + testX, y1 + y, z1 + testZ, 243, 81) && passesTest(x1 + testX, y1 + y, z1 + z, 729, 243)) {
                                mutable.set(x1 + testX, y1 + y, z1 + testZ);
                                chunk.setBlockState(mutable, SHELF, false);
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }


    private boolean passesTest(int x, int y, int z, int modulus, int previous) {
        return (mini(x, modulus, previous) && mini(y, modulus, previous)) || (mini(z, modulus, previous) && mini(y, modulus, previous)) || (mini(x, modulus, previous) && mini(z, modulus, previous));
    }

    private boolean mini(int i, int mod, int prev) {
        return (chunkMod(i, mod)) / prev != 1;
    }

    private void coolStructureWeDontUse() {
        //        boolean b = y % modulus == 0 && (x % modulus == 0 || z % modulus == 0);
        //        if(x % secondModulus == 0 && z % secondModulus == 0) {
        //            if (x % modulus == 0 && z % modulus == 0) {
        //                buildMengerCube(chunk, y * 16);
        //            }
        //            else if (b) {
        //                buildMengerCube(chunk, y * 16);
        //            }
        //        }
        //        else if (y % secondModulus== 0 && (x % secondModulus == 0 || z % secondModulus == 0)) {
        //            if (x % modulus == 0 && z % modulus == 0) {
        //                buildMengerCube(chunk, y * 16);
        //            }
        //            else if (b) {
        //                buildMengerCube(chunk, y * 16);
        //            }
        //        }
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