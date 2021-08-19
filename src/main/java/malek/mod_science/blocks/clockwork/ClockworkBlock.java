package malek.mod_science.blocks.clockwork;

import malek.mod_science.blocks.ModBlockEntities;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;

import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.state.property.Properties.HORIZONTAL_FACING;

public class ClockworkBlock extends HorizontalFacingBlock implements BlockEntityProvider {
    public ClockworkBlock() {
        super(AbstractBlock.Settings.of(Material.STONE).nonOpaque());
        setDefaultState(getStateManager().getDefaultState().with(ON, false));
    }
    static int ticker = 0;
    public static BooleanProperty ON = BooleanProperty.of("on");

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING);
        builder.add(ON);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return this.getDefaultState().with(HORIZONTAL_FACING, context.getPlayerFacing().getOpposite());
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        Direction direction = hit.getSide();
        Direction direction1 = state.get(HORIZONTAL_FACING);
        if(direction == direction1){
            world.setBlockState(pos, state.with(ON, true));
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }
    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return !world.isClient ? ClockworkBlock::tick : null;
    }

    private static <T extends BlockEntity> void tick(World world, BlockPos blockPos, BlockState blockState, T t) {
        SoundEvent soundEventClick = SoundEvents.BLOCK_LEVER_CLICK;
        //world.playSound((PlayerEntity)null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), soundEventClick, SoundCategory.PLAYERS, 0.4F, 1.75F);
        if(blockState.get(ON)){
            ticker ++;
            if(ticker >= 20){
                ticker=0;
                world.setBlockState(blockPos, blockState.with(ON, false));
            }
        }
    }


    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return ModBlockEntities.CLOCKWORK_BLOCK_ENTITY.instantiate(pos, state);
    }
}