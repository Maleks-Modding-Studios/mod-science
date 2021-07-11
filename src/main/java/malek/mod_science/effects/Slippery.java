package malek.mod_science.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;

public class Slippery extends StatusEffect {
    // Status effect settings
    public Slippery() {
        super(StatusEffectType.HARMFUL, // Sets if the effect is positive, neutral, or negative
              0x6eeeeb); // Hex code of the particle color (0x=#)
    }

    /*
    Code for if applyUpdateEvent can run at all
    In this case it would run every tick
     */
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    /*
    Code to execute if the above returns as true
     */
    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {

    }
}
