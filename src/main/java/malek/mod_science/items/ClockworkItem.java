package malek.mod_science.items;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.SoundKeyframeEvent;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class ClockworkItem extends Item implements IAnimatable {
    public AnimationFactory factory = new AnimationFactory(this);
    int ticker = 0;
    public ClockworkItem(Settings settings) {
        super(settings);
    }
    private <P extends Item & IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.clockwork.idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        AnimationController<ClockworkItem> controller = new AnimationController(this, "controller", 20, this::predicate);
        controller.registerSoundListener(this::soundListener);
        data.addAnimationController(controller);
    }


    @SuppressWarnings("resource")
    private <ENTITY extends IAnimatable> void soundListener(SoundKeyframeEvent<ENTITY> event) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if(player.getInventory().contains(new ItemStack(ModItems.CLOCKWORK))){
            player.playSound(SoundEvents.BLOCK_LEVER_CLICK, 0.5F, 1.75F);
        }
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
