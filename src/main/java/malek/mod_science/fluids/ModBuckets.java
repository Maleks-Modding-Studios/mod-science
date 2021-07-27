package malek.mod_science.fluids;




import malek.mod_science.items.ModItems;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.dimdev.matrix.Matrix;
import org.dimdev.matrix.Registrar;
import org.dimdev.matrix.RegistryEntry;

import static malek.mod_science.ModScience.MOD_ID;
import static malek.mod_science.ModScience.ModScienceId;
import static malek.mod_science.fluids.ModFluids.STILL_ENDER_DEW;
import static malek.mod_science.fluids.ModFluids.STILL_REWATER;


public class ModBuckets {

    public static Item REWATER_BUCKET;
    public static Item ENDER_DEW_BUCKET;
    public static Item GLIMMER_BUCKET;

    public static void init() {
        REWATER_BUCKET = Registry.register(Registry.ITEM, new Identifier(MOD_ID, "rewater_bucket"), new BucketItem(STILL_REWATER, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1).group(ModItems.MOD_SCIENCE)));
        ENDER_DEW_BUCKET = Registry.register(Registry.ITEM, ModScienceId("ender_dew_bucket"), new BucketItem(STILL_ENDER_DEW, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1).group(ModItems.MOD_SCIENCE)));
        GLIMMER_BUCKET = Registry.register(Registry.ITEM, ModScienceId("glimmer_bucket"), new BucketItem(STILL_ENDER_DEW, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1).group(ModItems.MOD_SCIENCE)));

    }

}
