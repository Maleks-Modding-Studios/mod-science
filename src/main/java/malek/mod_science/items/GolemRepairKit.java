package malek.mod_science.items;

import malek.mod_science.entities.golems.GolemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.UseAction;

import static malek.mod_science.components.player.madness.Madness.get;

public class GolemRepairKit extends BreakableItem {

    public GolemRepairKit(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        ActionResult breakCheck = use(user.getEntityWorld(), user, hand).getResult();
        if (breakCheck != ActionResult.FAIL && user instanceof ServerPlayerEntity && entity instanceof GolemEntity) {
            entity.heal((float) get(user).getMadness() * (float) 5.0);
        }

        return breakCheck;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.CROSSBOW;
    }

    @Override
    public boolean isUsedOnRelease(ItemStack stack) {
        return false;
    }

}
