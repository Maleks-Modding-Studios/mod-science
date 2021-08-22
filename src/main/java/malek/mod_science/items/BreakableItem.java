package malek.mod_science.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

/**
 * A {@link BreakableItem} is an item that can be broken by improper use.
 * When broken, it gains the boolean tag "broken" set to true.
 * An item should not have the "broken" tag set to false.
 * Using a broken item will fail early and play a sound.
 */
public class BreakableItem extends Item {

    public BreakableItem(Settings settings) {
        super(settings);
    }

    /**
     * @return Fails if the item is broken. Passes otherwise.
     */
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        NbtCompound tag = stack.getOrCreateNbt();

        // TODO spark check the player here and break the item if they fail.
        if (false) {
            tag.putBoolean("broken", true);
        }

        if (tag.contains("broken")) {
            // TODO play defunct sound
            return tag.getBoolean("broken") ? TypedActionResult.fail(stack) : TypedActionResult.pass(stack);
        }

        return TypedActionResult.pass(stack);
    }
}
