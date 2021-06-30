package malek.mod_science.items;

import malek.mod_science.items.orbs_of_power.MoltenCore;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.ai.brain.task.FarmerVillagerTask;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.dimdev.matrix.Matrix;
import org.dimdev.matrix.Registrar;
import org.dimdev.matrix.RegistryEntry;
import org.lwjgl.system.CallbackI;

import static malek.mod_science.ModScience.MOD_ID;

@Registrar(element = Item.class, modid = MOD_ID)
public final class ModItems {

    public static final ItemGroup MOD_SCIENCE = FabricItemGroupBuilder.create(new Identifier("modscience", "mod_science")).icon(() -> new ItemStack(Items.GLOW_ITEM_FRAME)).build();
    public static final FabricItemSettings DEFAULT = new FabricItemSettings().group(MOD_SCIENCE);

    @RegistryEntry("golem_repair_kit")
    public static final Item GOLEM_REPAIR_KIT = new GolemRepairKit(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("test_scythe")
    public static final Item TEST_SCYTHE = new ScytheItem(ToolMaterials.IRON, 1, -2.8F, new FabricItemSettings().group(MOD_SCIENCE));


    //NO!!! NO!!!!!!!! WHAT MAKES YOU THINK THIS IS A GOOD IDEA!!!!! ALWAYS, AND I MEAN ALWAYS!!!!!!!!!!!!!!!!!! PUT THE ITEMS INTO FabricItemSettings.group(MOD_SCIENCE)!!!!!
    // IT TAKES .5 SECONDS GOOD LORD!!!! CLEAN UP YOUR DAMN CODE!!!!!!!!!!!!!!
    // I will do it this time but the next I... I dont know what I will do but it will be painful. PLEASE
    @RegistryEntry("void_dust")
    public static final Item VOID_DUST = new Item(new FabricItemSettings().group(MOD_SCIENCE));
    
    // aember items start
    @RegistryEntry("aember")
    public static final Item AEMBER = new Item(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("blue_aember")
    public static final Item BLUE_AEMBER = new Item(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("golden_aember")
    public static final Item GOLDEN_AEMBER = new Item(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("jet_black_aember")
    public static final Item JET_BLACK_AEMBER = new Item(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("white_aember")
    public static final Item WHITE_AEMBER = new Item(new FabricItemSettings().group(MOD_SCIENCE));
    // aember items end
    
    @RegistryEntry("shear_force")
    public static final Item SHEAR_FORCE = new ShearForce(new FabricItemSettings().group(MOD_SCIENCE));

    //start metal crafting items
    @RegistryEntry("amalgametal")
    public static final Item AMALGAMETAL = new Item(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("singulite_ingot")
    public static final Item SINGULITE_INGOT = new Item(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("ethaerial_ingot")
    public static final Item ETHAREAIL_INGOT = new Item(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("ethaerium_rod")
    public static final Item ETHAERIUM_ROD = new Item(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("beathril_bar")
    public static final Item BEATHRIL_BAR = new Item(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("darkwyn_ingot")
    public static final Item DARKWYN_INGOT = new Item(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("octiron_ingot")
    public static final Item OCTIRON_INGOT = new Item(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("moonsliver")
    public static final Item MOONSILVER = new Item(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("impervium_chunk")
    public static final Item IMPERVIUM_CHUNK = new Item(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("admintium_ingot")
    public static final Item ADMINTIUM_INGOT = new Item(new FabricItemSettings().group(MOD_SCIENCE));
    //end metal crafting items

    @RegistryEntry("gravitic_nucleus")
    public static final Item GRAVITIC_NUCLEUS = new Item(new FabricItemSettings().maxCount(1).group(MOD_SCIENCE));

    @RegistryEntry("molten_core")
    public static final Item MOLTEN_CORE = new MoltenCore(new FabricItemSettings().group(MOD_SCIENCE).fireproof());



    public static void init() {
        Matrix.register(ModItems.class, Registry.ITEM);
    }

}

