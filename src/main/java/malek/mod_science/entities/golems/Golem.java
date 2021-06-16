package malek.mod_science.entities.golems;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public abstract class Golem extends PathAwareEntity implements GolemInventory {
    //Whether or not the golem is in solid form, or in movment form.
    boolean solid = true;
    //The block the golem turns into when it is solid.
    Block golemBlock;

    protected final DefaultedList<ItemStack> inventory;

    protected Golem(EntityType<? extends PathAwareEntity> entityType, World world, Block golemBlock, int inventorySize) {
        super(entityType, world);
        this.golemBlock = golemBlock;
        inventory = DefaultedList.ofSize(inventorySize, ItemStack.EMPTY);
    }
    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        Inventories.writeNbt(nbt, this.inventory);
    }
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        Inventories.readNbt(nbt, this.inventory);
    }

    public boolean getSolid() {return solid;}
}
