package malek.mod_science.mixin;

import malek.mod_science.items.ModItems;
import malek.mod_science.items.item_nbt.ChargeableItem;
import malek.mod_science.util.general.LoggerInterface;
import malek.mod_science.util.general.MixinUtil;
import net.minecraft.block.Blocks;
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
            ModItems.VOID_DUST, ModItems.MOLTEN_CORE
    };
    private static final Item[] itemsThatBurnInSunlight = {
            ModItems.VOID_DUST
    };

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void tickMixin(CallbackInfo ci) {
        ItemEntity itemEntity = MixinUtil.cast(this);
        Item thisItem = itemEntity.getStack().getItem();
        if (Arrays.stream(itemsThatWeDoStuffWith).anyMatch((item -> item == thisItem))) {
            //If the item is on of the items that set on fire when exposed to sunlight, then we do that.
            if (Arrays.stream(itemsThatBurnInSunlight).anyMatch((item -> item == thisItem))) {
                if (itemEntity.getEntityWorld().isSkyVisible(itemEntity.getBlockPos())) {
                    if (!itemEntity.isOnFire()) itemEntity.setOnFireFor(3);
                } else if (itemEntity.isOnFire()) {
                    itemEntity.setOnFire(false);
                }
            }
            //If a uncharged molten core is in a lava block, it turns into a charged core, and destroys the lava.
            if (thisItem == ModItems.MOLTEN_CORE
                && !ChargeableItem.isCharged(itemEntity.getStack())
                && itemEntity.getEntityWorld().getBlockState(itemEntity.getBlockPos()).getBlock() == Blocks.LAVA) {
                ChargeableItem.setCharged(itemEntity.getStack(), true);
                itemEntity.getEntityWorld().setBlockState(itemEntity.getBlockPos(), Blocks.AIR.getDefaultState(), 3);
            }


        }
    }


    @Override
    public Logger getLogger() {
        return LogManager.getLogger();
    }
}
