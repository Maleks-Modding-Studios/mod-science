package malek.mod_science.items.item_nbt;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

import static malek.mod_science.util.item.ItemNbt.charged;
import static malek.mod_science.util.item.ItemNbt.powerLevel;

public interface PowerLevel {
    static int getPowerLevel(ItemStack stack) {
        NbtCompound nbtCompound = stack.getNbt();
        if(nbtCompound == null)
            return 0;
        return nbtCompound.getInt(powerLevel);
    }

    static void setPowerLevel(ItemStack stack, int power_level) {
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        nbtCompound.putInt(powerLevel, power_level);
    }
}
