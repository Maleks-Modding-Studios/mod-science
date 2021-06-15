package malek.mod_science;

import malek.mod_science.components.player.madness.Madness;
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
import static me.shedaniel.autoconfig.annotation.ConfigEntry.*;
import static me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.*;
import static me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON;

@Config(name = MOD_ID)
public class ModConfig implements ConfigData {
    @ConfigEntry.Gui.TransitiveObject
    @ConfigEntry.Category("general")
    public General general = new General();
    @ConfigEntry.Gui.TransitiveObject
    @ConfigEntry.Category("madness")
    public MadnessConfig madness = new MadnessConfig();

    private static class MadnessFeature {
        @Tooltip boolean enable = true;
        @Tooltip double madnessThreshold = 0.0;
    }

    private static class MadnessRandomFeature extends MadnessFeature{
        @Tooltip double chance = 0.0f;
    }

    public static class MadnessConfig {

        @CollapsibleObject @Tooltip public LowMadness lowMadness = new LowMadness();
        public static class LowMadness {
            @Tooltip double lowMadnessThresholdAmount = 0.0;

            @CollapsibleObject @Tooltip public HealGolem healGolem = new HealGolem();
            @CollapsibleObject @Tooltip public GolemHealth golemHealth = new GolemHealth();
            @CollapsibleObject @Tooltip public RandomSaturationGain randomSaturationGain = new RandomSaturationGain();

            public static class HealGolem extends MadnessFeature {
                @Tooltip int healAmount;
                public HealGolem() {
                    enable = true;
                    madnessThreshold = 0.0;
                    healAmount = 1;
                }
            }
            public static class GolemHealth extends MadnessFeature {
                @Tooltip int extraHealth;
                public GolemHealth() {
                    enable = true;
                    madnessThreshold = 0.0;
                    extraHealth = 1;
                }
            }
            public static class RandomSaturationGain extends MadnessRandomFeature {
                @Tooltip int saturationAmount;
                public RandomSaturationGain(){
                    enable = true;
                    madnessThreshold = 0.0;
                    chance = 0.1;
                    saturationAmount = 1;
                }
            }


        }

        @CollapsibleObject @Tooltip public MediumMadness mediumMadness = new MediumMadness();
        public static class MediumMadness {
            @Tooltip double mediumMadnessThresholdAmount = 0.0;


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

        private Path getConfigPath() {
            return ModScienceInit.getConfigRoot().resolve(definition.name() + "-config.json5");
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
