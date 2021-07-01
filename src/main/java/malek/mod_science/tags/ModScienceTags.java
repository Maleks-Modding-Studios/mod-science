package malek.mod_science.tags;

import malek.mod_science.ModScience;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class ModScienceTags {
    public static final Tag<Block> SCYTHE_MINEABLE = TagRegistry.block(new Identifier(ModScience.MOD_ID, "mineable/scythe"));
    public static final Tag<Item> BURNS_IN_SUNLIGHT = TagRegistry.item(new Identifier(ModScience.MOD_ID, "burns_in_sunlight"));
}
