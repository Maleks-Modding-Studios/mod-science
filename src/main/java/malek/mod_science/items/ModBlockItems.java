package malek.mod_science.items;

import malek.mod_science.blocks.ModBlocks;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;
import org.dimdev.matrix.Matrix;
import org.dimdev.matrix.Registrar;
import org.dimdev.matrix.RegistryEntry;
import org.lwjgl.system.CallbackI;

import static malek.mod_science.ModScience.MOD_ID;
import static malek.mod_science.items.ModItems.MOD_SCIENCE;

@Registrar(element = Item.class, modid = MOD_ID)
public class ModBlockItems {

    public static final FabricItemSettings DEFAULT = new FabricItemSettings().group(MOD_SCIENCE);

    @RegistryEntry("shadowsilk_deepslate_ore")
    public static final Item SHADOWSILK_DEEPSLATE_ORE = new BlockItem(ModBlocks.SHADOWSILK_DEEPSLATE_ORE, DEFAULT);

    @RegistryEntry("aember_ore")
    public static final Item AEMBER_ORE = new BlockItem(ModBlocks.AEMBER_ORE, new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("test")
    public static final Item TEST = new BlockItem(ModBlocks.TEST, new FabricItemSettings().group(ItemGroup.MISC));

    @RegistryEntry("shadowsilk_stone_ore")
    public static final Item SHADOWSILK_ORE_ITEM = new BlockItem(ModBlocks.SHADOWSILK_STONE_ORE, new FabricItemSettings().group(MOD_SCIENCE));
    
    @RegistryEntry("flourcane_item")
    public static final Item FLOURCANE_ITEM = new BlockItem(ModBlocks.FLOURCANE_BLOCK, new FabricItemSettings().group(MOD_SCIENCE));
    
     @RegistryEntry("spircle_ore")
    public static final Item SPIRCALE_ORE = new BlockItem(ModBlocks.SPIRCALE_ORE, DEFAULT);
    
    @RegistryEntry("potato_ore_item")
    public static final Item POTATO_ORE_ITEM = new BlockItem(ModBlocks.POTATO_ORE, new FabricItemSettings().group(MOD_SCIENCE));

    public static void init() {
        Matrix.register(ModBlockItems.class, Registry.ITEM);
    }
}
