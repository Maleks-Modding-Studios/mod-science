package malek.mod_science.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;

public class Incurable extends StatusEffect {

    public Incurable() {
        super(StatusEffectType.HARMFUL,
              0x350C0C);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        entity.timeUntilRegen = 2000;
    }
}
