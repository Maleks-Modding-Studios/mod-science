package malek.mod_science.recipes.hex_crafting.basic;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import malek.mod_science.ModScience;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;


import java.util.ArrayList;



public class BasicHexcraftingRecipeSerializer implements RecipeSerializer<BasicHexcraftingRecipe> {
    public static final BasicHexcraftingRecipeSerializer INSTANCE = new BasicHexcraftingRecipeSerializer();

    public static final Identifier ID = ModScience.ModScienceId("basic_infuser_recipe");


    @Override
    public BasicHexcraftingRecipe read(Identifier id, JsonObject json) {
        BasicHexcraftingRecipeJsonFormat recipeJson = new Gson().fromJson(json, BasicHexcraftingRecipeJsonFormat.class);
        JsonArray ingredients = recipeJson.ingredients;
        ArrayList<Ingredient> myIngredients = new ArrayList<>();
        for(JsonElement jsonElement : ingredients)
        {
            myIngredients.add(Ingredient.fromJson(jsonElement));
        }
        Item outputItem = Registry.ITEM.getOrEmpty(new Identifier(recipeJson.outputItem)).get();
        ItemStack output = new ItemStack(outputItem, recipeJson.outputAmount);

        return new BasicHexcraftingRecipe(myIngredients, output, recipeJson.ticks, id);
    }

    @Override
    public BasicHexcraftingRecipe read(Identifier id, PacketByteBuf buf) {
        JsonArray array = new JsonArray();
        ArrayList<Ingredient> myIngredients = new ArrayList<>();
        for(JsonElement jsonElement : array)
        {
            myIngredients.add(Ingredient.fromJson(jsonElement));
        }
        ItemStack stack = buf.readItemStack();

        int ticks = buf.readInt();
        return new BasicHexcraftingRecipe(myIngredients, stack, ticks, id);
    }

    @Override
    public void write(PacketByteBuf buf, BasicHexcraftingRecipe recipe) {
        for(int i = 0; i < recipe.ingredients.size(); i++)
        {
            recipe.ingredients.get(i).write(buf);
        }
        buf.writeItemStack(recipe.getOutput());
        buf.writeInt(recipe.TICKS);
    }
}
