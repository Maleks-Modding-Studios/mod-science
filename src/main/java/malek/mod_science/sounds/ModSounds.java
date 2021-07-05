package malek.mod_science.sounds;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static malek.mod_science.ModScience.MOD_ID;

public class ModSounds {
    public static final Identifier ELEVATOR_MUSIC_ID = new Identifier(MOD_ID, "elevator_music");
    public static SoundEvent ELEVATOR_MUSIC = new SoundEvent(ELEVATOR_MUSIC_ID);

    public static void init() {
        Registry.register(Registry.SOUND_EVENT, ELEVATOR_MUSIC_ID, ELEVATOR_MUSIC);
    }
}
