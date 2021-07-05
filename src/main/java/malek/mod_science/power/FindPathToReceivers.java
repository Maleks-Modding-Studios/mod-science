package malek.mod_science.power;

import io.netty.util.internal.ConcurrentSet;
import malek.mod_science.blocks.power.FireReceiverBlockEntity;
import malek.mod_science.tags.ModScienceTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import java.util.Optional;

public class FindPathToReceivers {
    public ConcurrentSet<PowerPath> paths = new ConcurrentSet<>();
    //Previous pos contains all the previous locations that pipes have already branched out to, as to not repeat ourselves and not do an infinte loop.
    public ConcurrentSet<BlockPos> previousPos = new ConcurrentSet<>();
    public static final int MAX_RECURSION_DEPTH = 3000;
    int recursionDepth = 0;

    public static void resetNetworkSearch(World world, BlockPos pos) {
        FindPathToReceivers findPathToReceivers = new FindPathToReceivers();
        findPathToReceivers.findTargets(world, new PowerPath(pos));
        for(PowerPath path : findPathToReceivers.paths) {
            if(world.getBlockEntity(path.currentPos) == null) {
                LogManager.getLogger().log(Level.ERROR, "Trying to find path to receiver for power there was a non block entity marked as a reciver block entity");
            }
            else {
                ((FireReceiverBlockEntity) world.getBlockEntity(path.currentPos)).markNetworkDirty();
            }
        }
    }

    //looks for a path
    protected void lookForPath(World world, PowerPath powerPath) {
        if (isValidEndpoint(world, powerPath.currentPos)) {
            System.out.println(world.getBlockState(powerPath.currentPos));
            System.out.println(powerPath.currentPos);
            paths.add(powerPath);
        } else {
            findTargets(world, powerPath);
        }
    }

    public void findTargets(World world, PowerPath powerPath) {
        Optional<BlockPos>[] optionals = getAroundTarget(world, powerPath.currentPos);
        for (Optional<BlockPos> optional : optionals) {
            if (optional.isPresent() && !previousPos.contains(optional.get())) {
                previousPos.add(optional.get());
                recursionDepth++;
                //Prevents the recursive pipe algorthim from causing a stack overflow error if we go 3000 recursion too deep.
                if (recursionDepth > MAX_RECURSION_DEPTH) {
                    return;
                }
                this.lookForPath(world, new PowerPath(optional.get(), powerPath));
            }
        }
    }

    Optional<BlockPos>[] getAroundTarget(World world, BlockPos pos) {
        Optional<BlockPos>[] positions = new Optional[6];
        positions[0] = getIfMatches(world, pos.add(1, 0, 0));
        positions[1] = getIfMatches(world, pos.add(0, 1, 0));
        positions[2] = getIfMatches(world, pos.add(0, 0, 1));
        positions[3] = getIfMatches(world, pos.add(-1, 0, 0));
        positions[4] = getIfMatches(world, pos.add(0, -1, 0));
        positions[5] = getIfMatches(world, pos.add(0, 0, -1));
        return positions;
    }

    Optional<BlockPos> getIfMatches(World world, BlockPos pos) {
        if (isValidCarrier(world, pos)) {
            System.out.println(world.getBlockState(pos));
            return Optional.of(pos);
        }
        return Optional.empty();
    }

    //checks if it is a valid pipe or generator, to carry the path
    private boolean isValidCarrier(World world, BlockPos pos) {
        return (world.getBlockState(pos).isIn(ModScienceTags.PIPE) || world.getBlockState(pos).isIn(ModScienceTags.GENERATOR));
    }

    //cheks if it is a valid machine to connect to
    private boolean isValidEndpoint(World world, BlockPos pos) {
        return world.getBlockState(pos).isIn(ModScienceTags.PIPE_CONNECTS_TO);
    }
}
