package malek.mod_science.power;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;


public abstract class PowerPipe extends Block implements IPowerBlock, IPowerCarrier {

    public PowerPipe(Settings settings) {
        super(settings);
    }


    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        if(!world.isClient()) {
            FindPathToReceivers.resetNetworkSearch(world, pos);
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(0.1, 0.1, 0.1, 0.9, 0.9, 0.9);
    }


    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if(!world.isClient()) {
            FindPathToReceivers.resetNetworkSearch(world, pos);
        }

        super.onBreak(world, pos, state, player);
    }

    @Override
    public PowerBlockType getPowerType() {
        return PowerBlockType.PIPE;
    }
}
