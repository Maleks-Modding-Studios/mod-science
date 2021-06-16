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

/*
    @RegistryEntry("test")
    public static final Item TEST = new Item(new FabricItemSettings());

    @RegistryEntry("test2")
    public static final Item TEST2 = new Item(new FabricItemSettings());

    @RegistryEntry("test4")
    public static final Item TEST5 = new Item(new FabricItemSettings());

    @RegistryEntry("test6")
    public static final Item TEST7 = new Item(new FabricItemSettings());

    @RegistryEntry("test8")
    public static final Item TEST9 = new Item(new FabricItemSettings());

    @RegistryEntry("test11")
    public static final Item TEST12 = new Item(new FabricItemSettings());

    @RegistryEntry("test13")
    public static final Item TEST14 = new Item(new FabricItemSettings());

    @RegistryEntry("test15")
    public static final Item TEST16 = new Item(new FabricItemSettings());

    @RegistryEntry("test17")
    public static final Item TEST18 = new Item(new FabricItemSettings());

    @RegistryEntry("test19")
    public static final Item TEST29 = new Item(new FabricItemSettings());

    @RegistryEntry("test29")
    public static final Item TEST24 = new Item(new FabricItemSettings());


 */


    public static void init() {
        Matrix.register(ModItems.class, Registry.ITEM);
        for(int i = 0; i < 100; i++) {
            Registry.register(Registry.ITEM, new Identifier(MOD_ID, "tester"+i), new Item(new FabricItemSettings()));
        }
    }
}
