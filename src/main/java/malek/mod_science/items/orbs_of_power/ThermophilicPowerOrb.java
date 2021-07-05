package malek.mod_science.items.orbs_of_power;

import malek.mod_science.items.item_nbt.ChargeableItem;
import malek.mod_science.items.item_nbt.PowerLevel;
import malek.mod_science.util.general.LoggerInterface;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ThermophilicPowerOrb extends Item implements LoggerInterface {
    public ThermophilicPowerOrb(Settings settings) {
        super(settings);
    }
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PowerLevel.setPowerLevel(context.getStack(), PowerLevel.getPowerLevel(context.getStack())+1);
        if(PowerLevel.getPowerLevel(context.getStack()) > 11) {
            PowerLevel.setPowerLevel(context.getStack(), 0);
        }
        log(PowerLevel.getPowerLevel(context.getStack()) + "");
        return ActionResult.PASS;
    }
    @Override
    public Logger getLogger() {
        return LogManager.getLogger();
    }
}
