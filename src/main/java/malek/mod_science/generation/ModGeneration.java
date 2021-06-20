package malek.mod_science.generation;

import malek.mod_science.generation.genUtils.ModScienceScatteredOreFeature;
import malek.mod_science.generation.genUtils.OreBlockStates;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.*;

import static malek.mod_science.ModScience.MOD_ID;

public class ModGeneration {
    private static <FC extends FeatureConfig> ConfiguredFeature<FC, ?> registerConfiguredFeature(String id, ConfiguredFeature<FC, ?> configuredFeature) {
        return (ConfiguredFeature)Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, id, configuredFeature);
    }
    private static <C extends FeatureConfig, F extends Feature<C>> F registerFeature(String name, F feature) {
        return (F) Registry.register(Registry.FEATURE, name, feature);
    }

    private static final Feature<DefaultFeatureConfig> LEY_KNOT_GEN = new LeyKnotFeature(DefaultFeatureConfig.CODEC);
    public static final ConfiguredFeature<?, ?> CONFIGURED_LEY_KNOT_GEN = LEY_KNOT_GEN.configure(DefaultFeatureConfig.DEFAULT);
//    public static final Feature<OreFeatureConfig> SHADOWSILK_ORE_GEN = new (OreFeatureConfig.CODEC);
    public static final Feature<OreFeatureConfig> SHADOWSILK_ORE_GEN = new ModScienceScatteredOreFeature(OreFeatureConfig.CODEC);
    public static final ConfiguredFeature<?, ?> CONFIGURED_SHADOWSILK_ORE_GEN = (ConfiguredFeature)((ConfiguredFeature)SHADOWSILK_ORE_GEN.configure(new OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, OreBlockStates.SHADOW_ORE_STATE, 3, 0.5F)).triangleRange(YOffset.fixed(-64), YOffset.fixed(10))).spreadHorizontally();



    public static void init() {
        Registry.register(Registry.FEATURE, new Identifier(MOD_ID, "ley_knot_gen"), LEY_KNOT_GEN);
        Registry.register(Registry.FEATURE, new Identifier(MOD_ID, "shadowsilk_ore_gen"), SHADOWSILK_ORE_GEN);
        RegistryKey<ConfiguredFeature<?, ?>> stoneSpiral = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY,
                new Identifier(MOD_ID, "ley_knot_gen"));
        RegistryKey<ConfiguredFeature<?, ?>> yeetus = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, new Identifier(MOD_ID, "shadowsilk_ore_gen"));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, stoneSpiral.getValue(), CONFIGURED_LEY_KNOT_GEN);
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, yeetus.getValue(), CONFIGURED_SHADOWSILK_ORE_GEN);
        BiomeModifications.addFeature(BiomeSelectors.all(), GenerationStep.Feature.RAW_GENERATION, stoneSpiral);

        BiomeModifications.addFeature(BiomeSelectors.all(), GenerationStep.Feature.UNDERGROUND_ORES, yeetus);

    }
}
