package malek.mod_science.blocks.blockentities;

import malek.mod_science.blocks.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.lwjgl.system.CallbackI;

import static malek.mod_science.ModScience.MOD_ID;

public class ModBlockEntities {
    public static BlockEntityType<ShadowSilkOreBlockEntity> SHADOW_SILK_STONE_ORE_BLOCK_ENTITY;
    public static void init() {
        SHADOW_SILK_STONE_ORE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, "shadowsilk_stone_ore_blockentity"), FabricBlockEntityTypeBuilder.create(ShadowSilkOreBlockEntity::new, ModBlocks.SHADOWSILK_STONE_ORE, ModBlocks.SHADOWSILK_DEEPSLATE_ORE).build(null));

    }
}
