package malek.mod_science.blocks;


import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.item.PickaxeItem;
import net.minecraft.util.registry.Registry;
import org.dimdev.matrix.Matrix;
import org.dimdev.matrix.Registrar;
import org.dimdev.matrix.RegistryEntry;

import static malek.mod_science.ModScience.MOD_ID;

@Registrar(element = Block.class, modid = MOD_ID)
public final class ModBlocks {

    private static final FabricBlockSettings DEFAULT = FabricBlockSettings.of(Material.STONE).strength(0.3F, 0.3F);
    private static final FabricBlockSettings ORE = FabricBlockSettings.of(Material.STONE);

    @RegistryEntry("aember")
    public static final Block AEMBER = register(new Block(ORE));

    private static Block register(Block block) {
        return block;
    }

    public static void init() {
        Matrix.register(ModBlocks.class, Registry.BLOCK);
    }
    @Environment(EnvType.CLIENT)
    public static void initClient() {

    }
}
