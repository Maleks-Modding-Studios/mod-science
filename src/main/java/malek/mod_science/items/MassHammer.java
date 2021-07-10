package malek.mod_science.items;

import malek.mod_science.custom_recipes.mass_hammer.MassHammerInventory;
import malek.mod_science.items.item_nbt.ChargeableItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class MassHammer extends Item {
    public MassHammer(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
//        if(!ChargeableItem.isCharged(context.getStack())) {
//            return ActionResult.PASS;
//        }
        if(context.getWorld().isClient) {
            return ActionResult.PASS;
        }
        ChargeableItem.setCharged(context.getStack(), false);
        tryCraft(context.getWorld(), context.getPlayer(), context.getStack(), context.getBlockPos(), context);
        return ActionResult.PASS;
    }
    private void tryCraft(World world, PlayerEntity player, ItemStack stack, BlockPos pos, ItemUsageContext context) {
        MassHammerInventory massHammerInventory = new MassHammerInventory();
        world.getEntitiesByType(TypeFilter.instanceOf(ItemEntity.class), new Box(-1, -1, -1, 1, 1, 1), (s) -> true).stream().forEach((itemEntity -> System.out.println(itemEntity)));
    }
}
