package malek.mod_science.recipies;
import net.minecraft.recipe.RecipeType;

public class Type implements RecipeType<BasicHexcraftingRecipe> {
    private Type(){}
    public static final Type INSTANCE=new Type();
    public static final String ID="basic_infuser_recipe";
}
