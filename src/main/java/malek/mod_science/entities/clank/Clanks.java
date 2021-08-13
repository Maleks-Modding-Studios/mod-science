package malek.mod_science.entities.clank;

import malek.mod_science.ModScience;
import malek.mod_science.entities.clank.variants.*;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.Registry;

public final class Clanks {
    public static final DefaultedRegistry<Clank> REGISTRY = FabricRegistryBuilder.createDefaulted(Clank.class, ModScience.createIdentifier("clank"), ModScience.createIdentifier("clockworker")).buildAndRegister();

    public static final Clank CLOCKWORKER = new Clockworker();
    public static final Clank SPARKWORKER = new Sparkworker();
    public static final Clank CLOCKCOPTER = new Clockcopter();
    public static final Clank THINKBOT = new Thinkbot();
    public static final Clank CATOMATON = new Catomaton();
    public static final Clank CLOCKWORK_GOLEM = new ClockworkGolem();
    public static final Clank STEALTH_GOLEM = new StealthGolem();
    public static final Clank AETHERNAL_AUTOMATON = new AethernalAutomaton();
    public static final Clank MOLTEN_GIANT = new MoltenGiant();
    public static final Clank STRONGBOX = new Strongbox();
    public static final Clank CAGE_SPIDER = new CageSpider();
    public static final Clank SUBSIDIARY_LOCOMATON = new SubsidiaryLocomaton();
    public static final Clank GIANT_SLIME_GOLEM = new GiantSlimeGolem();

    private Clanks() {
    }
    
    public static void init() {
        register("clockworker", CLOCKWORKER);
        register("sparkworker", SPARKWORKER);
        register("clockcopter", CLOCKCOPTER);
        register("thinkbot", THINKBOT);
        register("catomaton", CATOMATON);
        register("clockwork_golem", CLOCKWORK_GOLEM);
        register("stealth_golem", STEALTH_GOLEM);
        register("aethernal_automaton", AETHERNAL_AUTOMATON);
        register("molten_giant", MOLTEN_GIANT);
        register("strongbox", STRONGBOX);
        register("cage_spider", CAGE_SPIDER);
        register("subsidiary_locomaton", SUBSIDIARY_LOCOMATON);
        register("giant_slime_golem", GIANT_SLIME_GOLEM);
    }

    private static void register(String key, Clank clank) {
        Registry.register(REGISTRY, ModScience.createIdentifier(key), clank);
    }

    public static Clank getFromIdentifier(Identifier identifier) {
        return REGISTRY.get(identifier);
    }

    public static Identifier getIdentifierFrom(Clank clank) {
        return REGISTRY.getId(clank);
    }
}
