package malek.mod_science.blocks;

import alexiil.mc.lib.attributes.AttributeList;
import alexiil.mc.lib.attributes.AttributeProvider;
import alexiil.mc.lib.attributes.CombinableAttribute;
import alexiil.mc.lib.attributes.SearchOptions;
import alexiil.mc.lib.attributes.fluid.FluidAttributes;
import alexiil.mc.lib.attributes.fluid.FluidVolumeUtil;
import alexiil.mc.lib.attributes.fluid.amount.FluidAmount;
import alexiil.mc.lib.attributes.fluid.volume.FluidKeys;
import alexiil.mc.lib.attributes.fluid.volume.FluidVolume;
import malek.mod_science.blocks.blockentities.CalderaCauldronBlockEntity;
import malek.mod_science.blocks.blockentities.ShadowSilkOreBlockEntity;
import malek.mod_science.mixin.BucketItemMixin;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class CalderaCauldron extends BlockWithEntity implements BlockEntityProvider, AttributeProvider {

    protected CalderaCauldron(Settings settings) {
        super(settings);
    }
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            if( player.getInventory().getMainHandStack().getItem() instanceof BucketItem) {
                BucketItem bucket = (BucketItem) player.getInventory().getMainHandStack().getItem();
                CalderaCauldronBlockEntity calderaCauldronBlockEntity = (CalderaCauldronBlockEntity) world.getBlockEntity(pos);
                if(player.getInventory().getMainHandStack().getItem() == Items.BUCKET) {
                    if(!calderaCauldronBlockEntity.fluidInv.getInvFluid(0).isEmpty()) {
                        player.getInventory().setStack(player.getInventory().selectedSlot, new ItemStack(calderaCauldronBlockEntity.fluidInv.getInvFluid(0).fluidKey.getRawFluid().getBucketItem(), 1));
                        calderaCauldronBlockEntity.fluidInv.extract(FluidAmount.BUCKET);
                        calderaCauldronBlockEntity.sync();
                    }
                } else {
                    if(calderaCauldronBlockEntity.fluidInv.insert(
                            FluidKeys.get(
                                    (((BucketItemMixin)(bucket)).getFluid())
                    ).withAmount(FluidAmount.BUCKET)).getAmount() == 0) {

                        player.getInventory().setStack(player.getInventory().selectedSlot, new ItemStack(Items.BUCKET, 1));
                    }
                }
                calderaCauldronBlockEntity.sync();
            }
        }
        return ActionResult.SUCCESS;
    }
    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CalderaCauldronBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return CalderaCauldronBlockEntity::tick;
    }
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
    @Override
    public void addAllAttributes(World world, BlockPos pos, BlockState state, AttributeList<?> to) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if(blockEntity instanceof CalderaCauldronBlockEntity) {
                to.offer((((CalderaCauldronBlockEntity)blockEntity).fluidInv));
            }
    }

}
