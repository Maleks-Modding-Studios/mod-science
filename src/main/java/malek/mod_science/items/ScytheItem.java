package malek.mod_science.items;

import draylar.magna.api.MagnaTool;
import malek.mod_science.tags.ModScienceTags;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolMaterial;

public class ScytheItem extends MiningToolItem implements MagnaTool {

    public ScytheItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(attackDamage, attackSpeed, material, ModScienceTags.SCYTHE_MINEABLE, settings);
    }

    @Override
    public int getRadius(ItemStack stack) {
        // This is a 5x5
        return 2;
    }

    @Override
    public int getDepth(ItemStack stack) {
        return 3;
    }

    @Override
    public boolean playBreakEffects() {
        return false;
    }
}
