package malek.mod_science.mixin;

import malek.mod_science.ModScience;
import malek.mod_science.components.player.last_door.LastDoor;
import malek.mod_science.components.player.timeout.Timeout;
import malek.mod_science.components.world.doors.DimPos;
import malek.mod_science.components.world.doors.DoorsComponent;
import malek.mod_science.worlds.dimensions.LSpaceDimension;
import malek.mod_science.worlds.dimensions.TheRoomDimension;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Mixin(ServerPlayerInteractionManager.class)
public class ServerPlayerInteractionManagerMixin {

    private static final float CHANCE_TO_ENTER_WORLDSPINE = 0.00F;

    @Shadow
    protected ServerWorld world;



    @Inject(method = "tryBreakBlock", at = @At("HEAD"), cancellable = true)
    public void tryBreakBlockMixin(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (world.getRegistryKey().equals(TheRoomDimension.WORLD_KEY)) cir.setReturnValue(false);
    }


    //this whole thing is concern
    @Inject(method = "interactItem", at = @At("HEAD"), cancellable = true)
    public void interactItem(ServerPlayerEntity player, World world, ItemStack stack, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (world.getRegistryKey().equals(TheRoomDimension.WORLD_KEY)) cir.setReturnValue(ActionResult.PASS);
        if(stack.getItem().equals(Items.ENDER_PEARL)) {
            Timeout.TIMEOUT.get(player).setTimeOut(20);
            RegistryKey<World> registryKey = RegistryKey.of(Registry.WORLD_KEY, ModScience.ModScienceId("the_void"));
            ServerWorld serverWorld = world.getServer().getWorld(registryKey);
            Timeout.TIMEOUT.get(player).setTime();
            player.teleport(serverWorld, -5, 2, -5, 0 ,0 );
        }
     }

    @Inject(method = "interactBlock", at = @At("HEAD"), cancellable = true)
    public void interactBlock(ServerPlayerEntity player, World world, ItemStack stack, Hand hand, BlockHitResult hitResult, CallbackInfoReturnable<ActionResult> cir) {
        if (world.getRegistryKey().equals(LSpaceDimension.WORLD_KEY)) {
            Random random = new Random();
            List<DimPos> dimPosArrayList = DoorsComponent.get(world).stream().toList();
            if(random.nextFloat() <= CHANCE_TO_ENTER_WORLDSPINE) {
                //worldspine teleport code here
            }
            else {
                DimPos pos1 = dimPosArrayList.get(random.nextInt(dimPosArrayList.size()));
                player.teleport((ServerWorld) pos1.getWorld(), pos1.getX(), pos1.getY(), pos1.getZ(), player.getYaw(), player.getPitch());
            }
        }
        if (world.getRegistryKey().equals(TheRoomDimension.WORLD_KEY)) {
            if (world.getBlockState(hitResult.getBlockPos()).getBlock() instanceof DoorBlock && Timeout.TIMEOUT.get(player).isTimeToLetOut(player)) {
                if(LastDoor.LAST_DOOR.get(player).isVoid()){
                    player.teleport(player.getServer().getOverworld(), player.getServer().getOverworld().getSpawnPos().getX(), player.getServer().getOverworld().getSpawnPos().getY(), player.getServer().getOverworld().getSpawnPos().getZ(), 0f, 0f);
                }
                teleportPlayerBack(player, world);
            }
            cir.setReturnValue(ActionResult.PASS);
        }else if (world.getBlockState(hitResult.getBlockPos()).getBlock() instanceof DoorBlock && !world.getRegistryKey().equals(TheRoomDimension.WORLD_KEY)){
            LastDoor.LAST_DOOR.get(player).setLastDoor(hitResult.getBlockPos());
            LastDoor.LAST_DOOR.get(player).setLastWorld(player.getServerWorld().getRegistryKey());
            System.out.println(world);
        }
    }

    private void teleportPlayerBack(ServerPlayerEntity player, World world) {
//        if(player.getSpawnPointPosition() != null)
        if(LastDoor.LAST_DOOR.get(player).getLastDoorBlockPos().getY() != 0) {
            player.teleport(player.getServer().getWorld(LastDoor.LAST_DOOR.get(player).getLastWorld()), LastDoor.LAST_DOOR.get(player).getLastDoorBlockPos().getX(), LastDoor.LAST_DOOR.get(player).getLastDoorBlockPos().getY(), LastDoor.LAST_DOOR.get(player).getLastDoorBlockPos().getZ(), 0F, 0F);
        }
        else {
            player.teleport(player.getServer().getOverworld(), player.getServer().getOverworld().getSpawnPos().getX(), player.getServer().getOverworld().getSpawnPos().getY(), player.getServer().getOverworld().getSpawnPos().getZ(), 0f, 0f);
        }
        //        else
//            player.teleport(player.getServer().getOverworld(), player.getServer().getOverworld().getSpawnPos().getX(), player.getServer().getOverworld().getSpawnPos().getY(), player.getServer().getOverworld().getSpawnPos().getZ(), 0f, 0f);
    }
}
