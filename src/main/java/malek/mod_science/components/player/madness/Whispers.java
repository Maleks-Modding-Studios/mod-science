package malek.mod_science.components.player.madness;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class Whispers {
    private static final String BASE_STRING = "mod_science.madness.whispers";

    public static Text getWhisperText(int selector, Entity entity, ItemStack item) {
        if (selector == 2 && item.isEmpty()) {
            // Default to quiet cackling if empty-handed.
            selector = 1;
        }
        return new TranslatableText(BASE_STRING + selector, entity.getDisplayName(), item.toHoverableText());
    }
}
