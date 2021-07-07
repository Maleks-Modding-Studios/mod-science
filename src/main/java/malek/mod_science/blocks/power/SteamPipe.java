package malek.mod_science.blocks.power;

import malek.mod_science.blocks.ModBlocks;
import malek.mod_science.power.IPower;
import malek.mod_science.power.IPowerBlock;
import malek.mod_science.properties.ModProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;

import static malek.mod_science.blocks.power.Efficiency.*;
import static malek.mod_science.blocks.power.Efficiency.MEDIUM;
import static malek.mod_science.properties.ModProperties.CONNECTED_DIRECTIONS;
import static malek.mod_science.properties.ModProperties.getConnectedDirection;

public class SteamPipe extends PowerPipe{
    public SteamPipe(Settings settings) {
        super(settings);
        setDefaultState(this.stateManager.getDefaultState().with(ModProperties.NORTH_CONNECTED, false)
                                         .with(ModProperties.SOUTH_CONNECTED, false).with(ModProperties.WEST_CONNECTED, false)
                                         .with(ModProperties.EAST_CONNECTED, false).with(ModProperties.UP_CONNECTED, false)
                                         .with(ModProperties.DOWN_CONNECTED, false));
    }
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        for(BooleanProperty direction : CONNECTED_DIRECTIONS)
        {
            stateManager.add(direction);
        }
    }
    @Override
    public Efficiency getFireEfficiency() {
        return MEDIUM;
    }

    @Override
    public Efficiency getLightEfficiency() {
        return NONE;
    }

    @Override
    public Efficiency getArcEfficiency() {
        return LOW;
    }

    @Override
    public Efficiency getTimeEfficiency() {
        return MEDIUM;
    }

    @Override
    public Efficiency getFluidEfficiency() {
        return HIGH;
    }

    @Override
    public Efficiency getSapEfficiency() {
        return NONE;
    }

    public BlockState getStateFromWorld(WorldAccess world, BlockPos pos)
    {
        BlockPos offsetPos = pos;
        BlockState returnState = this.getDefaultState();
        for(BooleanProperty direction : CONNECTED_DIRECTIONS)
        {
            try {
                offsetPos = pos.offset(getConnectedDirection(direction));
            } catch (Exception e) {
                e.printStackTrace();
            }
            returnState = returnState.with(direction, canConnect(world.getBlockState(offsetPos).getBlock()));
        }
        return returnState;
    }
    public void tryResetState(WorldAccess world, BlockPos pos) {
        if(world.getBlockState(pos).getBlock() == ModBlocks.STEAM_PIPE) {
           resetState(world, pos);
        }
    }
    public void resetState(WorldAccess world, BlockPos pos) {
        BlockPos offsetPos = pos;
        BlockState returnState = this.getDefaultState();
        for(BooleanProperty direction : CONNECTED_DIRECTIONS)
        {
            try {
                offsetPos = pos.offset(getConnectedDirection(direction));
            } catch (Exception e) {
                e.printStackTrace();
            }
            returnState = returnState.with(direction, canConnect(world.getBlockState(offsetPos).getBlock()));
        }
        world.setBlockState(pos, returnState, 3);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return getStateFromWorld(ctx.getWorld(), ctx.getBlockPos());
    }

    public static boolean canConnect(Block block) {
        return block instanceof IPowerBlock;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        return getStateFromWorld(world, pos);
    }

}
