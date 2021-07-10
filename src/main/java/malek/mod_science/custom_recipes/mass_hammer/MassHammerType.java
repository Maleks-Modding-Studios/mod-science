package malek.mod_science.custom_recipes.mass_hammer;

import net.minecraft.recipe.RecipeType;

public class MassHammerType implements RecipeType<MassHammerRecipe> {
    private MassHammerType(){}
    public static final MassHammerType INSTANCE=new MassHammerType();
    public static final String ID="basic_infuser_recipe";
}