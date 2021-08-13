package malek.mod_science.items;

import dev.architectury.platform.Mod;
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
    
    @RegistryEntry("flourcane")
    public static final Item FLOURCANE_ITEM = new BlockItem(ModBlocks.FLOURCANE_BLOCK, new FabricItemSettings().group(MOD_SCIENCE));
    
     @RegistryEntry("spircle_ore")
    public static final Item SPIRCALE_ORE = new BlockItem(ModBlocks.SPIRCALE_ORE, DEFAULT);
    
    @RegistryEntry("potato_ore")
    public static final Item POTATO_ORE_ITEM = new BlockItem(ModBlocks.POTATO_ORE, new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("receiver")
    public static final Item POWER_HOLDER = new BlockItem(ModBlocks.FIRE_POWER_HOLDER, new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("steam_pipe")
    public static final Item POWER_PIPE = new BlockItem(ModBlocks.STEAM_PIPE, new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("generator")
    public static final Item POWER_GENERATOR = new BlockItem(ModBlocks.FIRE_POWER_GENERATOR, new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("matter_cavitation_chamber")
    public static final Item MATTER_CAVITATION_CHAMBER = new BlockItem(ModBlocks.MATTER_CAVITATION_CHAMBER, new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("caldera_cauldron")
    public static final Item CALDERA_CAULDRON = new BlockItem(ModBlocks.CALDERA_CAULDRON, new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("transfusion_matrix")
    public static final Item TRANSFUSION_MATRIX = new BlockItem(ModBlocks.TRANSFUSION_MATRIX, new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("tesseract_block")
    public static final Item TESSERACT_BLOCK = new BlockItem(ModBlocks.TESSERACT_BLOCK, new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("mad")
    public static final Item MAD = new BlockItem(ModBlocks.MAD, new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("crystal_growth")
    public static final Item CRYSTAL_GROWTH = new BlockItem(ModBlocks.CRYSTAL_GROWTH, new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("lava_stride")
    public static final Item LAVA_STRIDE = new BlockItem(ModBlocks.LAVA_STRIDE_BLOCK, new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("water_stride")
    public static final Item WATER_STRIDE = new BlockItem(ModBlocks.WATER_STRIDE_BLOCK, new FabricItemSettings().group(MOD_SCIENCE));


    public static void init() {
        Matrix.register(ModBlockItems.class, Registry.ITEM);
    }
}
