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

@Config(name = MOD_ID)
public class ModConfig implements ConfigData {
    @ConfigEntry.Gui.TransitiveObject
    @ConfigEntry.Category("general")
    private General general = new General();






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
            return ModScience.getConfigRoot().resolve(definition.name() + "-config.json5");
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
