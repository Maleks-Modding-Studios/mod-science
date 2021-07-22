package malek.mod_science.tags;

import malek.mod_science.ModScience;
import malek.mod_science.util.general.PlayerUtil;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.lwjgl.system.CallbackI;

public class ModScienceTags {
    public static final Tag<Block> SCYTHE_MINEABLE = TagRegistry.block(ModScience.ModScienceId("mineable/scythe"));
    public static final Tag<Item> BURNS_IN_SUNLIGHT = TagRegistry.item(ModScience.ModScienceId("burns_in_sunlight"));
    public static final Tag<Block> PIPE_CONNECTS_TO = TagRegistry.block(ModScience.ModScienceId("mod_science_pipe_connects_to"));
    public static final Tag<Block> GENERATOR = TagRegistry.block(ModScience.ModScienceId("mod_science_generator"));
    public static final Tag<Block> RECEIVER = TagRegistry.block(ModScience.ModScienceId("mod_science_receiver"));
    public static final Tag<Block> PIPE = TagRegistry.block(ModScience.ModScienceId("mod_science_valid_pipe"));//yes I did it
    public static final Tag<Block> ORES = TagRegistry.block(new Identifier("c:ores"));

    public static void init() {
    }
}
