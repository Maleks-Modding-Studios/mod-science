package malek.mod_science.damagesources;

import net.minecraft.entity.damage.DamageSource;

public class ModDamageSources {
    public static final DamageSource PHYSICAL = new PhysicalDamageSource("physical");
    public static final DamageSource BURN = new BurnDamageSource("burn");
    public static final DamageSource STASIS = new StasisDamageSource("stasis");
    public static final DamageSource FLUX = new FluxDamageSource("flux");
    public static final DamageSource DAMP = new DampDamageSource("damp");
    public static final DamageSource TRANSIENT = new TransientDamageSource("transient");
    public static final DamageSource CHRONIC = new ChronicDamageSource("chronic");
    public static final DamageSource POSSIBLE = new PossibleDamageSource("possible");
    public static final DamageSource CERTAIN = new CertainDamageSource("certain");

    //Slashing, Crushing, Piercing, Kinetic/Fall
    private static class PhysicalDamageSource extends DamageSource {
        public PhysicalDamageSource(String name) {
            super(name);
        }
    }

    //Fire/Soulfire, Heat/Magma, Lava, Acid
    private static class BurnDamageSource extends DamageSource {
        public BurnDamageSource(String name) {
            super(name);
            isFire();
        }
    }

    //Freezing, Petrification
    private static class StasisDamageSource extends DamageSource {
        public StasisDamageSource(String name) {
            super(name);
        }
    }
    //Lightning, Arc, Laser
    private static class FluxDamageSource extends DamageSource {
        public FluxDamageSource(String name) {
            super(name);
        }
    }
    //Drowning, Suffocation
    private static class DampDamageSource extends DamageSource {
        public DampDamageSource(String name) {
            super(name);
        }
    }
    //Space, Explosio
    private static class TransientDamageSource extends DamageSource {
        public TransientDamageSource(String name) {
            super(name);
        }
    }
    //Time, Wither
    private static class ChronicDamageSource extends DamageSource {
        public ChronicDamageSource(String name) {
            super(name);
        }
    }
    //Magic, Mutation
    private static class PossibleDamageSource extends DamageSource {
        public PossibleDamageSource(String name) {
            super(name);
            setUsesMagic();
        }
    }
    //Reality, Existential
    private static class CertainDamageSource extends DamageSource {
        public CertainDamageSource(String name) {
            super(name);
            setOutOfWorld();
        }
    }
}

