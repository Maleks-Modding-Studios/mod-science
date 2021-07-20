package malek.mod_science.biomes;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import it.unimi.dsi.fastutil.Hash;
import malek.mod_science.generation.genUtils.TheRoomFeature;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.MinecraftDedicatedServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructureManager;
import net.minecraft.util.BlockRotation;
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

    public void makeStair(Chunk chunk) {
        for(int y = chunk.getBottomY(); y < chunk.getTopY(); y++) {
            makeStairPiece(chunk, y);
        }

    }
    public void makeStairPiece(Chunk chunk, int y) {
        if(y == chunk.getBottomY()) {
            for (int x = 0; x < 16; x++)
                for(int z = 0; z < 16; z++)
                    //chunk.setBlockState(chunk.getPos().getBlockPos(x, y, z), SHELF, false);
            return;
        }
        //buildLadderSec(chunk, y);
    }

    public void buildLadderSec(Chunk chunk, int x, int y, int z) {
        chunk.setBlockState(chunk.getPos().getBlockPos(x, y, z), SHELF, false);
        chunk.setBlockState(chunk.getPos().getBlockPos(1+x, y, z), Blocks.LADDER.getDefaultState().rotate(BlockRotation.CLOCKWISE_90), false);
    }




    BlockPos.Mutable mutable = new BlockPos.Mutable(0, 0, 0);
    Random random = new Random();
    public static final float CHANCE_FOR_STAIR = 1F;
    @Override
    public void buildSurface(ChunkRegion region, Chunk chunk) {
        try {
            int x = chunk.getPos().x * 16;
            int z = chunk.getPos().z * 16;
            int testX = x;
            int testZ = z;
            int num = 16;

            int randomSelectX = random.nextInt(15);
            int randomStartY = random.nextInt(chunk.getTopY());
            boolean doY = false;
            boolean endY = false;
            for (int yt = chunk.getBottomY(); yt < chunk.getTopY(); yt++) {
                int y = yt;
                boolean passes = true;
                for (int x1 = 0; x1 < num; x1++) {
                    for (int z1 = 0; z1 < num; z1++) {
                        for (int y1 = 0; y1 < num; y1++) {
                            if (fullyPassesTest(x1+testX, y1+y, z1+testZ)) {
                                mutable.set(x1 + testX, y1 + y, z1 + testZ);
                                chunk.setBlockState(mutable, SHELF, false);
                            }
                            if (fullyPassesTest(x1+testX, y1+y, z1+testZ) && !fullyPassesTest(x1+testX+1, y1+y, z1+testZ)) {
                                chunk.setBlockState(mutable.add(1, 0, 0), Blocks.LADDER.getDefaultState().rotate(BlockRotation.CLOCKWISE_90), false);
                            }
                            /*
                            if(passesPartial(x1+testX, y1+y, z1+testZ)) {
                                if(random.nextFloat() <= 1f && !passesPartial(x1+testX, y1+y+1, z1+testZ)) {
                                    doY = true;
                                }
                            }
                            if (doY && !endY) {
                                if (!passesPartial(x1 + testX, y1 + y+1, z1 + testZ)) {
                                    System.out.println("x:"+(x1+testX) +",y:"+(y1+y+1) +",z:" + (z1+testZ));
                                    buildLadderSec(chunk, x1+testX,y1+y+1, z1+testZ);
                                }
                                else {
                                    endY = true;
                                    doY = false;
                                }
                            }

                             */
                        }
                    }
                }
            }
            /*
            for(int i = 0; i < 10; i++) {
                int ladderIndex = random.nextInt(ladderYPos.size() - 2);
                for (int y1 = ladderYPos.get(ladderIndex); y1 < ladderYPos.get(ladderIndex + 1); y1++) {
                    buildLadderSec(chunk, y1);
                    System.out.println("x" + x + "y" + y1 + "z" + z);
                }
            }

             */
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    //THIS IS CODE FOR DECIDING TO SET THINGS TO DOORS!!! REMEMBER THIS!
    /*
   if (fullyPassesTest(x1+testX, y1+y, z1+testZ) && !fullyPassesTest(x1+testX+1, y1+y, z1+testZ)) {
                                chunk.setBlockState(mutable, Blocks.LADDER.getDefaultState().rotate(BlockRotation.CLOCKWISE_90), false);
                            }
     */

    private boolean passesPartial(int x, int y, int z) {
        return passesTest(x, y, z, 27, 9) && passesTest(x, y, z, 81, 27) && passesTest(x, y, z, 243, 81) && passesTest(x, y,  z, 729, 243);
    }

    private boolean fullyPassesTest(int x, int y, int z) {
        return passesTest(x, y, z, 3, 1) && passesTest(x, y, z, 9, 3) && passesTest(x, y, z, 27, 9) && passesTest(x, y, z, 81, 27) && passesTest(x, y, z, 243, 81) && passesTest(x, y,  z, 729, 243);
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
        return CompletableFuture.completedFuture(chunk);
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