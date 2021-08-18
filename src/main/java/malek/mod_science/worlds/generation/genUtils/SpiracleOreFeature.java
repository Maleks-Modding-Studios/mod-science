package malek.mod_science.worlds.generation.genUtils;


import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Random;
import java.util.function.Function;

import malek.mod_science.blocks.ModBlocks;
import malek.mod_science.blocks.spircle_ore.OreType;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.ChunkSectionCache;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.Heightmap.Type;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig.Target;
import net.minecraft.world.gen.feature.util.FeatureContext;

import static malek.mod_science.blocks.spircle_ore.OreType.SpircleOreProperties.ORE_TYPE;

public class SpiracleOreFeature extends Feature<OreFeatureConfig> {
    public SpiracleOreFeature(Codec<OreFeatureConfig> codec) {
        super(codec);
    }

    public boolean generate(FeatureContext<OreFeatureConfig> context) {
        Random random = context.getRandom();
        BlockPos blockPos = context.getOrigin();
        StructureWorldAccess structureWorldAccess = context.getWorld();
        OreFeatureConfig oreFeatureConfig = (OreFeatureConfig)context.getConfig();
        float f = random.nextFloat() * 3.1415927F;
        float g = (float)oreFeatureConfig.size / 8.0F;
        int i = MathHelper.ceil(((float)oreFeatureConfig.size / 16.0F * 2.0F + 1.0F) / 2.0F);
        double d = (double)blockPos.getX() + Math.sin((double)f) * (double)g;
        double e = (double)blockPos.getX() - Math.sin((double)f) * (double)g;
        double h = (double)blockPos.getZ() + Math.cos((double)f) * (double)g;
        double j = (double)blockPos.getZ() - Math.cos((double)f) * (double)g;

        double l = (double)(blockPos.getY() + random.nextInt(3) - 2);
        double m = (double)(blockPos.getY() + random.nextInt(3) - 2);
        int n = blockPos.getX() - MathHelper.ceil(g) - i;
        int o = blockPos.getY() - 2 - i;
        int p = blockPos.getZ() - MathHelper.ceil(g) - i;
        int q = 2 * (MathHelper.ceil(g) + i);
        int r = 2 * (2 + i);

        for(int s = n; s <= n + q; ++s) {
            for(int t = p; t <= p + q; ++t) {
                if (o <= structureWorldAccess.getTopY(Type.OCEAN_FLOOR_WG, s, t)) {
                    return this.generateVeinPart(structureWorldAccess, random, oreFeatureConfig, d, e, h, j, l, m, n, o, p, q, r);
                }
            }
        }

        return false;
    }

