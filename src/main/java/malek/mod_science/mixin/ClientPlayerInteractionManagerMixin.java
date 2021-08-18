package malek.mod_science.mixin;

import malek.mod_science.components.player.timeout.Timeout;
import malek.mod_science.worlds.dimensions.TheRoomDimension;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.DoorBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@Environment(EnvType.CLIENT)
@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {
    @Final
    @Shadow
    private MinecraftClient client;





    private long time = System.currentTimeMillis();
    @Inject(method = "interactBlock", at = @At("HEAD"), cancellable = true)
    public void interactBlock(ClientPlayerEntity player, ClientWorld world, Hand hand, BlockHitResult hitResult, CallbackInfoReturnable<ActionResult> cir) {
        if (world.getRegistryKey().equals(TheRoomDimension.WORLD_KEY)) {
            if ((world.getBlockState(hitResult.getBlockPos()).getBlock() instanceof DoorBlock)) {
                if (!Timeout.TIMEOUT.get(player).isTimeOutOver()) {
                    cir.setReturnValue(ActionResult.PASS);
                }
            } else
            {
                cir.setReturnValue(ActionResult.PASS);
            }
        }
        if(world.getBlockState(hitResult.getBlockPos()).getBlock() instanceof DoorBlock){

        }
    }

    @Inject(method = "breakBlock", at = @At("HEAD"), cancellable = true)
    public void breakBlockMixin(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if(client.world.getRegistryKey() == TheRoomDimension.WORLD_KEY) {
            cir.setReturnValue(false);
        }
    }


    @Inject(method = "interactItem", at = @At("HEAD"), cancellable = true)
    public void interactItem(PlayerEntity player, World world, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (world.getRegistryKey().equals(TheRoomDimension.WORLD_KEY)) cir.setReturnValue(ActionResult.PASS);
    }
}

