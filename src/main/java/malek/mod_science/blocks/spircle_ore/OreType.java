package malek.mod_science.blocks.spircle_ore;

import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.StringIdentifiable;

public enum OreType implements StringIdentifiable {
    DIAMOND,
    EMERALD,
    REDSTONE,
    LAPIS;

    @Override
    public String asString() {
        return switch (this) {
            case LAPIS -> "lapis";
            case DIAMOND -> "diamond";
            case EMERALD -> "emerald";
            case REDSTONE -> "redstone";
        };
    }
    public static class SpircleOreProperties {
        public static final EnumProperty<OreType> ORE_TYPE = EnumProperty.of("ore_type", OreType.class);
    }
}