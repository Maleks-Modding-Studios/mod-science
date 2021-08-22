package malek.mod_science.blocks;

import malek.mod_science.util.general.LoggerInterface;
import malek.mod_science.util.general.MultiblockStructure;
import malek.mod_science.util.general.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;

public class BlockMultiblockTest extends Block implements LoggerInterface {
    MultiblockStructure multiblockStructure;

    public BlockMultiblockTest(Settings settings) {
        super(settings);
        multiblockStructure = new MultiblockStructure(2, 2, 2);
        HashSet<BlockState> firstBlock = new HashSet();
        HashSet<BlockState> secondBlock = new HashSet<>();
        firstBlock.add(Blocks.GRASS_BLOCK.getDefaultState());
        secondBlock.add(Blocks.STONE.getDefaultState());
        multiblockStructure.setBlockState(0, 0, 0, firstBlock);
        multiblockStructure.setBlockState(1, 1, 1, secondBlock);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        log(WorldUtil.multiblockMatches(world, pos, multiblockStructure) + "");
        super.onPlaced(world, pos, state, placer, itemStack);
    }

    @Override
    public Logger getLogger() {
        return LogManager.getLogger();
    }
}
