package malek.mod_science.blocks.blockentities;

import malek.mod_science.blocks.CalderaCauldron;
import malek.mod_science.blocks.ModBlocks;
import malek.mod_science.blocks.power.FireReceiverBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static malek.mod_science.ModScience.MOD_ID;

public class ModBlockEntities {
    public static final Identifier MATTER_CHAMBER = new Identifier(MOD_ID, "matter_cavitation_chamber_block_entity");


    public static BlockEntityType<ShadowSilkOreBlockEntity> SHADOW_SILK_STONE_ORE_BLOCK_ENTITY;
    public static BlockEntityType<MatterCavitationChamberBlockEntity> MATTER_CAVITATION_CHAMBER_BLOCK_ENTITY;
    public static BlockEntityType<FireReceiverBlockEntity> FIRE_POWER_HOLDER_BLOCK_ENTITY;
    public static BlockEntityType<CalderaCauldronBlockEntity> CALDERA_CAULDRON_BLOCK_ENTITY;

    public static void init() {
        SHADOW_SILK_STONE_ORE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, "shadowsilk_stone_ore_blockentity"), FabricBlockEntityTypeBuilder.create(ShadowSilkOreBlockEntity::new, ModBlocks.SHADOWSILK_STONE_ORE, ModBlocks.SHADOWSILK_DEEPSLATE_ORE).build(null));
        MATTER_CAVITATION_CHAMBER_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, MATTER_CHAMBER, FabricBlockEntityTypeBuilder.create(MatterCavitationChamberBlockEntity::new, ModBlocks.MATTER_CAVITATION_CHAMBER).build(null));
        FIRE_POWER_HOLDER_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, "fire_power_holder"), FabricBlockEntityTypeBuilder.create(FireReceiverBlockEntity::new, ModBlocks.FIRE_POWER_HOLDER).build(null));
        CALDERA_CAULDRON_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, "caldera_cauldron"), FabricBlockEntityTypeBuilder.create(CalderaCauldronBlockEntity::new, ModBlocks.CALDERA_CAULDRON).build(null));
    }
}
