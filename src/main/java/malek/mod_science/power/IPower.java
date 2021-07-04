package malek.mod_science.power;

import java.util.Set;

public interface IPower {
    Set<PowerPath> getPowerPaths();

    //Set getMax[type]PowerLevel to return 0 for it to not store that kind of power.

    double getFirePowerLevel();
    double getMaxFirePowerLevel();
    default boolean canContainFirePower() {
        return !(getMaxFirePowerLevel() == 0);
    }

    double getArcPowerLevel();
    double getMaxArcPowerLevel();
    default boolean canContainArcPower() {
        return !(getMaxArcPowerLevel() == 0);
    }

    double getLightPowerLevel();
    double getMaxLightPowerLevel();

    default boolean canContainLightPower() {
        return !(getLightPowerLevel() == 0);
    }


    double getTimePowerLevel();
    double getMaxTimePowerLevel();

    default boolean canContainTimePower() {
        return !(getMaxTimePowerLevel() == 0);
    }





}
