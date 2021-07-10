package malek.mod_science.custom_recipes;

import alexiil.mc.lib.attributes.fluid.impl.SimpleFixedFluidInv;
import alexiil.mc.lib.attributes.fluid.volume.FluidVolume;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;

public abstract class Recipe {
    Inventory inventory;
    SimpleFixedFluidInv fluidInv;
    Item item;
    Item output;
    FluidVolume fluidVolume;
    Predicate<ItemStack> isValid;
    Consumer<ItemStack> consumer;
    public Recipe(Inventory inventory, SimpleFixedFluidInv fluidInv, Item item, FluidVolume fluidVolume, Item output, Predicate<ItemStack> isValid, Consumer<ItemStack> consumer) {
        this.inventory = inventory;
        this.fluidInv = fluidInv;
        this.fluidVolume = fluidVolume;
        this.item = item;
        this.output = output;
        this.isValid = isValid;
        this.consumer = consumer;
    }
    public boolean matches() {
        return isValid.test(inventory.getStack(0)) && inventory.getStack(0).getItem().equals(item) ;
    }
    public void doRecipe() {
        ItemStack output1 = new ItemStack(output, 1);
        consumer.accept(output1);
        inventory.setStack(0, output1);
        inventory.markDirty();
    }
}
