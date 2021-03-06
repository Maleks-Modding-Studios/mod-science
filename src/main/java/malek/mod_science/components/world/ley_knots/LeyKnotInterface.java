package malek.mod_science.components.world.ley_knots;

public interface LeyKnotInterface {
    int getX();

    void setX(int x);

    int getZ();

    void setZ(int z);

    double getSapRegenerationAmount();

    boolean isUnravelled();

    void setUnravelled(boolean unravelled);

    void setSapRegenAmount(double amount);

    default void removeSap(double amount) {
        addSap(-amount);
    }

    default void addSap(double amount) {
        setSap(Math.max(amount + getSap(), 0));
    }

    double getSap();

    void setSap(double amount);

    default void removeInstability(double amount) {
        addInstability(-amount);
    }

    default void addInstability(double amount) {
        setInstability(Math.max(amount + getInstability(), 0));
    }

    double getInstability();

    void setInstability(double amount);

    default boolean isEmpty() {
        return getSap() == 0;
    }
}
