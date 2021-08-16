package malek.mod_science.biomes;

import malek.mod_science.blocks.ModBlocks;
import malek.mod_science.generation.ModGeneration;
import net.minecraft.block.Blocks;
import net.minecraft.sound.MusicSound;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

import static malek.mod_science.ModScience.MOD_ID;

public final class ModBiomes {
    public static final RegistryKey<Biome> PERSONAL_WHITE_VOID_KEY;
    protected static void addTheRoomStructure(GenerationSettings.Builder builder){
        builder.structureFeature(ModGeneration.THE_ROOM_FEATURE_CONFIGURED);
    }
    private static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> VOID_SURFACE_BUILDER = SurfaceBuilder.DEFAULT.withConfig(new TernarySurfaceConfig(Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), Blocks.VOID_AIR.getDefaultState()));
                    //    public static final RegistryKey<Biome> PUBLIC_BLACK_VOID_KEY;
//    public static final RegistryKey<Biome> DUNGEON_DANGEROUS_BLACK_VOID_KEY;
//    public static final RegistryKey<Biome> LIMBO_KEY;
    public static final Biome PERSONAL_WHITE_VOID_BIOME;
//    public static final Biome PUBLIC_BLACK_VOID_BIOME;
//    public static final Biome DUNGEON_DANGEROUS_BLACK_VOID_BIOME;
    //public static final Biome LIMBO_BIOME;

    public static void init() {
        //Registry.register(BuiltinRegistries.BIOME, LIMBO_KEY.getValue(), LIMBO_BIOME);
        Registry.register(BuiltinRegistries.BIOME, PERSONAL_WHITE_VOID_KEY.getValue(), PERSONAL_WHITE_VOID_BIOME);
        Registry.register(BuiltinRegistries.BIOME, new Identifier(MOD_ID, "wylds_desert"), WYLDS_DESERT);
//        Registry.register(BuiltinRegistries.BIOME, PUBLIC_BLACK_VOID_KEY.getValue(), PUBLIC_BLACK_VOID_BIOME);
//        Registry.register(BuiltinRegistries.BIOME, DUNGEON_DANGEROUS_BLACK_VOID_KEY.getValue(), DUNGEON_DANGEROUS_BLACK_VOID_BIOME);
//        // only ever needed if the biome api is broken
        //        BuiltinBiomesAccessor.getIdMap().put(BuiltinRegistries.BIOME.getRawId(LIMBO_BIOME), LIMBO_KEY);
        //        BuiltinBiomesAccessor.getIdMap().put(BuiltinRegistries.BIOME.getRawId(PERSONAL_WHITE_VOID_BIOME), PERSONAL_WHITE_VOID_KEY);
        //        BuiltinBiomesAccessor.getIdMap().put(BuiltinRegistries.BIOME.getRawId(PUBLIC_BLACK_VOID_BIOME), PUBLIC_BLACK_VOID_KEY);
        //        BuiltinBiomesAccessor.getIdMap().put(BuiltinRegistries.BIOME.getRawId(DUNGEON_DANGEROUS_BLACK_VOID_BIOME), DUNGEON_DANGEROUS_BLACK_VOID_KEY);

    }

    private static BiomeEffects createEffect(boolean white) {
        BiomeEffects.Builder builder = new BiomeEffects.Builder()
                .waterColor(0x3f76e4)
                .waterFogColor(0x50533)
                .fogColor(white ? 0xFFFFFF : 0xc0d8ff)
                .skyColor(white ? 0xFFFFFF : 0x111111)
                .grassColorModifier(BiomeEffects.GrassColorModifier.NONE);
        if (white) {

            //builder.music(new MusicSound(ModSoundEvents.WHITE_VOID, 0, 0, true));
        }
        return builder.build();
    }

    static {
        GenerationSettings.Builder VOID_GEN_SETTINGS_BUILDER = new GenerationSettings.Builder();
        addTheRoomStructure(VOID_GEN_SETTINGS_BUILDER);
        VOID_GEN_SETTINGS_BUILDER.surfaceBuilder(VOID_SURFACE_BUILDER);

        Biome.Builder voidBiomeBuilder = new Biome.Builder()
                .category(Biome.Category.NONE)
                .depth(0)
                .downfall(0)
                .generationSettings(VOID_GEN_SETTINGS_BUILDER.build())
                .precipitation(Biome.Precipitation.NONE)
                .scale(0)
                .spawnSettings(new SpawnSettings.Builder().build())
                .temperature(0.8f)
                .temperatureModifier(Biome.TemperatureModifier.NONE);
        PERSONAL_WHITE_VOID_KEY = RegistryKey.of(Registry.BIOME_KEY, new Identifier(MOD_ID, "white_void"));
//        PUBLIC_BLACK_VOID_KEY = RegistryKey.of(Registry.BIOME_KEY, new Identifier(MOD_ID, "black_void"));
//        DUNGEON_DANGEROUS_BLACK_VOID_KEY = RegistryKey.of(Registry.BIOME_KEY, new Identifier(MOD_ID+"dangerous_black_void"));
//        LIMBO_KEY = RegistryKey.of(Registry.BIOME_KEY, new Identifier(MOD_ID, "limbo"));
        PERSONAL_WHITE_VOID_BIOME = voidBiomeBuilder.effects(createEffect(true)).build();
//        PUBLIC_BLACK_VOID_BIOME = voidBiomeBuilder.effects(createEffect(false)).build();
//        DUNGEON_DANGEROUS_BLACK_VOID_BIOME = voidBiomeBuilder.effects(createEffect(false)).build();

        /*

        LIMBO_BIOME = new Biome.Builder()
                .category(Biome.Category.NONE)
                .depth(0.1f)
                .downfall(0.0f)
                .effects(new BiomeEffects.Builder()
                                 .fogColor(0x404040)
                                 .waterColor(0x101010)
                                 .foliageColor(0)
                                 .waterFogColor(0)
                                 .moodSound(new BiomeMoodSound(ModSoundEvents.CRACK, 6000, 8, 2.0))
                                 .music(new MusicSound(ModSoundEvents.CREEPY, 0, 0, true))
                                 .skyColor(0x404040)
                                 .grassColor(0)
                                 .particleConfig(new BiomeParticleConfig(new RiftParticleEffect(0.2f, 2000), 0.003F))
                                 .build()
                )
                .generationSettings(new GenerationSettings.Builder()
                                            .feature(
                                                    GenerationStep.Feature.SURFACE_STRUCTURES,
                                                    ModFeatures.LIMBO_GATEWAY_CONFIGURED_FEATURE
                                            )
                                            .feature(
                                                    GenerationStep.Feature.UNDERGROUND_ORES,
                                                    ModFeatures.SOLID_STATIC_ORE
                                            )
                                            .feature(GenerationStep.Feature.UNDERGROUND_ORES,
                                                     ModFeatures.DECAYED_BLOCK_ORE)
                                            .surfaceBuilder(
                                                    SurfaceBuilder.DEFAULT.withConfig(
                                                            new TernarySurfaceConfig(
                                                                    ModBlocks.UNFOLDED_BLOCK.getDefaultState(),
                                                                    ModBlocks.UNWARPED_BLOCK.getDefaultState(),
                                                                    ModBlocks.ETERNAL_FLUID.getDefaultState()
                                                            )
                                                    ))
                                            .build()
                )
                .precipitation(Biome.Precipitation.NONE)
                .scale(2F)
                .spawnSettings(new SpawnSettings.Builder()
                                       .creatureSpawnProbability(0.02f)
                                       .spawn(
                                               SpawnGroup.MONSTER,
                                               new SpawnSettings.SpawnEntry(
                                                       ModEntityTypes.MONOLITH,
                                                       1,
                                                       1,
                                                       1
                                               )
                                       )
                                       .build()
                )
                .temperature(0.2f)
                .temperatureModifier(Biome.TemperatureModifier.NONE)
                .build();
         */

    }
    // SurfaceBuilder defines how the surface of your biome looks.
    // We use custom surface builder for our biome to cover surface with obsidians.
    private static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> OBSIDIAN_SURFACE_BUILDER = SurfaceBuilder.DEFAULT
            .withConfig(new TernarySurfaceConfig(ModBlocks.REALITY_BLOCK_CYAN.getDefaultState(),
                    ModBlocks.REALITY_BLOCK_CYAN.getDefaultState(),
                                                 ModBlocks.REALITY_BLOCK_RED.getDefaultState()));

    private static final Biome WYLDS_DESERT = createObsiland();

    private static Biome createObsiland() {
        // We specify what entities spawn and what features generate in the biome.
        // Aside from some structures, trees, rocks, plants and
        //   custom entities, these are mostly the same for each biome.
        // Vanilla configured features for biomes are defined in DefaultBiomeFeatures.

        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addFarmAnimals(spawnSettings);
        DefaultBiomeFeatures.addMonsters(spawnSettings, 95, 5, 100);

        GenerationSettings.Builder generationSettings = new GenerationSettings.Builder();
        generationSettings.surfaceBuilder(OBSIDIAN_SURFACE_BUILDER);
        DefaultBiomeFeatures.addDefaultUndergroundStructures(generationSettings);
        DefaultBiomeFeatures.addLandCarvers(generationSettings);
        DefaultBiomeFeatures.addDefaultLakes(generationSettings);
        DefaultBiomeFeatures.addDungeons(generationSettings);
        DefaultBiomeFeatures.addMineables(generationSettings);
        DefaultBiomeFeatures.addDefaultOres(generationSettings);
        DefaultBiomeFeatures.addDefaultDisks(generationSettings);
        DefaultBiomeFeatures.addSprings(generationSettings);
        DefaultBiomeFeatures.addFrozenTopLayer(generationSettings);

        return (new Biome.Builder())
                .precipitation(Biome.Precipitation.RAIN)
                .category(Biome.Category.DESERT)
                .depth(0.125F)
                .scale(0.05F)
                .temperature(0.8F)
                .downfall(0.4F)
                .effects((new BiomeEffects.Builder())
                                 .waterColor(0x3f76e4)
                                 .waterFogColor(0x050533)
                                 .fogColor(0xc0d8ff)
                                 .skyColor(0x77adff)
                                 .build())
                .spawnSettings(spawnSettings.build())
                .generationSettings(generationSettings.build())
                .build();
    }
}