package malek.mod_science.custom_recipes.mass_hammer;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;

import static malek.mod_science.ModScience.MOD_ID;

public class MassHammerRecipeSerializer implements RecipeSerializer<MassHammerRecipe> {
    public static final MassHammerRecipeSerializer INSTANCE = new MassHammerRecipeSerializer();

    public static final Identifier ID = new Identifier(MOD_ID, "mass_hammer_recipe");


    @Override
    public MassHammerRecipe read(Identifier id, JsonObject json) {
        MassHammerJsonFormat recipeJson = new Gson().fromJson(json, MassHammerJsonFormat.class);
        JsonArray ingredients = recipeJson.ingredients;
        ArrayList<Ingredient> myIngredients = new ArrayList<>();
        for(JsonElement jsonElement : ingredients)
        {
            myIngredients.add(Ingredient.fromJson(jsonElement));
        }
        Item outputItem = Registry.ITEM.getOrEmpty(new Identifier(recipeJson.outputItem)).get();
        ItemStack output = new ItemStack(outputItem, recipeJson.outputAmount);
        return new MassHammerRecipe(myIngredients, output, id);
    }

    @Override
    public MassHammerRecipe read(Identifier id, PacketByteBuf buf) {
        JsonArray array = new JsonArray();
        ArrayList<Ingredient> myIngredients = new ArrayList<>();
        for(JsonElement jsonElement : array)
        {
            myIngredients.add(Ingredient.fromJson(jsonElement));
        }
        ItemStack stack = buf.readItemStack();
        return new MassHammerRecipe(myIngredients, stack, id);
    }

    @Override
    public void write(PacketByteBuf buf, MassHammerRecipe recipe) {
        for(int i = 0; i < recipe.ingredients.size(); i++)
        {
            recipe.ingredients.get(i).write(buf);
        }
        buf.writeItemStack(recipe.getOutput());
    }
}