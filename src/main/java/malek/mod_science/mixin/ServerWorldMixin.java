package malek.mod_science.mixin;

import malek.mod_science.components.world.doors.DoorsComponent;
import malek.mod_science.util.general.MixinUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {

    @Inject(method = "setBlockState", at = @At("HEAD"), cancellable = true)
    public boolean setBlockStateMixin(BlockPos pos, BlockState state, int flags, CallbackInfoReturnable<Boolean> cir) {
        ServerWorld world = MixinUtil.cast(this);
        if(state.getBlock() instanceof DoorBlock) {
            if(world.getBlockState(pos).get(DoorBlock.HALF).equals(DoubleBlockHalf.UPPER)) {
                DoorsComponent.get(world).add(pos.add(0, -1, 0));
            }
            else {
                DoorsComponent.get(world).add(pos);
            }
        }
        return true;
    }

}
