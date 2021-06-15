package malek.mod_science.components.player.sap.normal_sap;

import dev.onyxstudios.cca.api.v3.component.Component;

public interface NormalSapInterface extends Component {
    double getNormalSap();
    void setNormalSap(double amount);
    void increaseNormalSap(double amount);
    void decreaseNormalSap(double amount);

    default void setNormalSap(float amount) {
        setNormalSap((double) amount);
    }
    default void increaseNormalSap(float amount) {
        increaseNormalSap((double)amount);
    }
    default void decreaseNormalSap(float amount) {
        decreaseNormalSap((double) amount);
    }

    default void setNormalSap(int amount) {
        setNormalSap((double) amount);
    }
    default void increaseNormalSap(int amount) {
        increaseNormalSap((double) amount);
    }
    default void decreaseNormalSap(int amount) {
        decreaseNormalSap((double) amount);
    }
}
