package malek.mod_science;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.ConfigSerializer;
import me.shedaniel.autoconfig.util.Utils;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Jankson;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static malek.mod_science.ModScience.MOD_ID;
import static me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.CollapsibleObject;
import static me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.Tooltip;

@Config(name = MOD_ID)
public class ModConfig implements ConfigData {
    @ConfigEntry.Gui.TransitiveObject
    @ConfigEntry.Category("general")
    public General general = new General();
    @ConfigEntry.Gui.TransitiveObject
    @ConfigEntry.Category("madness")
    public MadnessConfig madness = new MadnessConfig();

    private static class MadnessFeature {
        @Tooltip
        public boolean enable = true;
        @Tooltip
        public double madnessThreshold = 0.0;
    }

    private static class MadnessRandomFeature extends MadnessFeature {
        @Tooltip
        public double chance = 0.0f;
    }

    public static class MadnessConfig {

        @CollapsibleObject
        @Tooltip
        public LowMadness lowMadness = new LowMadness();
        @CollapsibleObject
        @Tooltip
        public MediumMadness mediumMadness = new MediumMadness();

        public static class LowMadness {
            @Tooltip
            public double thresholdAmount = 100.0;

            @CollapsibleObject
            @Tooltip
            public HealGolem healGolem = new HealGolem();
            @CollapsibleObject
            @Tooltip
            public GolemHealth golemHealth = new GolemHealth();
            @CollapsibleObject
            @Tooltip
            public RandomSaturationGain randomSaturationGain = new RandomSaturationGain();


            public static class HealGolem extends MadnessFeature {
                @Tooltip
                int healAmount;

                public HealGolem() {
                    enable = true;
                    madnessThreshold = 0.0;
                    healAmount = 1;
                }
            }

            public static class GolemHealth extends MadnessFeature {
                @Tooltip
                int extraHealth;

                public GolemHealth() {
                    enable = true;
                    madnessThreshold = 0.0;
                    extraHealth = 1;
                }
            }

            public static class RandomSaturationGain extends MadnessRandomFeature {
                @Tooltip
                public int saturationAmount;

                public RandomSaturationGain() {
                    enable = true;
                    madnessThreshold = 0.0;
                    chance = 0.1;
                    saturationAmount = 10;
                }
            }
        }

        public static class MediumMadness {
            @Tooltip
            public double thresholdAmount = 500.0;
            @CollapsibleObject
            @Tooltip
            public RandomEnchant randomEnchant = new RandomEnchant();
            @Tooltip
            public int xpMultiplier = 0;
            @CollapsibleObject
            @Tooltip
            public RandomSpectatorSpawn randomSpectatorSpawn = new RandomSpectatorSpawn();

            public static class RandomEnchant extends MadnessRandomFeature {
                public RandomEnchant() {
                    enable = true;
                    madnessThreshold = 0.0;
                    chance = 0.0;
                }
            }

            public static class RandomSpectatorSpawn extends MadnessRandomFeature {
                public RandomSpectatorSpawn() {
                    enable = true;
                    madnessThreshold = 0.0;
                    chance = 0.0;
                }
            }
        }

        public static class HighMadness {
            @Tooltip
            public double thresholdAmount = 1000.0;

        }

        public static class ExtremeMadness {
            @Tooltip
            public double thresholdAmount = 2000.0;

        }
    }

    public static class General {

    }

    public static class SubRootJanksonConfigSerializer<T extends ConfigData> implements ConfigSerializer<T> {
        private static final Jankson JANKSON = Jankson.builder().build();
        private final Config definition;
        private final Class<T> configClass;

        public SubRootJanksonConfigSerializer(Config definition, Class<T> configClass) {
            this.definition = definition;
            this.configClass = configClass;
        }

        @Override
        public void serialize(T config) throws SerializationException {
            Path configPath = getConfigPath();
            try {
                Files.createDirectories(configPath.getParent());
                BufferedWriter writer = Files.newBufferedWriter(configPath);
                writer.write(JANKSON.toJson(config).toJson(true, true));
                writer.close();
            } catch (IOException e) {
                throw new SerializationException(e);
            }
        }

        private Path getConfigPath() {
            return ModScienceInit.getConfigRoot().resolve(definition.name() + "-config.json5");
        }

        @Override
        public T deserialize() throws SerializationException {
            Path configPath = getConfigPath();
            if (Files.exists(configPath)) {
                try {
                    return JANKSON.fromJson(JANKSON.load(getConfigPath().toFile()), configClass);
                } catch (Throwable e) {
                    throw new SerializationException(e);
                }
            } else {
                return createDefault();
            }
        }

        @Override
        public T createDefault() {
            return Utils.constructUnsafely(configClass);
        }
    }
}
