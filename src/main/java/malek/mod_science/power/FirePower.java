package malek.mod_science.power;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.Set;

import static malek.mod_science.util.general.WorldUtil.toBinary;

public interface FirePower extends FirePowerCarrier{
    Set<FirePowerPath> getFirePowerPaths();

    double getFireLevel();
    double getFireMaxLevel();






}
