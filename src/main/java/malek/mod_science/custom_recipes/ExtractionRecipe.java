package malek.mod_science.custom_recipes;

import alexiil.mc.lib.attributes.fluid.impl.SimpleFixedFluidInv;
import alexiil.mc.lib.attributes.fluid.volume.FluidVolume;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ExtractionRecipe extends Recipe {
    public ExtractionRecipe(Inventory inventory, SimpleFixedFluidInv fluidInv, Item item, FluidVolume fluidVolume, Item output, Predicate<ItemStack> predicate, Consumer<ItemStack> consumer) {
        super(inventory, fluidInv, item, fluidVolume, output, predicate, consumer);
    }
    public boolean matches() {
        return super.matches() && fluidInv.getInvFluid(0).isEmpty();
    }

    @Override
    public void doRecipe() {
        super.doRecipe();
        fluidInv.insert(fluidVolume);
    }
}