package malek.mod_science.recipes.oremagnet;

import malek.mod_science.ModScience;
import malek.mod_science.items.ModItems;
import malek.mod_science.recipes.ModRecipes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class OreMagnetRecipe extends SpecialCraftingRecipe {
    public OreMagnetRecipe(Identifier id) {
        super(id);
    }
    private static final Set<Item> items = new HashSet<>();
    static {
        items.add(ModItems.ORE_MAGNET);
    }
    @Override
    public boolean matches(CraftingInventory inventory, World world) {
            return inventory.containsAny(items) && getBlockItemInInventory(inventory).isPresent();
    }

    @Override
    public ItemStack craft(CraftingInventory inventory) {
        ItemStack stack = new ItemStack(ModItems.ORE_MAGNET);
        NbtCompound compound = stack.getOrCreateNbt();
        BlockItem item = getBlockItemInInventory(inventory).get();
        compound.putString("block", Registry.BLOCK.getId(item.getBlock()).toString());
        return stack;
    }

    public Optional<BlockItem> getBlockItemInInventory(CraftingInventory inventory) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.getStack(i).getItem() instanceof BlockItem) {
                return Optional.of((BlockItem) inventory.getStack(i).getItem());
            }
        }
        return Optional.empty();
    }

    @Environment(EnvType.CLIENT)
    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.ORE_MAGNET_SERIALIZER;
    }
}
