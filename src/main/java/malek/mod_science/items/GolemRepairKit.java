package malek.mod_science.items;

import malek.mod_science.components.player.madness.Madness;
import malek.mod_science.entities.golems.Golem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

import static malek.mod_science.components.player.madness.Madness.MADNESS;
import static malek.mod_science.components.player.madness.Madness.get;

public class GolemRepairKit extends Item {

    public GolemRepairKit(Settings settings){
        super(settings);
    }

    @Override
    public boolean isUsedOnRelease(ItemStack stack){
        return false;
    }
    @Override
    public UseAction getUseAction(ItemStack stack){
        return UseAction.CROSSBOW;
    }
    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand){
        if(user instanceof ServerPlayerEntity && entity instanceof Golem){
            entity.heal((float) get(user).getMadness()*(float) 5.0);
        }
        return use(user.getEntityWorld(), user, hand).getResult();
    }
    @Override
    public ActionResult useOnBlock(ItemUsageContext context){
        return ActionResult.FAIL;
    }


}
