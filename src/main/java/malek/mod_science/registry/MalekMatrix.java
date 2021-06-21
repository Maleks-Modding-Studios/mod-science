package malek.mod_science.registry;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.dimdev.matrix.Registrar;
import org.dimdev.matrix.RegistryEntry;

import java.lang.reflect.Modifier;
import java.util.Arrays;

public class MalekMatrix {

    /**
     * @param clazz
     *         The class that should be scanned for registry entries.
     * @param registry
     *         The registry that entries should be registered to.
     */

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void register(Class<?> clazz, Registry<?> registry) {
        Registrar registrar = clazz.getAnnotation(Registrar.class);
        if (registrar == null) {
            return;
        }

        String modid = registrar.modid();
        Class<?> element = registrar.element();


        Arrays.stream(clazz.getFields()).filter(field -> field.isAnnotationPresent(RegistryEntry.class) && Modifier.isPublic(field.getModifiers()) && Modifier.isStatic(field.getModifiers()) && element.isAssignableFrom(field.getType())).forEach(field -> {
            try {
                Object value = field.get(null);
                if (value instanceof CreateMinecraftObj) {
                    ((CreateMinecraftObj) value).start();
                } else {
                    throw new Exception("Has A None 'CreateMinecraftObject' in a exclusive create minecraft object class setup!");
                }
            } catch (IllegalAccessException e) {
                throw new AssertionError(e);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Arrays.stream(clazz.getFields()).filter(field -> field.isAnnotationPresent(RegistryEntry.class) && Modifier.isPublic(field.getModifiers()) && Modifier.isStatic(field.getModifiers()) && element.isAssignableFrom(field.getType())).forEach(field -> {
            try {
                Object value = field.get(null);
                if (value instanceof CreateMinecraftObj) {
                    ((CreateMinecraftObj) value).join();
                    Registry.register((Registry) registry, new Identifier(modid, field.getAnnotation(RegistryEntry.class).value()), ((CreateMinecraftObj) value).get());
                }
            } catch (IllegalAccessException | InterruptedException e) {
                throw new AssertionError(e);
            }
        });

        // TODO Can we delete this? -Platy
        /*
        Arrays.stream(clazz.getFields())
              .filter(field -> field.isAnnotationPresent(RegistryEntry.class)
                               && Modifier.isPublic(field.getModifiers())
                               && Modifier.isStatic(field.getModifiers())
                               && element.isAssignableFrom(field.getType())
              )
              .forEach(field -> {
                  try {
                      Object value = field.get(null);
                      Registry.register((Registry) registry, new Identifier(modid, field.getAnnotation(RegistryEntry.class).value()), element.cast(value));
                      if (value instanceof BlockItem) {
                          Item.BLOCK_ITEMS.put(((BlockItem) value).getBlock(), (Item) value);
                      }
                  } catch (IllegalAccessException e) {
                      throw new AssertionError(e);
                  }
              });

         */
    }
}