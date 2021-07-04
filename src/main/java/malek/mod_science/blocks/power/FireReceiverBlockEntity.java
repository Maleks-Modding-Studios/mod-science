package malek.mod_science.blocks.power;

import malek.mod_science.blocks.blockentities.ModBlockEntities;
import malek.mod_science.power.FindPowerPathsToGenerators;
import malek.mod_science.power.PowerPath;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FireReceiverBlockEntity extends BlockEntity {
    public FireReceiverBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FIRE_POWER_HOLDER_BLOCK_ENTITY, pos, state);
    }
    FindPowerPathsToGenerators findPowerPathsToGenerators = null;
    public static <T extends BlockEntity> void tick(World world, BlockPos blockPos, BlockState state, T t) {
        if(world.isClient())
            return;
        ((FireReceiverBlockEntity)t).tick(world, blockPos, state);
    }

    private void tick(World world, BlockPos blockPos, BlockState state) {
        if(findPowerPathsToGenerators == null) {
            findPowerPathsToGenerators = new FindPowerPathsToGenerators();
            findPowerPathsToGenerators.findTargets(world, new PowerPath(blockPos));
            for(PowerPath f : findPowerPathsToGenerators.paths) {
                System.out.println(f.currentPos);
            }
        }
    }
}
