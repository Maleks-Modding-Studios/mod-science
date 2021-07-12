package malek.mod_science.recipes.mass_hammer;

import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.ArrayList;

public class MassHammerRecipe implements Recipe<MassHammerInventory> {
    public final ArrayList<Ingredient> ingredients;
    public final ItemStack result;
    private final Identifier id;
    public MassHammerRecipe(ArrayList<Ingredient> ingredientArrayList, ItemStack result, Identifier id) {
        this.ingredients = ingredientArrayList;
        this.result = result;
        this.id = id;
    }

    @Override
    public boolean matches(MassHammerInventory inv, World world) {
        System.out.println(inv.getStack(0));
        System.out.println(inv.getStack(1));
        if(ingredients.get(0).test(inv.getStack(0)) && ingredients.get(1).test(inv.getStack(1))) {
            return true;
        }
        return ingredients.get(0).test(inv.getStack(1)) && ingredients.get(1).test(inv.getStack(0));
    }

    @Override
    public ItemStack craft(MassHammerInventory inv) {
        inv.itemEntity1.world.spawnEntity(new ItemEntity(inv.itemEntity1.world, inv.itemEntity1.getX(), inv.itemEntity1.getY(), inv.itemEntity1.getZ(), getOutput().copy()));
        inv.itemEntity1.getStack().decrement(1);
        inv.itemEntity2.getStack().decrement(1);
        if(inv.itemEntity1.getStack().getCount() == 0) {
            inv.itemEntity1.kill();
        }
        if(inv.itemEntity2.getStack().getCount() == 0) {
            inv.itemEntity2.kill();
        }
        return getOutput().copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getOutput() {
        return this.result;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return MassHammerRecipeSerializer.INSTANCE;
    }


    @Override
    public RecipeType<?> getType() {
        return MassHammerType.INSTANCE;
    }
}
