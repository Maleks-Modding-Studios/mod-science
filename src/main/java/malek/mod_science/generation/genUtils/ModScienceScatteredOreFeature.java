package malek.mod_science.generation.genUtils;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig.Target;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Objects;
import java.util.Random;

public class ModScienceScatteredOreFeature extends Feature<OreFeatureConfig> {

    public ModScienceScatteredOreFeature(Codec<OreFeatureConfig> codec) {
        super(codec);
    }

    public boolean generate(FeatureContext<OreFeatureConfig> context) {
        StructureWorldAccess structureWorldAccess = context.getWorld();
        Random random = context.getRandom();
        OreFeatureConfig oreFeatureConfig = context.getConfig();
        BlockPos blockPos = context.getOrigin();
        int i = random.nextInt(oreFeatureConfig.size + 1);
        Mutable mutable = new Mutable();

        for (int j = 0; j < i; ++j) {
            this.setPos(mutable, random, blockPos, Math.min(j, 7));
            BlockState blockState = structureWorldAccess.getBlockState(mutable);

            for (Target target : oreFeatureConfig.targets) {
                Objects.requireNonNull(structureWorldAccess);
                if (OreFeature.shouldPlace(blockState, structureWorldAccess::getBlockState, random, oreFeatureConfig, target, mutable)) {
                    structureWorldAccess.setBlockState(mutable, target.state, 2);
                    break;
                }
            }
        }

        return true;
    }

    private void setPos(Mutable mutable, Random random, BlockPos origin, int spread) {
        int i = this.getSpread(random, spread);
        int j = this.getSpread(random, spread);
        int k = this.getSpread(random, spread);
        mutable.set(origin, i, j, k);
    }

    private int getSpread(Random random, int spread) {
        return Math.round((random.nextFloat() - random.nextFloat()) * (float) spread);
    }
}
