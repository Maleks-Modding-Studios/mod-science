package malek.mod_science.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.ItemEntity;
import net.minecraft.util.ActionResult;

public interface ItemEntityTickEvent {
    Event<ItemEntityTickEvent> EVENT = EventFactory.createArrayBacked(ItemEntityTickEvent.class, callbacks -> itemEntity -> {
        ActionResult result;
        for (ItemEntityTickEvent callback : callbacks) {
            result = callback.doTick(itemEntity);
            if (result != ActionResult.PASS) {
                return result;
            }
        }
        return ActionResult.PASS;
    });

    ActionResult doTick(ItemEntity itemEntity);
}
