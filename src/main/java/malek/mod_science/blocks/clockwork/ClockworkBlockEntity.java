package malek.mod_science.blocks.clockwork;

import malek.mod_science.blocks.ModBlockEntities;
import malek.mod_science.items.ClockworkItem;
import malek.mod_science.items.ModItems;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.SoundKeyframeEvent;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class ClockworkBlockEntity extends BlockEntity implements IAnimatable, BlockEntityTicker {
    private final AnimationFactory factory = new AnimationFactory(this);

    private boolean on = false;

    public ClockworkBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CLOCKWORK_BLOCK_ENTITY, pos, state);
    }
    @Override
    public void tick(World world, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        on = state.get(ClockworkBlock.ON);
    }


    @SuppressWarnings("unchecked")
    private <E extends BlockEntity & IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().transitionLengthTicks = 0;
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.clockworkblock.idle", true));
        return PlayState.CONTINUE;
    }
    private <E extends BlockEntity & IAnimatable> PlayState predicate2(AnimationEvent<E> event) {
        if(event.getAnimatable().getWorld().getBlockState(pos).get(ClockworkBlock.ON)){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.clockworkblock.crank", true));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    @Override
    public void registerControllers(AnimationData data) {
        AnimationController<ClockworkItem> controller = new AnimationController(this, "controller", 0, this::predicate);
        data.addAnimationController(controller);
        AnimationController<ClockworkItem> controller2 = new AnimationController(this, "controller2", 0, this::predicate2);
        data.addAnimationController(controller2);
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

}