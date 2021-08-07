package malek.mod_science.effects;

import malek.mod_science.ModScience;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.lwjgl.system.CallbackI;

public class ModEffects {
    // Create status effects as you would in the innit class
    public static final StatusEffect SLIPPERY = new Slippery();
    public static final StatusEffect STICKY = new Sticky();
    public static final StatusEffect INCURABLE = new Incurable();
    public static final StatusEffect WET = new Wet();

    /*
    Register status effects here how you would in the mod initializer, this will run and register
    normally as if this were in the init class

    Lang token is "effect.mod_science.<path>"
    Effect texture is at "assets\mod_science\textures\mob_effect\<path>
     */
    public static void init() {
        Registry.register(Registry.STATUS_EFFECT, new Identifier(ModScience.MOD_ID, "slippery"), SLIPPERY);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(ModScience.MOD_ID, "sticky"), STICKY);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(ModScience.MOD_ID, "incurable"), INCURABLE);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(ModScience.MOD_ID, "wet"), WET);
    }
}
