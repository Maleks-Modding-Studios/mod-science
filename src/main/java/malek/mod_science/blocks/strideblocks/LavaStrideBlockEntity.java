package malek.mod_science.blocks.strideblocks;

import malek.mod_science.blocks.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LavaStrideBlockEntity extends BlockEntity {
    public LavaStrideBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.LAVA_STRIDE_BLOCK_ENTITY, pos, state);
    }

    int lavaTicksSinceCreation = 0;


    public static <T extends BlockEntity> void tick(World world, BlockPos blockPos, BlockState state, T t) {
        if(world.isClient())
            return;
        ((LavaStrideBlockEntity)t).tick(world, blockPos, state);
    }
    public void tick(World world, BlockPos blockpos, BlockState state) {
        if(!getCachedState().get(LavaStride.PERSISTENT)) {
            if (lavaTicksSinceCreation > 20) {
                world.setBlockState(blockpos, Blocks.LAVA.getDefaultState(), 2);
            }
            ++lavaTicksSinceCreation;
        }
    }
}
