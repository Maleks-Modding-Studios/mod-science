package malek.mod_science.blocks;

import malek.mod_science.blocks.blockentities.CalderaCauldronBlockEntity;
import malek.mod_science.blocks.blockentities.TransfusionMatrixBlockEntity;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.InventoryProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class TransfusionMatrixBlock extends ModBlockWithEntity implements BlockEntityProvider, InventoryProvider {
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
    public SidedInventory getInventory(BlockState state, WorldAccess world, BlockPos pos) {
        return (SidedInventory) world.getBlockEntity(pos);
    }
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        // You need a Block.createScreenHandlerFactory implementation that delegates to the block entity,
        // such as the one from BlockWithEntity
        //player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
        TransfusionMatrixBlockEntity blockEntity = (TransfusionMatrixBlockEntity) world.getBlockEntity(pos);
        if(player.getInventory().getMainHandStack().isEmpty()) {
            player.getInventory().setStack(player.getInventory().selectedSlot, blockEntity.putItemInPlayerHand(player));
        } else {
            blockEntity.takeItemFromPlayerHand(player, player.getInventory().getMainHandStack());
        }
        return ActionResult.SUCCESS;
    }
}
