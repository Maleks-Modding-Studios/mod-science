package malek.mod_science.power;

import io.netty.util.internal.ConcurrentSet;
import malek.mod_science.blocks.power.FirePowerGenerator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class FindFirePowerPaths {
    public ConcurrentSet<FirePowerPath> paths = new ConcurrentSet<>();
    public ConcurrentSet<BlockPos> previousPos = new ConcurrentSet<>();
    protected void lookForPath(World world, FirePowerPath firePowerPath) {
        if(isValidEndpoint(world, firePowerPath.currentPos)) {
            System.out.println(world.getBlockState(firePowerPath.currentPos));
            System.out.println(firePowerPath.currentPos);
            paths.add(firePowerPath);
        } else {
            findTargets(world, firePowerPath);
        }
    }
    public void findTargets(World world, FirePowerPath firePowerPath) {
        Optional<BlockPos>[] optionals = getAroundTarget(world, firePowerPath.currentPos);
         for (Optional<BlockPos> optional : optionals) {
             if(optional.isPresent() && !previousPos.contains(optional.get())) {
                 previousPos.add(optional.get());
//                 FindFirePowerPaths second = new FindFirePowerPaths();
//                 second.paths = this.paths;
//                 second.previousPos = this.previousPos;
//                 new Thread(second::lookForPath);
                 this.lookForPath(world, new FirePowerPath(optional.get(), firePowerPath));
                 //System.out.println(optional.get());
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
        if(isValidCarrier(world, pos)) {
            //System.out.println(world.getBlockState(pos));
            return Optional.of(pos);
        }
        return Optional.empty();
    }
    private boolean isValidCarrier(World world, BlockPos pos) {
        return world.getBlockState(pos).getBlock() instanceof FirePowerCarrier;
    }
    private boolean isValidEndpoint(World world, BlockPos pos) {
        //System.out.println(world.getBlockState(pos).getBlock());
        return world.getBlockState(pos).getBlock() instanceof FirePowerGenerator;
    }

}
