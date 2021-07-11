package malek.mod_science.items;

import malek.mod_science.custom_recipes.mass_hammer.MassHammerInventory;
import malek.mod_science.custom_recipes.mass_hammer.MassHammerRecipe;
import malek.mod_science.custom_recipes.mass_hammer.MassHammerType;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        ArrayList<ItemEntity> itemEntities = (ArrayList<ItemEntity>) world.getEntitiesByType(TypeFilter.instanceOf(ItemEntity.class), new Box(-1 + pos.getX(), -1 + pos.getY(), -1 + pos.getZ(), 2 + pos.getX(), 2 + pos.getY(), 2 + pos.getZ()), (s) -> true);
        if(itemEntities.size() >= 2) {
            massHammerInventory.setItemEntity1(itemEntities.get(0));
            massHammerInventory.setItemEntity2(itemEntities.get(1));
            List<MassHammerRecipe> match = world.getRecipeManager().getAllMatches(MassHammerType.INSTANCE, massHammerInventory, world);
            for (MassHammerRecipe massHammerRecipe : match) {
                if (massHammerRecipe.matches(massHammerInventory, world)) {
                    massHammerRecipe.craft(massHammerInventory);
                }
            }
        }
    }
}
