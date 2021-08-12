package malek.mod_science.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.Vec3d;

public class Sticky extends StatusEffect {

    public Sticky() {
        super(StatusEffectType.HARMFUL,
              0x20C620);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        Vec3d vec3d = entity.getVelocity();
        if (!entity.getBlockStateAtPos().getFluidState().isIn(FluidTags.WATER)) {
            if (amplifier >= 2) {
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 20));
            }
        } else
            entity.removeStatusEffect(ModEffects.STICKY);
    }
}
