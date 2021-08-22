package malek.mod_science.fluids.magneticite;

import malek.mod_science.fluids.BaseFluid;
import malek.mod_science.fluids.ModBuckets;
import malek.mod_science.fluids.ModFluidBlocks;
import malek.mod_science.fluids.ModFluids;
import malek.mod_science.mixin.FlowableFluidAccessor;
import malek.mod_science.util.general.LoggerInterface;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Magneticite extends BaseFluid implements LoggerInterface {

    @Override
    protected void tryFlow(WorldAccess world, BlockPos fluidPos, FluidState state) {
        if (!state.isEmpty()) {
            BlockState blockState = world.getBlockState(fluidPos);
            BlockPos blockPos = fluidPos.down();
            BlockState blockState2 = world.getBlockState(blockPos);
            FluidState fluidState = this.getUpdatedState(world, blockPos, blockState2);
            if (this.canFlow(world, fluidPos, blockState, Direction.NORTH, blockPos, blockState2, world.getFluidState(blockPos), fluidState.getFluid())) {
                this.flow(world, blockPos, blockState2, Direction.NORTH, fluidState);
                if (((FlowableFluidAccessor)this).stupid_fucking_method_2(world, fluidPos) >= 3) {
                    ((FlowableFluidAccessor)this).stupid_fucking_method(world, fluidPos, state, blockState);
                }
            } else if (state.isStill() || !((FlowableFluidAccessor)this).stupid_fucking_method_3(world, fluidState.getFluid(), fluidPos, blockState, blockPos, blockState2)) {
                //((FlowableFluidAccessor)this).stupid_fucking_method(world, fluidPos, state, blockState);
            }

        }
    }

    @Override
    public Fluid getFlowing() {
        return ModFluids.FLOWING_MAGNETICITE;
    }

    @Override
    public Fluid getStill() {
        return ModFluids.STILL_MAGNETICITE;
    }

    @Override
    public Item getBucketItem() {
        return ModBuckets.MAGNETICITE_BUCKET;
    }

    @Override
    protected BlockState toBlockState(FluidState state) {
        return ModFluidBlocks.MAGNETICITE.getDefaultState().with(Properties.LEVEL_15, getBlockStateLevel(state));
    }

    @Override
    public boolean isStill(FluidState state) {
        return false;
    }

    @Override
    public int getLevel(FluidState state) {
        return 0;
    }

    public static class Flowing extends Magneticite {
        @Override
        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getLevel(FluidState fluidState) {
            return fluidState.get(LEVEL);
        }

        @Override
        public boolean isStill(FluidState fluidState) {
            return false;
        }
    }

    public static class Still extends Magneticite {
        @Override
        public int getLevel(FluidState fluidState) {
            return 8;
        }

        @Override
        public boolean isStill(FluidState fluidState) {
            return true;
        }
    }
    public Logger getLogger() {
        return LogManager.getLogger();
    }
}
