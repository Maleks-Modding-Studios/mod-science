package malek.mod_science.mixin;

import malek.mod_science.components.world.doors.DimPos;
import malek.mod_science.components.world.doors.DoorsComponent;
import malek.mod_science.worlds.dimensions.LSpaceDimension;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static malek.mod_science.util.general.WorldUtil.getDoorBlockPos;

@Mixin(DoorBlock.class)
public class DoorBlockMixin {

    @Inject(method = "onBreak", at = @At("HEAD"))
    public void onBreakMixin(World world, BlockPos pos, BlockState state, PlayerEntity player, CallbackInfo ci) {
        DoorsComponent.get(LSpaceDimension.world).remove(new DimPos(getDoorBlockPos(world, pos), world.getRegistryKey()));
    }
    @Inject(method = "onPlaced", at = @At("HEAD"))
    public void onPlacedMixin(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack, CallbackInfo ci) {
        DoorsComponent.get(LSpaceDimension.world).add(new DimPos(getDoorBlockPos(world, pos), world.getRegistryKey()));
    }
}
