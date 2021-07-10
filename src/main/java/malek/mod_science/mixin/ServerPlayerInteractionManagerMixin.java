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
        if (world.getRegistryKey().equals(TheRoomDimension.WORLD_KEY)) cir.setReturnValue(false);
    }

    @Inject(method = "interactItem", at = @At("HEAD"), cancellable = true)
    public void interactItem(ServerPlayerEntity player, World world, ItemStack stack, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (world.getRegistryKey().equals(TheRoomDimension.WORLD_KEY)) cir.setReturnValue(ActionResult.PASS);
    }

    @Inject(method = "interactBlock", at = @At("HEAD"), cancellable = true)
    public void interactBlock(ServerPlayerEntity player, World world, ItemStack stack, Hand hand, BlockHitResult hitResult, CallbackInfoReturnable<ActionResult> cir) {
        if (world.getRegistryKey().equals(TheRoomDimension.WORLD_KEY)) {
            if (world.getBlockState(hitResult.getBlockPos()).getBlock() instanceof DoorBlock) {
                teleportPlayerBack(player, world);
            }
            cir.setReturnValue(ActionResult.PASS);
        }
    }

    private void teleportPlayerBack(ServerPlayerEntity player, World world) {
        if(player.getSpawnPointPosition() != null)
            player.teleport(player.getServer().getWorld(player.getSpawnPointDimension()), player.getSpawnPointPosition().getX(), player.getSpawnPointPosition().getY(), player.getSpawnPointPosition().getZ(), 0F, 0F);
        else
            player.teleport(player.getServer().getOverworld(), player.getServer().getOverworld().getSpawnPos().getX(), player.getServer().getOverworld().getSpawnPos().getY(), player.getServer().getOverworld().getSpawnPos().getZ(), 0f, 0f);
    }
}
