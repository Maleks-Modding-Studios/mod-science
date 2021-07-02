package malek.mod_science.generation;

import com.google.common.collect.ImmutableList;
import malek.mod_science.blocks.ModBlocks;
import malek.mod_science.generation.genUtils.ModScienceScatteredOreFeature;
import malek.mod_science.generation.genUtils.OreBlockStates;
import malek.mod_science.generation.genUtils.SpiracleOreFeature;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.block.Block;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.*;

import static malek.mod_science.ModScience.MOD_ID;

public class ModGeneration {
    public static final TagMatchRuleTest POTATO_TARGETS = new TagMatchRuleTest(BlockTags.DIRT);


    private static final Feature<DefaultFeatureConfig> LEY_KNOT_GEN = new LeyKnotFeature(DefaultFeatureConfig.CODEC);
    public static final ConfiguredFeature<?, ?> CONFIGURED_LEY_KNOT_GEN = LEY_KNOT_GEN.configure(DefaultFeatureConfig.DEFAULT);
    public static final Feature<OreFeatureConfig> POTATO_ORE_GEN = new OreFeature(OreFeatureConfig.CODEC);
    public static final Feature<OreFeatureConfig> SPIRACLE_ORE_GEN = new SpiracleOreFeature(OreFeatureConfig.CODEC);
    // public static final Feature<OreFeatureConfig> SHADOWSILK_ORE_GEN = new (OreFeatureConfig.CODEC);
    public static final Feature<OreFeatureConfig> SHADOWSILK_ORE_GEN = new ModScienceScatteredOreFeature(OreFeatureConfig.CODEC);
    public static final ConfiguredFeature<?, ?> CONFIGURED_SHADOWSILK_ORE_GEN = SHADOWSILK_ORE_GEN.configure(new OreFeatureConfig(OreFeatureConfig.Rules.STONE_ORE_REPLACEABLES, OreBlockStates.SHADOWSILK_STONE_ORE_STATE, 8, 0.5F)).triangleRange(YOffset.fixed(-64), YOffset.fixed(10)).repeat(200).spreadHorizontally();
    public static final ConfiguredFeature<?, ?> CONFIGURED_SHADOWSILK_DEEPSLATE_ORE_GEN = SHADOWSILK_ORE_GEN.configure(new OreFeatureConfig(OreFeatureConfig.Rules.DEEPSLATE_ORE_REPLACEABLES, OreBlockStates.SHADOWSILK_DEEPSLATE_ORE_STATE, 8, 0.5F)).triangleRange(YOffset.fixed(-64), YOffset.fixed(10)).repeat(200).spreadHorizontally();
    public static final ConfiguredFeature<?, ?> CONFIGURED_POTATO_ORE_GEN = POTATO_ORE_GEN.configure(new OreFeatureConfig(POTATO_TARGETS, ModBlocks.POTATO_ORE.getDefaultState(), 17, 0.5F)).triangleRange(YOffset.fixed(50), YOffset.fixed(256)).repeat(20).spreadHorizontally();
    public static final ConfiguredFeature<?, ?> CONFIGURED_SPIRACLE_ORE_GEN = SPIRACLE_ORE_GEN.configure(new OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, ModBlocks.SPIRCALE_ORE.getDefaultState(), 10, 0.1F)).triangleRange(YOffset.fixed(0), YOffset.fixed(40)).repeat(15).spreadHorizontally();




//    private static <FC extends FeatureConfig> ConfiguredFeature<FC, ?> registerConfiguredFeature(String id, ConfiguredFeature<FC, ?> configuredFeature) {
//        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, id, configuredFeature);
//    }
//
//    private static <C extends FeatureConfig, F extends Feature<C>> F registerFeature(String name, F feature) {
//        return Registry.register(Registry.FEATURE, name, feature);
//    }

    public static void init() {
        Registry.register(Registry.FEATURE, new Identifier(MOD_ID, "ley_knot_gen"), LEY_KNOT_GEN);
        Registry.register(Registry.FEATURE, new Identifier(MOD_ID, "shadowsilk_ore_gen"), SHADOWSILK_ORE_GEN);
        Registry.register(Registry.FEATURE, new Identifier(MOD_ID, "potato_ore_gen"), POTATO_ORE_GEN);
        Registry.register(Registry.FEATURE, new Identifier(MOD_ID, "spiracle_gen"), SPIRACLE_ORE_GEN);
        //Registry.register(Registry.FEATURE, new Identifier(MOD_ID, "shadowsilk_deepslate_ore_gen"), SHADOWSILK_ORE_GEN);
        RegistryKey<ConfiguredFeature<?, ?>> stoneSpiral = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, new Identifier(MOD_ID, "ley_knot_gen"));
        RegistryKey<ConfiguredFeature<?, ?>> yeetus = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, new Identifier(MOD_ID, "shadowsilk_ore_gen"));
        RegistryKey<ConfiguredFeature<?, ?>> fetus = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, new Identifier(MOD_ID, "shadowsilk_deepslate_ore_gen"));
        RegistryKey<ConfiguredFeature<?, ?>> potat = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, new Identifier(MOD_ID, "potato_ore_gen"));
        RegistryKey<ConfiguredFeature<?, ?>> spiracle = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, new Identifier(MOD_ID, "spiracle_gen"));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, stoneSpiral.getValue(), CONFIGURED_LEY_KNOT_GEN);
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, yeetus.getValue(), CONFIGURED_SHADOWSILK_ORE_GEN);
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, fetus.getValue(), CONFIGURED_SHADOWSILK_DEEPSLATE_ORE_GEN);
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, potat.getValue(), CONFIGURED_POTATO_ORE_GEN);
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, spiracle.getValue(), CONFIGURED_SPIRACLE_ORE_GEN);


        BiomeModifications.addFeature(BiomeSelectors.all(), GenerationStep.Feature.RAW_GENERATION, stoneSpiral);

        BiomeModifications.addFeature(BiomeSelectors.all(), GenerationStep.Feature.UNDERGROUND_ORES, fetus);
        BiomeModifications.addFeature(BiomeSelectors.all(), GenerationStep.Feature.UNDERGROUND_ORES, yeetus);
        BiomeModifications.addFeature(BiomeSelectors.all(), GenerationStep.Feature.UNDERGROUND_ORES, spiracle);

    }
}
