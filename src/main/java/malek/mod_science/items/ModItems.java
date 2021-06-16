package malek.mod_science.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.dimdev.matrix.Matrix;
import org.dimdev.matrix.Registrar;
import org.dimdev.matrix.RegistryEntry;

import static malek.mod_science.ModScience.MOD_ID;

@Registrar(element = Item.class, modid = MOD_ID)
public final class ModItems {
    @RegistryEntry("test")
    public static final Item TEST = new Item(new FabricItemSettings());

    public static void init() {
        Matrix.register(ModItems.class, Registry.ITEM);
    }
}
