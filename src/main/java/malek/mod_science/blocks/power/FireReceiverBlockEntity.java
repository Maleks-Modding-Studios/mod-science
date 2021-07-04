package malek.mod_science.blocks.power;

import malek.mod_science.blocks.blockentities.ModBlockEntities;
import malek.mod_science.power.FindPowerPathsToGenerators;
import malek.mod_science.power.IPowerReceiver;
import malek.mod_science.power.PowerPath;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Set;

public class FireReceiverBlockEntity extends BlockEntity implements IPowerReceiver {
    boolean isNetworkDirty = false;
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
        if(findPowerPathsToGenerators == null || isNetworkDirty()) {
            findPaths();
            isNetworkDirty = false;
        }
    }
    public void findPaths() {
        findPowerPathsToGenerators = new FindPowerPathsToGenerators();
        findPowerPathsToGenerators.findTargets(world, new PowerPath(this.getPos()));
        for(PowerPath f : findPowerPathsToGenerators.paths) {
            System.out.println(f.toString());
        }
    }

    @Override
    public Set<PowerPath> getPowerPaths() {
        return findPowerPathsToGenerators.paths;
    }

    @Override
    public double getFirePowerLevel() {
        return 0;
    }

    @Override
    public double getMaxFirePowerLevel() {
        return 0;
    }

    @Override
    public double getArcPowerLevel() {
        return 0;
    }

    @Override
    public double getMaxArcPowerLevel() {
        return 0;
    }

    @Override
    public double getLightPowerLevel() {
        return 0;
    }

    @Override
    public double getMaxLightPowerLevel() {
        return 0;
    }

    @Override
    public double getTimePowerLevel() {
        return 0;
    }

    @Override
    public double getMaxTimePowerLevel() {
        return 0;
    }

    @Override
    public void markNetworkDirty() {
        isNetworkDirty = true;
    }

    @Override
    public boolean isNetworkDirty() {
        return isNetworkDirty;
    }
}
