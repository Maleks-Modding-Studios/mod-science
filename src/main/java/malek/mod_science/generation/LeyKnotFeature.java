package malek.mod_science.generation;

import com.mojang.serialization.Codec;
import malek.mod_science.components.world.ley_knots.LeyKnot;
import malek.mod_science.components.world.ley_knots.LeyKnotMap;
import malek.mod_science.util.general.LoggerInterface;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.RandomFeature;
import net.minecraft.world.gen.feature.RandomFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LeyKnotFeature extends Feature implements LoggerInterface {
    public static final float CHANCE_FOR_LEY_KNOT_TO_GENERATE = 0.1F;
    public static final double MIN_DISTANCE_TO_SPAWN = 100;
    public static final int NUMBER_OF_LEY_KNOTS_NEARBY_CHECKED = 400;

    public LeyKnotFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }
    public static BlockPos lastPos = new BlockPos(0, 0, 0);
    @Override
    public boolean generate(FeatureContext context) {
        if (context.getRandom().nextFloat() <= CHANCE_FOR_LEY_KNOT_TO_GENERATE) {
            if(context.getOrigin().getSquaredDistance(lastPos) < MIN_DISTANCE_TO_SPAWN) {
                return false;
            }
            lastPos = context.getOrigin().mutableCopy();
            if(context.getWorld().isClient()) {
                return true;
            }
            generateLeyKnot(context);
            return true;
        }
        return false;
    }

    private void generateLeyKnot(FeatureContext<RandomFeatureConfig> context) {
        LeyKnot leyKnot = new LeyKnot();
        leyKnot.setFields(context.getOrigin().getX(), context.getOrigin().getZ(), 100, 1, 1, false);
        LeyKnotMap.get(context.getWorld().toServerWorld()).put(new BlockPos(context.getOrigin().getX(), 0, context.getOrigin().getZ()), leyKnot);
        log("Created Ley Knot At : x, " + context.getOrigin().getX() + ", z, " + context.getOrigin().getZ());
    }

    @Override
    public Logger getLogger() {
        return LogManager.getLogger();
    }


}
