package malek.mod_science.material.tool;

import malek.mod_science.items.ModItems;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class BeathrillMaterial implements ToolMaterial {

    @Override
    public int getDurability() {
        return 1300;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 0.0F;
    }

    @Override
    public float getAttackDamage() {
        return 1.0F;
    }

    @Override
    public int getMiningLevel() {
        return 2;
    }

    @Override
    public int getEnchantability() {
        return 0;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(ModItems.BEATHRIL_BAR);
    }
}
