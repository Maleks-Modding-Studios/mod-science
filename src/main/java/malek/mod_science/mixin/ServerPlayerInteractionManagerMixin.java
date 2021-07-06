package malek.mod_science.mixin;

import malek.mod_science.dimensions.TheRoomDimension;
import net.minecraft.block.DoorBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerInteractionManager.class)
public class ServerPlayerInteractionManagerMixin {
    @Shadow
    protected ServerWorld world;
    @Inject(method = "tryBreakBlock", at = @At("HEAD"), cancellable = true)
    public void tryBreakBlockMixin(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if(world.getRegistryKey().equals(TheRoomDimension.WORLD_KEY))
            cir.setReturnValue(false);
    }
    @Inject(method = "tryBreakBlock", at = @At("HEAD"), cancellable = true)
    public void interactItem(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if(world.getRegistryKey().equals(TheRoomDimension.WORLD_KEY))
            cir.setReturnValue(false);
    }
    @Inject(method = "interactBlock", at = @At("HEAD"), cancellable = true)
    public void interactBlock(ServerPlayerEntity player, World world, ItemStack stack, Hand hand, BlockHitResult hitResult, CallbackInfoReturnable<ActionResult> cir) {
        if(world.getRegistryKey().equals(TheRoomDimension.WORLD_KEY) && !(world.getBlockState(hitResult.getBlockPos()).getBlock() instanceof DoorBlock))
            cir.setReturnValue(ActionResult.PASS);
    }

}
