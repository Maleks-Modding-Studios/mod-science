package malek.mod_science.mixin;

import malek.mod_science.items.ModItems;
import malek.mod_science.util.general.LoggerInterface;
import malek.mod_science.util.general.MixinUtil;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.brain.task.SeekSkyTask;
import net.minecraft.entity.ai.goal.AvoidSunlightGoal;
import net.minecraft.item.Item;
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
public class ItemEntityMixin implements LoggerInterface {
    private static final Item[] itemsThatWeDoStuffWith = {
            ModItems.VOID_DUST
    };
    private static final Item[] itemsThatBurnInSunlight = {
            ModItems.VOID_DUST
    };
    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void tickMixin(CallbackInfo ci) {
        ItemEntity itemEntity = MixinUtil.cast(this);
        Item thisItem = itemEntity.getStack().getItem();
        if(Arrays.stream(itemsThatWeDoStuffWith).anyMatch((item -> item == thisItem))) {
            if(Arrays.stream(itemsThatBurnInSunlight).anyMatch((item -> item == thisItem))) {
                if (itemEntity.getEntityWorld().isSkyVisible(itemEntity.getBlockPos())) {
                    if (!itemEntity.isOnFire()) itemEntity.setOnFireFor(3);
                } else if (itemEntity.isOnFire()) {
                    itemEntity.setOnFire(false);
                }
            }


        }
    }


    @Override
    public Logger getLogger() {
        return LogManager.getLogger();
    }
}
