package malek.mod_science.generation;

import com.mojang.serialization.Codec;
import malek.mod_science.components.world.ley_knots.LeyKnot;
import malek.mod_science.components.world.ley_knots.LeyKnotMap;
import malek.mod_science.util.general.LoggerInterface;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.RandomFeature;
import net.minecraft.world.gen.feature.RandomFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LeyKnotFeature extends Feature implements LoggerInterface {
    public static final float CHANCE_FOR_LEY_KNOT_TO_GENERATE = 0.1F;
    public static final double MIN_DISTANCE_TO_SPAWN = 100;
    public static final int NUMBER_OF_LEY_KNOTS_NEARBY_CHECKED = 400;

    public LeyKnotFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    //This little thing right here makes sure a leyKnot isn't placed to close to the last placed leyKnot.
    //It also functions independtly in each dimension.
    public static Map<World, ArrayList<BlockPos>> lastPos = new HashMap<>();

    private static void addToLastPos(World world, BlockPos pos) {
        if (lastPos.get(world).size() == 3) {
            lastPos.get(world).clear();
        }
        lastPos.get(world).add(pos);
    }

    @Override
    public boolean generate(FeatureContext context) {
        World world = context.getWorld().toServerWorld();
        if (context.getRandom().nextFloat() <= CHANCE_FOR_LEY_KNOT_TO_GENERATE) {
            if (lastPos.get(world) == null) {
                lastPos.put(world, new ArrayList<>());
            }
            if (lastPos.get(world).size() > 0) {
                if (context.getOrigin().getSquaredDistance(lastPos.get(world).get(lastPos.get(world).size() - 1)) < MIN_DISTANCE_TO_SPAWN) {
                    return false;
                }
            }

            addToLastPos(world, context.getOrigin().mutableCopy());
            if (context.getWorld().isClient()) {
                return true;
            }
            generateLeyKnot(context);
            return true;
        }
        return false;
    }

    private void generateLeyKnot(FeatureContext<RandomFeatureConfig> context) {
        ServerWorld world = context.getWorld().toServerWorld();
        LeyKnot leyKnot = new LeyKnot();
        leyKnot.setFields(context.getOrigin().getX(), context.getOrigin().getZ(), 100, 1, 1, false);
        LeyKnotMap.get(world).put(new BlockPos(context.getOrigin().getX(), 0, context.getOrigin().getZ()), leyKnot);
        if (lastPos.get(world).size() == 3) {
            LeyKnotMap.get(world).get(lastPos.get(world).get(0)).setLinkedKnot1(lastPos.get(world).get(1));
            LeyKnotMap.get(world).get(lastPos.get(world).get(0)).setLinkedKnot2(lastPos.get(world).get(2));

            LeyKnotMap.get(world).get(lastPos.get(world).get(1)).setLinkedKnot1(lastPos.get(world).get(0));
            LeyKnotMap.get(world).get(lastPos.get(world).get(1)).setLinkedKnot2(lastPos.get(world).get(2));

            LeyKnotMap.get(world).get(lastPos.get(world).get(2)).setLinkedKnot1(lastPos.get(world).get(0));
            LeyKnotMap.get(world).get(lastPos.get(world).get(2)).setLinkedKnot2(lastPos.get(world).get(1));

            log("Ley Knots are linked in these pos : " + lastPos.get(world).get(0) + lastPos.get(world).get(1) + lastPos.get(world).get(2));

        }
        log("Created Ley Knot At : x, " + context.getOrigin().getX() + ", z, " + context.getOrigin().getZ());
    }

    @Override
    public Logger getLogger() {
        return LogManager.getLogger();
    }


}
