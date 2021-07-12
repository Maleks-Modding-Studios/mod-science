package malek.mod_science.power;

public interface IPowerCarrier {
    Efficiency getFireEfficiency();
    Efficiency getLightEfficiency();
    Efficiency getArcEfficiency();
    Efficiency getTimeEfficiency();
    Efficiency getFluidEfficiency();
    Efficiency getSapEfficiency();
}
