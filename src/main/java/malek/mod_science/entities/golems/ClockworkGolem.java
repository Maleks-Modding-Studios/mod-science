package malek.mod_science.entities.golems;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class ClockworkGolem extends Golem{

    public ClockworkGolem(EntityType<ClockworkGolem> entityType, World world) {
        super(entityType, world, new Block(FabricBlockSettings.of(Material.STONE)), 1);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.inventory;
    }
}
