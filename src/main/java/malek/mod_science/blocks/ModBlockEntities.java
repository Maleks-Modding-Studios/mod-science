package malek.mod_science.blocks;

import malek.mod_science.ModScience;
import malek.mod_science.blocks.CalderaCauldron.CalderaCauldronBlockEntity;
import malek.mod_science.blocks.MAD.MADBlockEntity;
import malek.mod_science.blocks.MatterCavitationChamber.MatterCavitationChamberBlockEntity;
import malek.mod_science.blocks.ShadowSilkOre.ShadowSilkOreBlockEntity;
import malek.mod_science.blocks.Tesseract.TesseractBlockEntity;
import malek.mod_science.blocks.TransfusionMatrix.TransfusionMatrixBlockEntity;
import malek.mod_science.power.FireReceiverBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static malek.mod_science.ModScience.MOD_ID;
import static malek.mod_science.ModScience.ModScienceId;

public class ModBlockEntities {
    public static final Identifier MATTER_CHAMBER = new Identifier(MOD_ID, "matter_cavitation_chamber_block_entity");


    public static BlockEntityType<ShadowSilkOreBlockEntity> SHADOW_SILK_STONE_ORE_BLOCK_ENTITY;
    public static BlockEntityType<MatterCavitationChamberBlockEntity> MATTER_CAVITATION_CHAMBER_BLOCK_ENTITY;
    public static BlockEntityType<FireReceiverBlockEntity> FIRE_POWER_HOLDER_BLOCK_ENTITY;
    public static BlockEntityType<CalderaCauldronBlockEntity> CALDERA_CAULDRON_BLOCK_ENTITY;
    public static BlockEntityType<TransfusionMatrixBlockEntity> TRANSFUSION_MATRIX_BLOCK_ENTITY;
    public static BlockEntityType<TesseractBlockEntity> TESSERACT_BLOCK_ENTITY;
    public static BlockEntityType<MADBlockEntity> MAD_BLOCK_ENTITY;

    public static void init() {
        SHADOW_SILK_STONE_ORE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, "shadowsilk_stone_ore_blockentity"), FabricBlockEntityTypeBuilder.create(ShadowSilkOreBlockEntity::new, ModBlocks.SHADOWSILK_STONE_ORE, ModBlocks.SHADOWSILK_DEEPSLATE_ORE).build(null));
        MATTER_CAVITATION_CHAMBER_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, MATTER_CHAMBER, FabricBlockEntityTypeBuilder.create(MatterCavitationChamberBlockEntity::new, ModBlocks.MATTER_CAVITATION_CHAMBER).build(null));
        FIRE_POWER_HOLDER_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, "fire_power_holder"), FabricBlockEntityTypeBuilder.create(FireReceiverBlockEntity::new, ModBlocks.FIRE_POWER_HOLDER).build(null));
        CALDERA_CAULDRON_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, "caldera_cauldron"), FabricBlockEntityTypeBuilder.create(CalderaCauldronBlockEntity::new, ModBlocks.CALDERA_CAULDRON).build(null));
        TRANSFUSION_MATRIX_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, "transfusion_matrix"), FabricBlockEntityTypeBuilder.create(TransfusionMatrixBlockEntity::new, ModBlocks.TRANSFUSION_MATRIX).build(null));
        TESSERACT_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, ModScience.ModScienceId("tesseract_block"), FabricBlockEntityTypeBuilder.create(TesseractBlockEntity::new, ModBlocks.TESSERACT_BLOCK).build(null));
        MAD_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, ModScienceId("mad_block_entity"), FabricBlockEntityTypeBuilder.create(MADBlockEntity::new, ModBlocks.MAD).build(null));
    }
}
