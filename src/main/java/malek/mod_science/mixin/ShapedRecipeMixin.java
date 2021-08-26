package malek.mod_science.mixin;

import malek.mod_science.util.general.MixinUtil;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShapedRecipe.class)
public class ShapedRecipeMixin {
    /**
     * Not setup yet, gonna mixin into this once we figure out how we want to do tinkering.
     */
    //    @Inject(method = "matches", at = @At("HEAD"), cancellable = true)
//    public boolean matches(CraftingInventory craftingInventory, World world, CallbackInfoReturnable<Boolean> cir) {
//
//        return false;
//    }
}
