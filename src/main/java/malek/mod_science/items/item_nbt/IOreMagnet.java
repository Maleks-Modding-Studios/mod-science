package malek.mod_science.items.item_nbt;

import net.minecraft.item.ItemStack;

public interface IOreMagnet {
    static boolean hasString(ItemStack stack) {
        return stack.getNbt() != null;
    }
    static String getString(ItemStack stack) {
        return stack.getNbt().getString("block");
    }
}
