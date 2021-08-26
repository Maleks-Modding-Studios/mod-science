package malek.mod_science.recipes;

import malek.mod_science.ModScience;
import malek.mod_science.recipes.hex_crafting.basic.BasicHexcraftingRecipe;
import malek.mod_science.recipes.hex_crafting.basic.BasicHexcraftingRecipeSerializer;
import malek.mod_science.recipes.hex_crafting.basic.BasicType;
import malek.mod_science.recipes.mass_hammer.MassHammerRecipeSerializer;
import malek.mod_science.recipes.mass_hammer.MassHammerType;
import malek.mod_science.recipes.oremagnet.OreMagnetRecipe;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModRecipes {
    public static SpecialRecipeSerializer<OreMagnetRecipe> ORE_MAGNET_SERIALIZER;

    public static void init() {
        //TODO FIX THIS SHIT SO IT DOESN"T SOMEHOW PREVENT CLIENTS FROM LOGGING ONTO SERVERS
        //B R U H
        Registry.register(Registry.RECIPE_SERIALIZER, MassHammerRecipeSerializer.ID, MassHammerRecipeSerializer.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, MassHammerRecipeSerializer.ID, MassHammerType.INSTANCE);
        Registry.register(Registry.RECIPE_SERIALIZER, BasicHexcraftingRecipeSerializer.ID, BasicHexcraftingRecipeSerializer.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, BasicHexcraftingRecipeSerializer.ID, BasicType.INSTANCE);
        ORE_MAGNET_SERIALIZER = Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(ModScience.MOD_ID, "ore_magnet_recipe_serializer"), new SpecialRecipeSerializer<>(OreMagnetRecipe::new));
    }
}
