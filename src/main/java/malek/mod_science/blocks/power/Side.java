package malek.mod_science.blocks.power;

import net.minecraft.util.StringIdentifiable;

public enum Side implements StringIdentifiable {
    OUT,
    IN;

    @Override
    public String asString() {
        return this==OUT ? "out" : "in";
    }
}
