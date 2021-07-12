package malek.mod_science.power;

public class PowerEff {
    public int lowEff = 0;
    public int mediumEff = 0;
    public int highEff = 0;
    PowerEff(PowerEff eff) {
        lowEff = eff.lowEff;
        mediumEff = eff.mediumEff;
        highEff = eff.highEff;
    }
    PowerEff() {

    }
    public boolean canCarry() {
        return lowEff!=-1;
    }
    @Override
    public String toString() {
        return "low:"+lowEff+", medium:"+mediumEff+", high:"+highEff;
    }
    public void incValue(Efficiency efficiency) {
        if(lowEff == -1) {
            return;
        }
        switch (efficiency) {
            case NONE :
                lowEff = -1;
                mediumEff = -1;
                highEff = -1;
                break;
            case LOW :
                lowEff++;
                break;
            case MEDIUM:
                mediumEff++;
                break;
            case HIGH:
                highEff++;
                break;
        }
    }
}
