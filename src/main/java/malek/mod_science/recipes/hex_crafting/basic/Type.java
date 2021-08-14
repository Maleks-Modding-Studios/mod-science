package malek.mod_science.recipes.hex_crafting.basic;
import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.world.World;

import java.util.Optional;

public class Type implements RecipeType<BasicHexcraftingRecipe> {
    private Type(){}
    public static final Type INSTANCE=new Type();
    public static final String ID="basic_hexcrafting_recipe";

    @Override
    public <C extends Inventory> Optional<BasicHexcraftingRecipe> get(Recipe<C> recipe, World world, C inventory) {
        return RecipeType.super.get(recipe, world, inventory);
    }
}
