package malek.mod_science.tags;

import malek.mod_science.ModScience;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import org.lwjgl.system.CallbackI;

public class ModScienceTags {
    public static final Tag<Block> SCYTHE_MINEABLE = TagRegistry.block(ModScience.ModScienceId("mineable/scythe"));
    public static final Tag<Item> BURNS_IN_SUNLIGHT = TagRegistry.item(ModScience.ModScienceId("burns_in_sunlight"));
    public static final Tag<Block> PIPE_CONNECTS_TO = TagRegistry.block(ModScience.ModScienceId("mod_science_pipe_connects_to"));
    public static final Tag<Block> PIPE = TagRegistry.block(ModScience.ModScienceId("mod_science_valid_pipe"));//yes I did it
    public static final Tag<Block> GENERATOR = TagRegistry.block(ModScience.ModScienceId("mod_science_valid_generator"));

}
