package malek.mod_science.blocks.shadow_silk_ore;

import malek.mod_science.blocks.ModBlockEntities;
import malek.mod_science.util.general.LoggerInterface;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShadowSilkOreBlockEntity extends BlockEntity implements LoggerInterface {
    public ShadowSilkOreBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SHADOW_SILK_STONE_ORE_BLOCK_ENTITY, pos, state);
    }

    public static <T extends BlockEntity> void tick(World world, BlockPos blockPos, BlockState state, T t) {
        if(world.isClient())
            return;
        ((ShadowSilkOreBlockEntity)t).tick(world, blockPos, state);
    }

    private void tick(World world, BlockPos blockPos, BlockState state) {
        if(world.isSkyVisible(blockPos.add(0, 1, 0))) {
            world.createExplosion(new TntEntity(world, blockPos.getX()+0.5, blockPos.getY()+0.5, blockPos.getZ()+0.5, null), blockPos.getX(), blockPos.getY(), blockPos.getZ(), 1.0F, Explosion.DestructionType.BREAK);
        }
    }

    @Override
    public Logger getLogger() {
        return LogManager.getLogger();
    }


}
