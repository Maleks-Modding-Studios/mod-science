package malek.mod_science.blocks;

import alexiil.mc.lib.attributes.AttributeList;
import alexiil.mc.lib.attributes.AttributeProvider;
import alexiil.mc.lib.attributes.fluid.amount.FluidAmount;
import alexiil.mc.lib.attributes.fluid.volume.FluidKeys;
import malek.mod_science.blocks.blockentities.CalderaCauldronBlockEntity;
import malek.mod_science.blocks.blockentities.TransfusionMatrixBlockEntity;
import malek.mod_science.mixin.BucketItemMixin;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class TransfusionMatrixBlock extends ModBlockWithEntity implements BlockEntityProvider, InventoryProvider, AttributeProvider {
    protected TransfusionMatrixBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TransfusionMatrixBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return TransfusionMatrixBlockEntity::tick;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(0.001, 0.001, 0.001, 0.999, 0.999, 0.999);
    }

    @Override
    public SidedInventory getInventory(BlockState state, WorldAccess world, BlockPos pos) {
        return (SidedInventory) world.getBlockEntity(pos);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        // You need a Block.createScreenHandlerFactory implementation that delegates to the block entity,
        // such as the one from BlockWithEntity
        //player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
        if(world.isClient()) {
            return ActionResult.SUCCESS;
        }
        if(player.isSneaking()) {
            TransfusionMatrixBlockEntity transfusionMatrixBlockEntity = (TransfusionMatrixBlockEntity) world.getBlockEntity(pos);
            transfusionMatrixBlockEntity.toggleMode();
        }
        else {
            if (player.getInventory().getMainHandStack().getItem() instanceof BucketItem) {
                BucketItem bucket = (BucketItem) player.getInventory().getMainHandStack().getItem();
                TransfusionMatrixBlockEntity transfusionMatrixBlockEntity = (TransfusionMatrixBlockEntity) world.getBlockEntity(pos);
                if (player.getInventory().getMainHandStack().getItem() == Items.BUCKET) {
                    if (!transfusionMatrixBlockEntity.fluidInv.getInvFluid(0).isEmpty()) {
                        player.getInventory().setStack(player.getInventory().selectedSlot, new ItemStack(transfusionMatrixBlockEntity.fluidInv.getInvFluid(0).fluidKey.getRawFluid().getBucketItem(), 1));
                        transfusionMatrixBlockEntity.fluidInv.extract(FluidAmount.BUCKET);
                        transfusionMatrixBlockEntity.sync();
                    }
                } else {
                    if (transfusionMatrixBlockEntity.fluidInv.insert(FluidKeys.get((((BucketItemMixin) (bucket)).getFluid())).withAmount(FluidAmount.BUCKET)).getAmount() == 0) {
                        player.getInventory().setStack(player.getInventory().selectedSlot, new ItemStack(Items.BUCKET, 1));
                    }
                }
                transfusionMatrixBlockEntity.sync();

            } else {
                TransfusionMatrixBlockEntity blockEntity = (TransfusionMatrixBlockEntity) world.getBlockEntity(pos);
                if (player.getInventory().getMainHandStack().isEmpty()) {
                    player.getInventory().setStack(player.getInventory().selectedSlot, blockEntity.putItemInPlayerHand(player));
                } else {
                    blockEntity.takeItemFromPlayerHand(player, player.getInventory().getMainHandStack());
                }
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public void addAllAttributes(World world, BlockPos pos, BlockState state, AttributeList<?> to) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof CalderaCauldronBlockEntity) {
            to.offer((((CalderaCauldronBlockEntity) blockEntity).fluidInv));
        }
    }
}
