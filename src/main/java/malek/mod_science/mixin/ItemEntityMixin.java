package malek.mod_science.mixin;

import malek.mod_science.event.ItemEntityTickEvent;
import malek.mod_science.items.ModItems;
import malek.mod_science.items.item_nbt.ChargeableItem;
import malek.mod_science.util.general.LoggerInterface;
import malek.mod_science.util.general.MixinUtil;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.brain.task.SeekSkyTask;
import net.minecraft.entity.ai.goal.AvoidSunlightGoal;
import net.minecraft.item.Item;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity implements LoggerInterface {

    public ItemEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void tickMixin(CallbackInfo ci) {
        ActionResult result = ItemEntityTickEvent.EVENT.invoker().doTick(MixinUtil.cast(this));
        switch (result) {
            case CONSUME:
                this.discard();
            case SUCCESS:
                ci.cancel();
        }
    }


    @Override
    public Logger getLogger() {
        return LogManager.getLogger();
    }
}
