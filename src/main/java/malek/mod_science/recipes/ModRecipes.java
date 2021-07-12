package malek.mod_science.recipes;

import malek.mod_science.ModScience;
import malek.mod_science.recipes.mass_hammer.MassHammerRecipeSerializer;
import malek.mod_science.recipes.mass_hammer.MassHammerType;
import net.minecraft.util.registry.Registry;

public class ModRecipes {
    public static void init() {
        Registry.register(Registry.RECIPE_SERIALIZER, MassHammerRecipeSerializer.ID, MassHammerRecipeSerializer.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, ModScience.ModScienceId("mass_hammer_recipe"), MassHammerType.INSTANCE);
    }
}
