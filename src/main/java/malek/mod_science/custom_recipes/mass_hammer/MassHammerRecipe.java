package malek.mod_science.custom_recipes.mass_hammer;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.ArrayList;

public class MassHammerRecipe implements Recipe<MassHammerInventory> {
    public final ArrayList<Ingredient> ingredients;
    public final ItemStack result;
    private final Identifier id;
    public MassHammerRecipe(ArrayList<Ingredient> ingredientArrayList, ItemStack result, Identifier id) {
        this.ingredients = ingredientArrayList;
        this.result = result;
        this.id = id;
    }

    @Override
    public boolean matches(MassHammerInventory inv, World world) {
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
    public ItemStack craft(MassHammerInventory inv) {

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
        return MassHammerRecipeSerializer.INSTANCE;
    }


    @Override
    public RecipeType<?> getType() {
        return MassHammerType.INSTANCE;
    }
}
