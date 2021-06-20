package malek.mod_science.generation;

import com.mojang.serialization.Codec;
import malek.mod_science.components.world.ley_knots.LeyKnot;
import malek.mod_science.components.world.ley_knots.LeyKnotMap;
import malek.mod_science.util.general.LoggerInterface;
import net.minecraft.block.Block;
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
import java.util.Random;

public class LeyKnotFeature extends Feature<DefaultFeatureConfig> implements LoggerInterface {
    public static final float CHANCE_FOR_LEY_KNOT_TO_GENERATE = 0.1F;
    public static final double MIN_DISTANCE_TO_SPAWN = 100;
    public static final int NUMBER_OF_LEY_KNOTS_NEARBY_CHECKED = 400;

    public LeyKnotFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    //This little thing right here makes sure a leyKnot isn't placed to close to the last placed leyKnot.
    //It also functions independently in each dimension.
    public static Map<World, ArrayList<BlockPos>> lastPos = new HashMap<>();

    private static void addToLastPos(World world, BlockPos pos) {
        if (lastPos.get(world).size() == 3) {
            lastPos.get(world).clear();
        }
        lastPos.get(world).add(pos);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        World world = context.getWorld().toServerWorld();
        Random random = context.getRandom();
        BlockPos targetPos = context.getOrigin().add(random.nextInt(16), 0, random.nextInt(16));
        if (context.getRandom().nextFloat() <= CHANCE_FOR_LEY_KNOT_TO_GENERATE) {
            lastPos.computeIfAbsent(world, k -> new ArrayList<>());
            ArrayList<BlockPos> leyKnotList = lastPos.get(world);
            if (leyKnotList.size() > 0) {
                if (targetPos.getSquaredDistance(leyKnotList.get(leyKnotList.size() - 1)) < MIN_DISTANCE_TO_SPAWN) {
                    return false;
                }
            }

            if (leyKnotList.size() == 3) {
                leyKnotList.clear();
            }
            leyKnotList.add(targetPos.toImmutable());
            if (context.getWorld().isClient()) {
                return true;
            }
            generateLeyKnot(targetPos, context);
            return true;
        }
        return false;
    }

    private void generateLeyKnot(BlockPos targetPos, FeatureContext<DefaultFeatureConfig> context) {
        ServerWorld world = context.getWorld().toServerWorld();
        Map<BlockPos, LeyKnot> leyKnotMap = LeyKnotMap.get(world);
        ArrayList<BlockPos> lastPosList = lastPos.get(world);
        LeyKnot leyKnot = new LeyKnot();
        leyKnot.setFields(targetPos.getX(), targetPos.getZ(), 100, 1, 1, false);
        leyKnotMap.put(new BlockPos(targetPos.getX(), 0, targetPos.getZ()), leyKnot);
        if (lastPosList.size() == 3) {
            leyKnotMap.get(lastPosList.get(0)).setLinkedKnot1(lastPosList.get(1));
            leyKnotMap.get(lastPosList.get(0)).setLinkedKnot2(lastPosList.get(2));

            leyKnotMap.get(lastPosList.get(1)).setLinkedKnot1(lastPosList.get(0));
            leyKnotMap.get(lastPosList.get(1)).setLinkedKnot2(lastPosList.get(2));

            leyKnotMap.get(lastPosList.get(2)).setLinkedKnot1(lastPosList.get(0));
            leyKnotMap.get(lastPosList.get(2)).setLinkedKnot2(lastPosList.get(1));

            log("Ley Knots are linked in these pos : " + lastPosList.get(0) + lastPosList.get(1) + lastPosList.get(2));
        }
        log("Created Ley Knot At : x, " + targetPos.getX() + ", z, " + targetPos.getZ());
    }
}
