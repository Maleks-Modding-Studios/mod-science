package malek.mod_science.blocks;

import malek.mod_science.blocks.blockentities.ShadowSilkOreBlockEntity;
import malek.mod_science.util.general.LoggerInterface;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

public class ShadowSilkOre extends BlockWithEntity implements BlockEntityProvider, LoggerInterface {

    public ShadowSilkOre(Settings settings) {
        super(settings);
    }
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return ShadowSilkOreBlockEntity::tick;
    }


    @Override
    public Logger getLogger() {
        return LogManager.getLogger();
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ShadowSilkOreBlockEntity(pos, state);
    }
}
