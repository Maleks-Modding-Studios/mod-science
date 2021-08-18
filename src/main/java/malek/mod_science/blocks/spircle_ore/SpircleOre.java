package malek.mod_science.blocks.spircle_ore;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;

import java.util.Random;

import static malek.mod_science.blocks.spircle_ore.OreType.DIAMOND;
import static malek.mod_science.blocks.spircle_ore.OreType.SpircleOreProperties.ORE_TYPE;

public class SpircleOre extends Block {
    Random random = new Random();
    public SpircleOre(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(ORE_TYPE, DIAMOND));
    }
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return getDefaultState().with(ORE_TYPE, (OreType) (ORE_TYPE.getValues().toArray()[random.nextInt(ORE_TYPE.getValues().size()-1)]));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(ORE_TYPE);
    }

}
