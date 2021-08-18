package malek.mod_science.blocks.redstone;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public class RedstoneGemBlock extends Block {
    private static final IntProperty POWER;

    public RedstoneGemBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(POWER, 0));
    }

    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        BlockPos pos = hit.getBlockPos();
        if (!world.getBlockTickScheduler().isScheduled(pos, state.getBlock())) {
            world.setBlockState(pos, (BlockState) state.with(POWER, 15), 3);
            world.getBlockTickScheduler().schedule(pos, state.getBlock(), 20);
        }
        Entity entity = projectile.getOwner();
        if (entity instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)entity;
            serverPlayerEntity.incrementStat(Stats.TARGET_HIT);
        }
    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if ((Integer)state.get(POWER) != 0) {
            world.setBlockState(pos, (BlockState)state.with(POWER, 0), 3);
        }

    }

    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return (Integer)state.get(POWER);
    }

    public boolean emitsRedstonePower(BlockState state) {
        return true;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{POWER});
        super.appendProperties(builder);
    }

    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!world.isClient() && !state.isOf(oldState.getBlock())) {
            if ((Integer)state.get(POWER) > 0 && !world.getBlockTickScheduler().isScheduled(pos, this)) {
                world.setBlockState(pos, (BlockState)state.with(POWER, 0), 18);
            }

        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.getBlockTickScheduler().isScheduled(pos, state.getBlock())) {
            world.setBlockState(pos, (BlockState) state.with(POWER, 15), 3);
            world.getBlockTickScheduler().schedule(pos, state.getBlock(), 8);
        }
        return ActionResult.CONSUME;
    }

    static {
        POWER = Properties.POWER;
    }
}
