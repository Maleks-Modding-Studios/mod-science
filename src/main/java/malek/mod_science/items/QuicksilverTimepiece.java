package malek.mod_science.items;

import malek.mod_science.components.world.timepiece.TimePieceUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class QuicksilverTimepiece extends Item {
    public QuicksilverTimepiece(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        itemStack.damage(1, user, (p) -> {
            p.sendToolBreakStatus(user.getActiveHand());
        });
        TimePieceUtils.setTimePieceTicks(user, 70);
        TimePieceUtils.setTimePieceUser(user);
        ((PlayerEntity)user).getItemCooldownManager().set(this, 3*20);
        user.setCurrentHand(hand);
        return TypedActionResult.consume(itemStack);
    }
}
