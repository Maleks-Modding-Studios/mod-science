package malek.mod_science.util.general;

import net.minecraft.block.AirBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class MultiblockStructure {
    Set<BlockState>[][][] blockStates;
    public int sizeX;
    public int sizeY;
    public int sizeZ;

    public MultiblockStructure(int sizeX, int sizeY, int sizeZ) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.sizeZ = sizeZ;
        this.blockStates = new HashSet[sizeX][sizeY][sizeZ];
    }

    public void setBlockState(BlockPos pos, Set<BlockState> blockState) {
        setBlockState(pos.getX(), pos.getY(), pos.getZ(), blockState);
    }

    public void setBlockState(int x, int y, int z, Set<BlockState> blockState) {
        this.blockStates[x][y][z] = blockState;
    }

    public boolean matchesBlockState(int x, int y, int z, BlockState state) {
        if (blockStates[x][y][z] == null) {
            return true;
        }
        for (BlockState blockState : blockStates[x][y][z]) {
            if (blockState.equals(state)) return true;
        }
        return false;
    }
}
