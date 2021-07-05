package malek.mod_science.items.orbs_of_power;

import malek.mod_science.items.item_nbt.ChargeableItem;
import malek.mod_science.items.item_nbt.PowerLevel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;

public class TempItem extends Item {
    public TempItem(Settings settings) {
        super(settings);
    }
    public ActionResult useOnBlock(ItemUsageContext context) {
        ChargeableItem.setCharged(context.getStack(), !ChargeableItem.isCharged(context.getStack()));
        return ActionResult.PASS;
    }
}
