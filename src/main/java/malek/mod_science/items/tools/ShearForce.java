package malek.mod_science.items.tools;

import malek.mod_science.tags.ModScienceTags;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ShearsItem;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.TagGroup;
import net.minecraft.tag.TagManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;

public class ShearForce extends ShearsItem {
    public ShearForce(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if(context.getWorld().isClient()) {
            return ActionResult.PASS;
        }
        BlockPos.iterateOutwards(context.getBlockPos(), 10, 10, 10).forEach((pos) -> {
            Block block = context.getWorld().getBlockState(pos).getBlock();
            if(ModScienceTags.SOFT_BLOCK.contains(block) || BlockTags.WOOL.contains(block) || BlockTags.LEAVES.contains(block) || BlockTags.BUTTONS.contains(block) || BlockTags.CROPS.contains(block)) {
                context.getWorld().breakBlock(pos, true);
                context.getPlayer().getItemCooldownManager().set(this, 20);
            }
        });
        return ActionResult.PASS;
    }

}