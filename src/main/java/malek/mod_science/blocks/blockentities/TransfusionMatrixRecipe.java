package malek.mod_science.blocks.blockentities;

import alexiil.mc.lib.attributes.fluid.amount.FluidAmount;
import alexiil.mc.lib.attributes.fluid.volume.FluidKeys;
import alexiil.mc.lib.attributes.fluid.volume.FluidVolume;
import malek.mod_science.items.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.ArrayList;
import java.util.List;

public class TransfusionMatrixRecipe {
    public final RecipeItem input;
    public final RecipeItem output;
    public final FluidVolume fluidVolume;
    public TransfusionMatrixRecipe(RecipeItem input, RecipeItem output, FluidVolume fluidVolume) {
        this.input = input;
        this.output = output;
        this.fluidVolume = fluidVolume;
    }
    public static final List<TransfusionMatrixRecipe> RECIPES = new ArrayList<>();
    static {
        RECIPES.add(new TransfusionMatrixRecipe(new RecipeItem(Items.GOLD_INGOT, 1), new RecipeItem(ModItems.AMALGAMETAL, 1), FluidKeys.WATER.withAmount(FluidAmount.BUCKET)));
    }
    public boolean matches(RecipeItem input, FluidVolume fluidVolume) {
        return this.input == input && this.fluidVolume.amount() == fluidVolume.amount() && this.fluidVolume.getFluidKey() == fluidVolume.getFluidKey();
    }


}
