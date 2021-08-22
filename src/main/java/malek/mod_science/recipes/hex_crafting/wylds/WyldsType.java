package malek.mod_science.recipes.hex_crafting.wylds;
import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.world.World;

import java.util.Optional;

public class WyldsType implements RecipeType<WyldsHexcraftingRecipe> {
    private WyldsType(){}
    public static final WyldsType INSTANCE = new WyldsType();
    public static final String ID="wylds_hexcrafting_recipe";

    @Override
    public <C extends Inventory> Optional<WyldsHexcraftingRecipe> match(Recipe<C> recipe, World world, C inventory) {
        return RecipeType.super.match(recipe, world, inventory);
    }
}
