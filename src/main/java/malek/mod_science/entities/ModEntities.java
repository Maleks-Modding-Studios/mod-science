package malek.mod_science.entities;

import malek.mod_science.entities.golems.ClockworkGolemEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static malek.mod_science.ModScience.MOD_ID;

public class ModEntities {
    public static final EntityType<ClockworkGolemEntity> CLOCKWORK_GOLEM_ENTITY_TYPE = Registry.register(Registry.ENTITY_TYPE, new Identifier(MOD_ID, "clockword_golem"), FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ClockworkGolemEntity::new).dimensions(EntityDimensions.fixed(0.75f, 0.75f)).build());

    public static void init() {
        FabricDefaultAttributeRegistry.register(CLOCKWORK_GOLEM_ENTITY_TYPE, ClockworkGolemEntity.createMobAttributes());
    }
}
