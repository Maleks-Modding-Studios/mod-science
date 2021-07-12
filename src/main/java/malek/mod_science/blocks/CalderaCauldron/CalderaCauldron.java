package malek.mod_science.blocks.CalderaCauldron;

import alexiil.mc.lib.attributes.AttributeList;
import alexiil.mc.lib.attributes.AttributeProvider;
import alexiil.mc.lib.attributes.fluid.amount.FluidAmount;
import alexiil.mc.lib.attributes.fluid.volume.FluidKeys;
import malek.mod_science.mixin.BucketItemMixin;
import malek.mod_science.power.*;
import malek.mod_science.properties.ModProperties;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.StateManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static malek.mod_science.power.Side.IN;
import static malek.mod_science.properties.ModProperties.*;

public class CalderaCauldron extends BlockWithEntity implements BlockEntityProvider, AttributeProvider, IPowerBlock {

    public CalderaCauldron(Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState()).with(SIDE_NORTH, IN)
                                                                  .with(ModProperties.SIDE_SOUTH, IN)
                                                                  .with(ModProperties.SIDE_EAST, IN)
                                                                  .with(ModProperties.SIDE_WEST, IN)
                                                                  .with(ModProperties.SIDE_UP, IN)
                                                                  .with(ModProperties.SIDE_DOWN, IN));
    }
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return getDefaultState();
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
        if (!world.isClient) {
            if(player.getInventory().getMainHandStack().getItem()==Items.STICK) {
                world.setBlockState(pos,  state.cycle(ModProperties.getSideFromDirection(hit.getSide())));
                FindPathToReceivers.resetNetworkSearch(world, pos);
                return ActionResult.PASS;
            }
            if (player.getInventory().getMainHandStack().getItem() instanceof BucketItem) {
                BucketItem bucket = (BucketItem) player.getInventory().getMainHandStack().getItem();
                CalderaCauldronBlockEntity calderaCauldronBlockEntity = (CalderaCauldronBlockEntity) world.getBlockEntity(pos);
                if (player.getInventory().getMainHandStack().getItem() == Items.BUCKET) {
                    if (!calderaCauldronBlockEntity.fluidInv.getInvFluid(0).isEmpty()) {
                        player.getInventory().setStack(player.getInventory().selectedSlot, new ItemStack(calderaCauldronBlockEntity.fluidInv.getInvFluid(0).fluidKey.getRawFluid().getBucketItem(), 1));
                        calderaCauldronBlockEntity.fluidInv.extract(FluidAmount.BUCKET);
                        calderaCauldronBlockEntity.sync();
                    }
                } else {
                    if (calderaCauldronBlockEntity.fluidInv.insert(FluidKeys.get((((BucketItemMixin) (bucket)).getFluid())).withAmount(FluidAmount.BUCKET)).getAmount() == 0) {

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

    @Override
    public PowerBlockType getPowerType() {
        return PowerBlockType.DUAL;
    }
}
