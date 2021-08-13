package malek.mod_science.items.item_nbt;

import net.minecraft.item.ItemStack;

public interface IOreMagnet {
    static boolean hasString(ItemStack stack) {
        return stack.getTag() != null;
    }
    static String getString(ItemStack stack) {
        return stack.getTag().getString("block");
    }
}
