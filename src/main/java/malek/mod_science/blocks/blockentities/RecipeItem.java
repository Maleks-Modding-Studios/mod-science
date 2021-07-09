package malek.mod_science.blocks.blockentities;

import net.minecraft.item.Item;

class RecipeItem {
    public final Item item;
    public final int amount;

    RecipeItem(Item item, int amount) {
        this.item = item;
        this.amount = amount;
    }
}