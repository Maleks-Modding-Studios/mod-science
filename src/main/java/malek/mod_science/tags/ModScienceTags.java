package malek.mod_science.tags;

import malek.mod_science.ModScience;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class ModScienceTags {
    public static final Tag<Block> SCYTHE_MINEABLE = TagRegistry.block(new Identifier(ModScience.MOD_ID, "mineable/scythe"));
}
