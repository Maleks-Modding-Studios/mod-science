package malek.mod_science.client;

import malek.mod_science.items.item_nbt.ChargeableItem;
import malek.mod_science.items.ModItems;
import malek.mod_science.items.item_nbt.PowerLevel;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;

import static malek.mod_science.util.item.ItemNbt.charged;
import static malek.mod_science.util.item.ItemNbt.powerLevel;

public class ItemModelProvider {
    private static void registerOrbModels() {
        FabricModelPredicateProviderRegistry.register(ModItems.MOLTEN_CORE, new Identifier(charged), (itemStack, clientWorld, livingEntity, i) -> ChargeableItem.isCharged(itemStack) ? 1.0F : 0.0F);
        FabricModelPredicateProviderRegistry.register(ModItems.LEYDEN_JAR, new Identifier(charged), (itemStack, clientWorld, livingEntity, i) -> ChargeableItem.isCharged(itemStack) ? 1.0F : 0.0F);
        FabricModelPredicateProviderRegistry.register(ModItems.THERMOPHILIC_POWER_ORB, new Identifier(powerLevel), (itemStack, clientWorld, livingEntity, i) -> ((float) PowerLevel.getPowerLevel(itemStack))/11);
    }
    public static void registerModels() {
        registerOrbModels();
    }
}
