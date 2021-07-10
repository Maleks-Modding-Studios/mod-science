package malek.mod_science.effects;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEffects {
    // Create status effects as you would in the innit class
    public static final StatusEffect SLIPPERY = new Slippery();

    /*
    Register status effects here how you would in the mod initializer, this will run and register
    normally as if this were in the init class

    Lang token is "effect.mod_science.<path>"
    Effect texture is at "assets\mod_science\textures\mob_effect\<path>
     */
    public static void init() {
        Registry.register(Registry.STATUS_EFFECT, new Identifier("mod_science", "slippery"), SLIPPERY);
    }
}
