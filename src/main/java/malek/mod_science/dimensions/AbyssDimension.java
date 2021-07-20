package malek.mod_science.dimensions;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

import static malek.mod_science.ModScience.MOD_ID;

public class AbyssDimension {

    public static final RegistryKey<World> ABYSS_WORLD_KEY = RegistryKey.of(Registry.WORLD_KEY, new Identifier(MOD_ID, "abyss"));

    public static void init() {

    }
}
