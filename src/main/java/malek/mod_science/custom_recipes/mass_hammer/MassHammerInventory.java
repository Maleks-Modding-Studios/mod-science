package malek.mod_science.custom_recipes.mass_hammer;

import malek.mod_science.util.general.ImplementedInventory;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

public class MassHammerInventory implements ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);
    public BlockPos pos;
    public ItemEntity itemEntity1;
    public ItemEntity itemEntity2;
    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    public void setItemEntity1(ItemEntity itemEntity) {
        itemEntity1 = itemEntity;
        inventory.set(0, itemEntity.getStack());
    }
    public void setItemEntity2(ItemEntity itemEntity) {
        itemEntity2 = itemEntity;
        inventory.set(1, itemEntity.getStack());
    }
}
