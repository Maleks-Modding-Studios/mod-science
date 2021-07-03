package malek.mod_science.blocks.power;

import malek.mod_science.blocks.blockentities.ShadowSilkOreBlockEntity;
import malek.mod_science.power.*;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class FirePowerHolder extends BlockWithEntity implements FirePowerReceiver, FirePowerCarrier, BlockEntityProvider {
    public FirePowerHolder(Settings settings) {
        super(settings);
    }
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS ;
        }

        return ActionResult.SUCCESS;
    }
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return FirePowerBlockEntity::tick;
    }
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
    @Override
    public Set<FirePowerPath> getFirePowerPaths() {
        return null;
    }

    @Override
    public double getFireLevel() {
        return 0;
    }

    @Override
    public double getFireMaxLevel() {
        return 0;
    }

    @Override
    public double getFireEfficiency() {
        return 0;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new FirePowerBlockEntity(pos, state);
    }
}
