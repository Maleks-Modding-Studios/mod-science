package malek.mod_science.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(FlowableFluid.class)
public interface FlowableFluidAccessor {
    @Invoker("method_15740") int stupid_fucking_method_2(WorldView world, BlockPos pos) ;
    @Invoker("method_15744") void stupid_fucking_method(WorldAccess world, BlockPos pos, FluidState fluidState, BlockState blockState);
    @Invoker("method_15736") boolean stupid_fucking_method_3(BlockView world, Fluid fluid, BlockPos pos, BlockState state, BlockPos fromPos, BlockState fromState);
}
