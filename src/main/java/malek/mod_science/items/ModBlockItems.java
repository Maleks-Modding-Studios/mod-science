package malek.mod_science.items;

import malek.mod_science.blocks.ModBlocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;
import org.dimdev.matrix.Matrix;
import org.dimdev.matrix.Registrar;
import org.dimdev.matrix.RegistryEntry;

import static malek.mod_science.ModScience.MOD_ID;

@Registrar(element = Item.class, modid = MOD_ID)
public class ModBlockItems {

    /*
    @RegistryEntry("aember")
    public static final Item AEMBER = new BlockItem(ModBlocks.AEMBER, (new Item.Settings()).group(ItemGroup.MISC));


     */
    public static void init() {
        Matrix.register(ModBlockItems.class, Registry.ITEM);
    }
}
