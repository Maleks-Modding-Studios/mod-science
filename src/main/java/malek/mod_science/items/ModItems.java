package malek.mod_science.items;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.dimdev.matrix.Matrix;
import org.dimdev.matrix.Registrar;
import org.dimdev.matrix.RegistryEntry;

import static malek.mod_science.ModScience.MOD_ID;

@Registrar(element = Item.class, modid = MOD_ID)
public final class ModItems {

    public static final ItemGroup MOD_SCIENCE = FabricItemGroupBuilder.create(new Identifier("modscience", "mod_science")).icon(() -> new ItemStack(Items.GLOW_ITEM_FRAME)).build();
    public static final FabricItemSettings DEFAULT = new FabricItemSettings().group(MOD_SCIENCE);

    @RegistryEntry("golem_repair_kit")
    public static final Item GOLEM_REPAIR_KIT = new GolemRepairKit(new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("test_scythe")
    public static final Item TEST_SCYTHE = new ScytheItem(ToolMaterials.IRON, 1, -2.8F, new FabricItemSettings().group(MOD_SCIENCE));

    @RegistryEntry("void_dust")
    public static final Item VOID_DUST = new Item(DEFAULT);


    public static void init() {
        Matrix.register(ModItems.class, Registry.ITEM);
    }

}

