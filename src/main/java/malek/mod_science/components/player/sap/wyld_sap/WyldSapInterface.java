package malek.mod_science.components.player.sap.wyld_sap;

import dev.onyxstudios.cca.api.v3.component.Component;

public interface WyldSapInterface extends Component {
    double getWyldSap();

    void setWyldSap(double amount);

    default void setWyldSap(float amount) {
        setWyldSap((double) amount);
    }

    default void setWyldSap(int amount) {
        setWyldSap((double) amount);
    }

    default void increaseWyldSap(float amount) {
        increaseWyldSap((double) amount);
    }

    void increaseWyldSap(double amount);

    default void decreaseWyldSap(float amount) {
        decreaseWyldSap((double) amount);
    }

    void decreaseWyldSap(double amount);

    default void increaseWyldSap(int amount) {
        increaseWyldSap((double) amount);
    }

    default void decreaseWyldSap(int amount) {
        decreaseWyldSap((double) amount);
    }
}
