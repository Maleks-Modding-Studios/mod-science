package malek.mod_science.components.world.timepiece;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public interface TimePieceComponent extends ComponentV3, ServerTickingComponent {
    long getTimePieceTicks();
    void setTimePieceTicks(long value);

    PlayerEntity getTimePieceUser();
    void setTimePieceUser(PlayerEntity value);

    ComponentKey<TimePieceComponent> worldKey = ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier("mod_science:timepiece"), TimePieceComponent.class);
}