    protected boolean generateVeinPart(StructureWorldAccess structureWorldAccess, Random random, OreFeatureConfig config, double startX, double endX, double startZ, double endZ, double startY, double endY, int x, int y, int z, int horizontalSize, int verticalSize) {
        int i = 0;
        BitSet bitSet = new BitSet(horizontalSize * verticalSize * horizontalSize);
        Mutable mutable = new Mutable();
        int j = config.size;
        double[] ds = new double[j * 4];

        int m;
        double t;
        double u;
        double v;
        double w;
        for(m = 0; m < j; ++m) {
            float f = (float)m / (float)j;
            t = MathHelper.lerp((double)f, startX, endX);
            u = MathHelper.lerp((double)f, startY, endY);
            v = MathHelper.lerp((double)f, startZ, endZ);
            w = random.nextDouble() * (double)j / 16.0D;
            double l = ((double)(MathHelper.sin(3.1415927F * f) + 1.0F) * w + 1.0D) / 2.0D;
            ds[m * 4 + 0] = t;
            ds[m * 4 + 1] = u;
            ds[m * 4 + 2] = v;
            ds[m * 4 + 3] = l;
        }

        int n;
        for(m = 0; m < j - 1; ++m) {
            if (!(ds[m * 4 + 3] <= 0.0D)) {
                for(n = m + 1; n < j; ++n) {
                    if (!(ds[n * 4 + 3] <= 0.0D)) {
                        t = ds[m * 4 + 0] - ds[n * 4 + 0];
                        u = ds[m * 4 + 1] - ds[n * 4 + 1];
                        v = ds[m * 4 + 2] - ds[n * 4 + 2];
                        w = ds[m * 4 + 3] - ds[n * 4 + 3];
                        if (w * w > t * t + u * u + v * v) {
                            if (w > 0.0D) {
                                ds[n * 4 + 3] = -1.0D;
                            } else {
                                ds[m * 4 + 3] = -1.0D;
                            }
                        }
                    }
                }
            }
        }

        ChunkSectionCache chunkSectionCache = new ChunkSectionCache(structureWorldAccess);

        try {
            for(n = 0; n < j; ++n) {
                t = ds[n * 4 + 3];
                if (!(t < 0.0D)) {
                    u = ds[n * 4 + 0];
                    v = ds[n * 4 + 1];
                    w = ds[n * 4 + 2];
                    int aa = Math.max(MathHelper.floor(u - t), x);
                    int ab = Math.max(MathHelper.floor(v - t), y);
                    int ac = Math.max(MathHelper.floor(w - t), z);
                    int ad = Math.max(MathHelper.floor(u + t), aa);
                    int ae = Math.max(MathHelper.floor(v + t), ab);
                    int af = Math.max(MathHelper.floor(w + t), ac);

                    for(int ag = aa; ag <= ad; ++ag) {
                        double ah = ((double)ag + 0.5D - u) / t;
                        if (ah * ah < 1.0D) {
                            for(int ai = ab; ai <= ae; ++ai) {
                                double aj = ((double)ai + 0.5D - v) / t;
                                if (ah * ah + aj * aj < 1.0D) {
                                    for(int ak = ac; ak <= af; ++ak) {
                                        double al = ((double)ak + 0.5D - w) / t;
                                        if (ah * ah + aj * aj + al * al < 1.0D && !structureWorldAccess.isOutOfHeightLimit(ai)) {
                                            int am = ag - x + (ai - y) * horizontalSize + (ak - z) * horizontalSize * verticalSize;
                                            if (!bitSet.get(am)) {
                                                bitSet.set(am);
                                                mutable.set(ag, ai, ak);
                                                if (structureWorldAccess.method_37368(mutable)) {
                                                    ChunkSection chunkSection = chunkSectionCache.getSection(mutable);
                                                    if (chunkSection != WorldChunk.EMPTY_SECTION) {
                                                        int an = ChunkSectionPos.getLocalCoord(ag);
                                                        int ao = ChunkSectionPos.getLocalCoord(ai);
                                                        int ap = ChunkSectionPos.getLocalCoord(ak);
                                                        BlockState blockState = chunkSection.getBlockState(an, ao, ap);
                                                        Iterator var57 = config.targets.iterator();

                                                        while(var57.hasNext()) {
                                                            Target target = (Target)var57.next();
                                                            Objects.requireNonNull(chunkSectionCache);
                                                            if (shouldPlace(blockState, chunkSectionCache::getBlockState, random, config, target, mutable)) {
                                                                chunkSection.setBlockState(an, ao, ap, ModBlocks.SPIRCALE_ORE.getDefaultState().with(ORE_TYPE, (OreType) (ORE_TYPE.getValues().toArray()[random.nextInt(ORE_TYPE.getValues().size() - 1)])), false);
                                                                ++i;
                                                                break;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Throwable var60) {
            try {
                chunkSectionCache.close();
            } catch (Throwable var59) {
                var60.addSuppressed(var59);
            }

            throw var60;
        }

        chunkSectionCache.close();
        return i > 0;
    }

    public static boolean shouldPlace(BlockState state, Function<BlockPos, BlockState> posToState, Random random, OreFeatureConfig config, Target target, Mutable pos) {
        if (!target.target.test(state, random)) {
            return false;
        } else if (shouldNotDiscard(random, config.discardOnAirChance)) {
            return true;
        } else {
            return !isExposedToAir(posToState, pos);
        }
    }

    protected static boolean shouldNotDiscard(Random random, float chance) {
        if (chance <= 0.0F) {
            return true;
        } else if (chance >= 1.0F) {
            return false;
        } else {
            return random.nextFloat() >= chance;
        }
    }
}
