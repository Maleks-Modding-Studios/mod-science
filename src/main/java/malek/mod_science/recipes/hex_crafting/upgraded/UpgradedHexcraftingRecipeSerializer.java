package malek.mod_science.recipes.hex_crafting.upgraded;

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


public class UpgradedHexcraftingRecipeSerializer implements RecipeSerializer<UpgradedHexcraftingRecipe> {
    public static final UpgradedHexcraftingRecipeSerializer INSTANCE = new UpgradedHexcraftingRecipeSerializer();

    public static final Identifier ID = ModScience.ModScienceId("upgraded_hexcrafting_recipe");


    @Override
    public UpgradedHexcraftingRecipe read(Identifier id, JsonObject json) {
        UpgradedHexcraftingRecipeJsonFormat recipeJson = new Gson().fromJson(json, UpgradedHexcraftingRecipeJsonFormat.class);
        JsonArray ingredients = recipeJson.ingredients;
        ArrayList<Ingredient> myIngredients = new ArrayList<>();
        for(JsonElement jsonElement : ingredients)
        {
            myIngredients.add(Ingredient.fromJson(jsonElement));
        }
        Item outputItem = Registry.ITEM.getOrEmpty(new Identifier(recipeJson.outputItem)).get();
        ItemStack output = new ItemStack(outputItem, recipeJson.outputAmount);

        return new UpgradedHexcraftingRecipe(myIngredients, output, recipeJson.ticks, id);
    }

    @Override
    public UpgradedHexcraftingRecipe read(Identifier id, PacketByteBuf buf) {
        JsonArray array = new JsonArray();
        ArrayList<Ingredient> myIngredients = new ArrayList<>();
        for(JsonElement jsonElement : array)
        {
            myIngredients.add(Ingredient.fromJson(jsonElement));
        }
        ItemStack stack = buf.readItemStack();

        int ticks = buf.readInt();
        return new UpgradedHexcraftingRecipe(myIngredients, stack, ticks, id);
    }

    @Override
    public void write(PacketByteBuf buf, UpgradedHexcraftingRecipe recipe) {
        for(int i = 0; i < recipe.ingredients.size(); i++)
        {
            recipe.ingredients.get(i).write(buf);
        }
        buf.writeItemStack(recipe.getOutput());
        buf.writeInt(recipe.TICKS);
    }
}
