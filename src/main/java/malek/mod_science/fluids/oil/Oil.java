package malek.mod_science.fluids.oil;

import malek.mod_science.fluids.BaseFluid;
import malek.mod_science.fluids.ModBuckets;
import malek.mod_science.fluids.ModFluidBlocks;
import malek.mod_science.fluids.ModFluids;
import malek.mod_science.util.general.LoggerInterface;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Oil extends BaseFluid implements LoggerInterface {

    @Override
    public Fluid getFlowing() {
        return ModFluids.FLOWING_OIL;
    }

    @Override
    public Fluid getStill() {
        return ModFluids.STILL_OIL;
    }

    @Override
    public Item getBucketItem() {
        return ModBuckets.OIL_BUCKET;
    }

    @Override
    protected BlockState toBlockState(FluidState state) {
        return
                ModFluidBlocks.OIL.getDefaultState().with(Properties.LEVEL_15, getBlockStateLevel(state));
    }

    @Override
    public boolean isStill(FluidState state) {
        return false;
    }

    @Override
    public int getLevel(FluidState state) {
        return 0;
    }

    public static class Flowing extends Oil {
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

    public static class Still extends Oil {
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
