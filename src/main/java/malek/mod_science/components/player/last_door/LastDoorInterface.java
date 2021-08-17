package malek.mod_science.components.player.last_door;

import dev.onyxstudios.cca.api.v3.component.Component;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;

public interface LastDoorInterface extends Component {
    public LastDoor getLastDoor(Entity entity);
    void setLastDoor(BlockPos pos);
    void setLastWorld(RegistryKey world);
    public Boolean isVoid();
}
