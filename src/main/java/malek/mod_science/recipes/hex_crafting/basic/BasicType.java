package malek.mod_science.recipes.hex_crafting.basic;
import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.world.World;

import java.util.Optional;

public class BasicType implements RecipeType<BasicHexcraftingRecipe> {
    private BasicType(){}
    public static final BasicType INSTANCE = new BasicType();
    public static final String ID="basic_hexcrafting_recipe";

    @Override
    public <C extends Inventory> Optional<BasicHexcraftingRecipe> match(Recipe<C> recipe, World world, C inventory) {
        return RecipeType.super.match(recipe, world, inventory);
    }
}
