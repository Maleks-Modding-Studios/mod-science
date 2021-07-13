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
                     if(x==2) {
                         xSize = 6;
                     }
                     if(z==2) {
                         zSize = 6;
                     }
                     if(y==2) {
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


    @Override
    public void buildSurface(ChunkRegion region, Chunk chunk) {
        int x = Math.abs(chunk.getPos().x);
        int z = Math.abs(chunk.getPos().z);
        int modulus = 2;
        int secondModulus = 4;
        int thirdModulus = 8;
        for(int y = 0; y < 16; y++) {
//            if (chunk.getPos().x % 3 != 0 && chunk.getPos().z % 3 != 0) buildMengerCube(chunk, y*16);
//            else if(y%3 == 0 && (chunk.getPos().x % 3 == 0 || chunk.getPos().z % 3 == 0)) {
//                buildMengerCube(chunk, y*16);
//            }
            /*if(chunk.getPos().x%9 != 1 && chunk.getPos().z%9 !=1) {
                if (chunk.getPos().x % 3 != 1 && chunk.getPos().z % 3 != 1) buildMengerCube(chunk, y * 16);
                else if (y % 3 != 1 && (chunk.getPos().x % 3 == 1 || chunk.getPos().z % 3 == 1) && (chunk.getPos().x % 3 != chunk.getPos().z % 3)) {
                    buildMengerCube(chunk, y * 16);
                }
            }
            else if(y%9 != 1 && (chunk.getPos().x % 9 == 1 || chunk.getPos().z % 9 == 1) && (*//*chunk.getPos().x % 9 != chunk.getPos().z % 9*//*true)) {
                if (chunk.getPos().x % 3 != 1 && chunk.getPos().z % 3 != 1) buildMengerCube(chunk, y * 16);
                else if (y % 3 != 1 && (chunk.getPos().x % 3 == 1 || chunk.getPos().z % 3 == 1) && (chunk.getPos().x % 3 != chunk.getPos().z % 3)) {
                    buildMengerCube(chunk, y * 16);
                }
            }*/
           // if(chunk.getPos().x%9 != 1 && chunk.getPos().z%9 !=1) {
            boolean b = y % modulus == 0 && (x % modulus == 0 || z % modulus == 0);
            if(x % thirdModulus != 0 && z % thirdModulus != 0) {
                if (x % secondModulus != 0 && z % secondModulus != 0) {
                    if (x % modulus == 0 && z % modulus == 0) {
                        buildMengerCube(chunk, y * 16);
                    } else if (b) {
                        buildMengerCube(chunk, y * 16);
                    }
                } else if (y % secondModulus != 0 && (x % secondModulus != 0 || z % secondModulus != 0)) {
                    if (x % modulus == 0 && z % modulus == 0) {
                        buildMengerCube(chunk, y * 16);
                    } else if (b) {
                        buildMengerCube(chunk, y * 16);
                    }
                }
            }
            else if (y % thirdModulus != 0 && (x % thirdModulus != 0 || z % thirdModulus != 0)) {
                if (x % secondModulus != 0 && z % secondModulus != 0) {
                    if (x % modulus == 0 && z % modulus == 0) {
                        buildMengerCube(chunk, y * 16);
                    } else if (b) {
                        buildMengerCube(chunk, y * 16);
                    }
                } else if (y % secondModulus != 0 && (x % secondModulus != 0 || z % secondModulus != 0)) {
                    if (x % modulus == 0 && z % modulus == 0) {
                        buildMengerCube(chunk, y * 16);
                    } else if (b) {
                        buildMengerCube(chunk, y * 16);
                    }
                }
            }
            //}

//            else if(y%9 != 1 && (chunk.getPos().x % 9 == 1 || chunk.getPos().z % 9 == 1) && (/*chunk.getPos().x % 9 != chunk.getPos().z % 9*/true)) {
//                if (chunk.getPos().x % 3 != 1 && chunk.getPos().z % 3 != 1) buildMengerCube(chunk, y * 16);
//                else if (y % 3 != 1 && (chunk.getPos().x % 3 == 1 || chunk.getPos().z % 3 == 1) && (chunk.getPos().x % 3 != chunk.getPos().z % 3)) {
//                    buildMengerCube(chunk, y * 16);
//                }
//            }
        }
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


    private void coolStructureWeDontUse( ) {
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