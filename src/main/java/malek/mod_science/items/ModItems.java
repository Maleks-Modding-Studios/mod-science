package malek.mod_science.items;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.dimdev.matrix.Matrix;
import org.dimdev.matrix.Registrar;
import org.dimdev.matrix.RegistryEntry;

import static malek.mod_science.ModScience.MOD_ID;

@Registrar(element = Item.class, modid = MOD_ID)
public final class ModItems {

    public static final ItemGroup MOD_SCIENCE = FabricItemGroupBuilder
            .create(new Identifier("modscience", "mod_science"))
            .icon(() -> new ItemStack(Items.GLOW_ITEM_FRAME))
            .build();

    @RegistryEntry("test")
    public static final Item TEST = new Item(new FabricItemSettings());

    @RegistryEntry("golem_repair_kit")
    public static final Item GOLEM_REPAIR_KIT = new GolemRepairKit(new FabricItemSettings().group(MOD_SCIENCE));


    public static void init() {
        Matrix.register(ModItems.class, Registry.ITEM);
    }

}

