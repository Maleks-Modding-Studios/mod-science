package malek.mod_science.recipes.hex_crafting.upgraded;
import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.world.World;

import java.util.Optional;

public class UpgradedType implements RecipeType<UpgradedHexcraftingRecipe> {
    private UpgradedType(){}
    public static final UpgradedType INSTANCE = new UpgradedType();
    public static final String ID="upgraded_hexcrafting_recipe";

    @Override
    public <C extends Inventory> Optional<UpgradedHexcraftingRecipe> match(Recipe<C> recipe, World world, C inventory) {
        return RecipeType.super.match(recipe, world, inventory);
    }
}
