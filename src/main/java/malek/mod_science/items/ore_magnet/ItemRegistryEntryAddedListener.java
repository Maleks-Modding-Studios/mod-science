package malek.mod_science.items.ore_magnet;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;


public class ItemRegistryEntryAddedListener implements RegistryEntryAddedCallback<Item> {
    private final ModScienceItemRegistrar registrar;

    public ItemRegistryEntryAddedListener(ModScienceItemRegistrar registrar) {
        this.registrar = registrar;
    }

    @Override
    public void onEntryAdded(int rawId, Identifier id, Item object) {
        registrar.handleEntry(id, object);
    }
}
