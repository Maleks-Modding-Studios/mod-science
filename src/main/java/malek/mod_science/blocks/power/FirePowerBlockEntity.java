package malek.mod_science.blocks.power;

import malek.mod_science.blocks.blockentities.ModBlockEntities;
import malek.mod_science.blocks.blockentities.ShadowSilkOreBlockEntity;
import malek.mod_science.power.FindFirePowerPaths;
import malek.mod_science.power.FirePowerPath;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.TntEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class FirePowerBlockEntity extends BlockEntity {
    public FirePowerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FIRE_POWER_HOLDER_BLOCK_ENTITY, pos, state);
    }
    FindFirePowerPaths findFirePowerPaths = null;
    public static <T extends BlockEntity> void tick(World world, BlockPos blockPos, BlockState state, T t) {
        if(world.isClient())
            return;
        ((FirePowerBlockEntity)t).tick(world, blockPos, state);
    }

    private void tick(World world, BlockPos blockPos, BlockState state) {
        if(findFirePowerPaths==null) {
            findFirePowerPaths = new FindFirePowerPaths();
            findFirePowerPaths.findTargets(world, new FirePowerPath(blockPos));
            for(FirePowerPath f : findFirePowerPaths.paths) {
                System.out.println(f.currentPos);
            }
        }
    }
}
