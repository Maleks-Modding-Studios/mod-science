package malek.mod_science.material.tool;

import malek.mod_science.items.ModItems;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class SinguliteMaterial implements ToolMaterial {

    @Override
    public int getDurability() {
        return 250;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 2.0F;
    }

    @Override
    public float getAttackDamage() {
        return 0.0F;
    }

    @Override
    public int getMiningLevel() {
        return 2;
    }

    @Override
    public int getEnchantability() {
        return 14;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(ModItems.SINGULITE_INGOT);
    }
}
