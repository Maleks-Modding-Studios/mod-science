package malek.mod_science.blocks.strideblocks;

import malek.mod_science.blocks.ModBlockEntities;
import malek.mod_science.blocks.TransfusionMatrix.TransfusionMatrixBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WaterStrideBlockEntity extends BlockEntity {
    public WaterStrideBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.WATER_STRIDE_BLOCK_ENTITY, pos, state);
    }

    int waterTicksSinceCreation = 0;

    public static <T extends BlockEntity> void tick(World world, BlockPos blockPos, BlockState state, T t) {
//        if(world.isClient())
//            return;
        ((WaterStrideBlockEntity)t).tick(world, blockPos, state);
    }
    public void tick(World world, BlockPos blockpos, BlockState state) {
        if(/*!state.get(WaterStride.PERSISTENT)*/true) {
            if (waterTicksSinceCreation > 20) {
                world.setBlockState(blockpos, Blocks.WATER.getDefaultState());
                world.markDirty(blockpos);
            }
            ++waterTicksSinceCreation;
        }
    }
}
