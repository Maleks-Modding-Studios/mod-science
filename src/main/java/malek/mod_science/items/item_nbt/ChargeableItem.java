package malek.mod_science.items.item_nbt;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

import static malek.mod_science.util.item.ItemNbt.charged;

public interface ChargeableItem {
    static boolean isCharged(ItemStack stack) {
        NbtCompound nbtCompound = stack.getTag();
        return nbtCompound != null && nbtCompound.getBoolean(charged);
    }

    static void setCharged(ItemStack stack, boolean isCharged) {
        NbtCompound nbtCompound = stack.getOrCreateTag();
        nbtCompound.putBoolean(charged, isCharged);
    }
}
