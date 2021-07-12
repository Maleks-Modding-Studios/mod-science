package malek.mod_science.blocks;

import alexiil.mc.lib.attributes.AttributeList;
import alexiil.mc.lib.attributes.AttributeProvider;
import alexiil.mc.lib.attributes.fluid.amount.FluidAmount;
import alexiil.mc.lib.attributes.fluid.volume.FluidKeys;
import malek.mod_science.blocks.blockentities.CalderaCauldronBlockEntity;
import malek.mod_science.blocks.blockentities.TransfusionMatrixBlockEntity;
import malek.mod_science.blocks.power.Side;
import malek.mod_science.mixin.BucketItemMixin;
import malek.mod_science.power.FindPathToReceivers;
import malek.mod_science.properties.ModProperties;
import malek.mod_science.util.TranfusionMatrixMode;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
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

import static malek.mod_science.blocks.power.Side.IN;
import static malek.mod_science.blocks.power.Side.OUT;
import static malek.mod_science.properties.ModProperties.*;
import static malek.mod_science.properties.ModProperties.SIDE_UP;

public class TransfusionMatrixBlock extends ModBlockWithEntity implements BlockEntityProvider, InventoryProvider, AttributeProvider {
    protected TransfusionMatrixBlock(Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState()).with(SIDE_NORTH, IN)
                                                                  .with(ModProperties.SIDE_SOUTH, IN)
                                                                  .with(ModProperties.SIDE_EAST, IN)
                                                                  .with(ModProperties.SIDE_WEST, IN)
                                                                  .with(ModProperties.SIDE_UP, IN)
                                                                  .with(ModProperties.SIDE_DOWN, IN));
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
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SIDE_NORTH);
        builder.add(SIDE_SOUTH);
        builder.add(SIDE_EAST);
        builder.add(SIDE_WEST);
        builder.add(SIDE_DOWN);
        builder.add(SIDE_UP);
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
//            if(transfusionMatrixBlockEntity.mode == TranfusionMatrixMode.INSERT) {
//                for(EnumProperty<Side> side : SIDE_DIRECTIONS) {
//                    state = state.with(side, IN);
//                }
//            } else {
//                for(EnumProperty<Side> side : SIDE_DIRECTIONS) {
//                    state = state.with(side, OUT);
//                }
//            }
            for(Direction direction : Direction.values()) {
                state = state.cycle(ModProperties.getSideFromDirection(direction));
            }
            transfusionMatrixBlockEntity.markNetworkDirty();
            transfusionMatrixBlockEntity.markDirty();
            world.setBlockState(pos, state);
            transfusionMatrixBlockEntity.sync();
            FindPathToReceivers.resetNetworkSearch(world, pos);
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
    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        if(!world.isClient()) {
            FindPathToReceivers.resetNetworkSearch(world, pos);
        }
    }


    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if(!world.isClient()) {
            FindPathToReceivers.resetNetworkSearch(world, pos);
        }

        super.onBreak(world, pos, state, player);
    }
}
