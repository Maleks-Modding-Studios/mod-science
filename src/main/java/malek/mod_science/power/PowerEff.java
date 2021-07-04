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
    @Override
    public String toString() {
        return "low:"+lowEff+", medium:"+mediumEff+", high:"+highEff;
    }
    public void incValue(int i) {
        switch (i) {
            case -1 :
                lowEff = -1;
                mediumEff = -1;
                highEff = -1;
                break;
            case 1 :
                lowEff++;
                break;
            case 2:
                mediumEff++;
                break;
            case 3 :
                highEff++;
                break;
        }
    }
}
