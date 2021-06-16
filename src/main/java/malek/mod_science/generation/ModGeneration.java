package malek.mod_science.generation;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.*;

import static malek.mod_science.ModScience.MOD_ID;

public class ModGeneration {
    private static final Feature<DefaultFeatureConfig> LEY_KNOT_GEN = new LeyKnotFeature(DefaultFeatureConfig.CODEC);
    public static final ConfiguredFeature<?, ?> CONFIGURED_LEY_KNOT_GEN = LEY_KNOT_GEN.configure(DefaultFeatureConfig.DEFAULT);

    public static void init() {
        Registry.register(Registry.FEATURE, new Identifier(MOD_ID, "ley_knot_gen"), LEY_KNOT_GEN);
        RegistryKey<ConfiguredFeature<?, ?>> stoneSpiral = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY,
                new Identifier(MOD_ID, "ley_knot_gen"));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, stoneSpiral.getValue(), CONFIGURED_LEY_KNOT_GEN);
        BiomeModifications.addFeature(BiomeSelectors.all(), GenerationStep.Feature.RAW_GENERATION, stoneSpiral);
    }
}
