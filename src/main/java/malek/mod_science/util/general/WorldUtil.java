package malek.mod_science.util.general;

import net.minecraft.block.DoorBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldUtil {
    public static boolean multiblockMatches(World world, BlockPos pos, MultiblockStructure multiblockStructure) {
        //We will need to check in all the directions for the directions we have.
        for (int i1 = 0; i1 < 8; i1++) {
            String binary = toBinary(i1, 3);
            String[] nums = {binary};
            char[] num = nums[0].toCharArray();
            if (matchingHelper(world, pos, new BlockPos(1 + (-2 * Integer.parseInt(String.valueOf(num[0]))), 1 + (-2 * Integer.parseInt(String.valueOf(num[1]))), 1 + (-2 * Integer.parseInt(String.valueOf(num[2])))), multiblockStructure)) {
                return true;
            }
        }

        return false;
    }

    public static String toBinary(int x, int len) {
        if (len > 0) {
            return String.format("%" + len + "s", Integer.toBinaryString(x)).replaceAll(" ", "0");
        }

        return null;
    }

    private static boolean matchingHelper(World world, BlockPos pos, BlockPos directionMultiplier, MultiblockStructure multiblockStructure) {
        pos = new BlockPos(pos.getX() * directionMultiplier.getX(), pos.getY() * directionMultiplier.getY(), pos.getZ() * directionMultiplier.getZ());
        for (int x = pos.getX(); x < multiblockStructure.sizeZ; x++) {
            for (int y = pos.getY(); y < multiblockStructure.sizeY; y++) {
                for (int z = pos.getZ(); z < multiblockStructure.sizeZ; z++) {
                    if (!multiblockStructure.matchesBlockState(x, y, z, world.getBlockState(new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z)))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static BlockPos getDoorBlockPos(World world, BlockPos pos) {
        return world.getBlockState(pos).getBlock() instanceof DoorBlock ? world.getBlockState(pos).get(DoorBlock.HALF).equals(DoubleBlockHalf.LOWER) ? pos : pos.add(0, 1, 0) : pos;
    }

}
