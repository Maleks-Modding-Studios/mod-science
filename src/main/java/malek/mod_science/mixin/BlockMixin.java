package malek.mod_science.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Block.class)
public abstract class BlockMixin {

    @Shadow
    protected abstract void spawnBreakParticles(World world, PlayerEntity player, BlockPos pos, BlockState state);

    /**
     * @author - MalekiRe
     */
    @Overwrite
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        this.spawnBreakParticles(world, player, pos, state);
        if (state.isIn(BlockTags.GUARDED_BY_PIGLINS)) {
            PiglinBrain.onGuardedBlockInteracted(player, false);
        }

        world.emitGameEvent(player, GameEvent.BLOCK_DESTROY, pos);
    }


}
