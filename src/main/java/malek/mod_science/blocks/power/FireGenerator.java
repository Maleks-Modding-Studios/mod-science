package malek.mod_science.blocks.power;

import malek.mod_science.power.FindPathToReceivers;
import malek.mod_science.power.IPowerBlock;
import malek.mod_science.power.PowerBlockType;
import malek.mod_science.properties.ModProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static malek.mod_science.blocks.power.Side.IN;
import static malek.mod_science.blocks.power.Side.OUT;
import static malek.mod_science.properties.ModProperties.*;
import static malek.mod_science.properties.ModProperties.SIDE_UP;

public class FireGenerator extends Block implements IPowerBlock {
    public FireGenerator(Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState()).with(SIDE_NORTH, OUT)
                                                                  .with(ModProperties.SIDE_SOUTH, OUT)
                                                                  .with(ModProperties.SIDE_EAST, OUT)
                                                                  .with(ModProperties.SIDE_WEST, OUT)
                                                                  .with(ModProperties.SIDE_UP, OUT)
                                                                  .with(ModProperties.SIDE_DOWN, OUT));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return getDefaultState();
    }
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SIDE_NORTH);
        builder.add(SIDE_SOUTH);
        builder.add(SIDE_EAST);
        builder.add(SIDE_WEST);
        builder.add(SIDE_DOWN);
        builder.add(SIDE_UP);
    }
    @Override
    public PowerBlockType getPowerType() {
        return PowerBlockType.GENERATOR;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        if(!world.isClient()) {
            FindPathToReceivers.resetNetworkSearch(world, pos);
        }
    }


    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if(!world.isClient()) {
            FindPathToReceivers.resetNetworkSearch(world, pos);
        }

        super.onBreak(world, pos, state, player);
    }
}
