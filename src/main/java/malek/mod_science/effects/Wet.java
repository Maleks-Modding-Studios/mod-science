package malek.mod_science.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.particle.*;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.Vec3d;

public class Wet extends StatusEffect {

    public Wet() {
        super(StatusEffectType.NEUTRAL,
              0x20C620);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (!entity.getBlockStateAtPos().getFluidState().isIn(FluidTags.LAVA)) {
            Vec3d vec3d = entity.getVelocity();
            entity.extinguish();
            entity.getEntityWorld().addParticle(ParticleTypes.DRIPPING_WATER, entity.getX(), entity.getY(), entity.getZ(), vec3d.x, vec3d.y, vec3d.z);
            if (entity.getBlockStateAtPos().isIn(BlockTags.FIRE)) {
                entity.getEntityWorld().removeBlock(entity.getBlockPos(), false);
            }
        } else
            entity.removeStatusEffect(ModEffects.WET);
    }
}
