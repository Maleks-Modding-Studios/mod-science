package malek.mod_science.blocks.mad;

import malek.mod_science.blocks.ModBlockEntities;
import malek.mod_science.util.general.LoggerInterface;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MADBlockEntity extends BlockEntity implements LoggerInterface {
    public MADBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MAD_BLOCK_ENTITY, pos, state);
    }

    public static <T extends BlockEntity> void tick(World world, BlockPos blockPos, BlockState state, T t) {
        if(world.isClient())
            return;
        ((MADBlockEntity)t).tick(world, blockPos, state);
    }

    private void tick(World world, BlockPos blockPos, BlockState state) {
        log("hi");
    }

    @Override
    public Logger getLogger() {
        return LogManager.getLogger();
    }


}