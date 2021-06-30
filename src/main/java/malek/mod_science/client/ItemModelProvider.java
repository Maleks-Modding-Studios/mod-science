package malek.mod_science.client;

import malek.mod_science.items.item_nbt.ChargeableItem;
import malek.mod_science.items.ModItems;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;

import static malek.mod_science.util.item.ItemNbt.charged;

public class ItemModelProvider {
    private static void registerOrbModels() {
        FabricModelPredicateProviderRegistry.register(ModItems.MOLTEN_CORE, new Identifier(charged), (itemStack, clientWorld, livingEntity, i) -> ChargeableItem.isCharged(itemStack) ? 1.0F : 0.0F);
    }
    public static void registerModels() {
        registerOrbModels();
    }
}
