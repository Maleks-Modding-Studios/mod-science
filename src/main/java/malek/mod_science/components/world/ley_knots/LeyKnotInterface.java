package malek.mod_science.components.world.ley_knots;

import dev.onyxstudios.cca.api.v3.component.Component;

public interface LeyKnotInterface extends Component {
    int getX();
    int getZ();
    double getSap();
    double getSapRegenerationAmount();
    double getInstability();
    boolean isUnravelled();


    void setX(int x);
    void setZ(int z);

    void setUnravelled(boolean unravelled);

    void setSap(double amount);
    void setInstability(double amount);
    void setSapRegenAmount(double amount);

    default void addSap(double amount) {
        setSap(Math.max(amount+getSap(), 0));
    }
    default void addInstability(double amount) {
        setInstability(Math.max(amount+getInstability(), 0));
    }
    default void removeSap(double amount) {
        addSap(-amount);
    }
    default void removeInstability(double amount) {
        addInstability(-amount);
    }

    default boolean isEmpty() {
        return getSap() == 0;
    }
}
