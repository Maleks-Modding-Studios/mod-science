package malek.mod_science.recipes.hex_crafting.upgraded;

import malek.mod_science.inventories.TesseractInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.ArrayList;

public class UpgradedHexcraftingRecipe implements Recipe<TesseractInventory> {
    public final ArrayList<Ingredient> ingredients;
    public final ItemStack result;
    public final int TICKS;
    private final Identifier id;

    public UpgradedHexcraftingRecipe(ArrayList<Ingredient> ingredients,  ItemStack result, int ticks, Identifier id) {
        this.ingredients = ingredients;

        this.result = result;
        this.TICKS = ticks;
        this.id = id;
    }

    @Override
    public boolean matches(TesseractInventory inv, World world) {
        for(int i = 0; i < inv.size(); i++)
        {
            if(!ingredients.get(i).test(inv.getStack(i)))
            {
                return false;
            }
        }



        return true;
    }

    @Override
    public ItemStack craft(TesseractInventory inv) {

        return getOutput().copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getOutput() {
        return this.result;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return UpgradedHexcraftingRecipeSerializer.INSTANCE;
    }


    @Override
    public RecipeType<?> getType() {
        return UpgradedType.INSTANCE;
    }
}
