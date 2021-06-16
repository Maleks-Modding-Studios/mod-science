package malek.mod_science.items;

import malek.mod_science.blocks.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;
import org.dimdev.matrix.Matrix;
import org.dimdev.matrix.Registrar;
import org.dimdev.matrix.RegistryEntry;

import static malek.mod_science.ModScience.MOD_ID;

@Registrar(element = Item.class, modid = MOD_ID)
public class ModBlockItems extends Thread{

    public static final Registry<Item> REGISTRY = Registry.ITEM;

    @RegistryEntry("aember")
    public static final Item AEMBER = create(ModBlocks.AEMBER);


    private static Item create(Block block) {
        return create(new BlockItem(block, (new Item.Settings()).group(ItemGroup.MISC)));
    }

    private static Item create(Item item) {
        if (item instanceof BlockItem) {
            ((BlockItem) item).appendBlocks(Item.BLOCK_ITEMS, item);
        }

        return item;
    }
    public void run() {
        init();
    }
    public void init() {
        Matrix.register(ModItems.class, Registry.ITEM);
    }
}
