package malek.mod_science.power;

import net.minecraft.util.StringIdentifiable;

public enum Side implements StringIdentifiable {
    OUT,
    IN;

    @Override
    public String asString() {
        return this==OUT ? "out" : "in";
    }

    public static Side negate(Side side) {
        return switch (side) {
            case OUT -> IN;
            case IN -> OUT;
        };
    }
}
