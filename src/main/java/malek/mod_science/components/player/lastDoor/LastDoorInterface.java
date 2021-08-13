package malek.mod_science.components.player.lastDoor;

import dev.onyxstudios.cca.api.v3.component.Component;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public interface LastDoorInterface extends Component {
    public LastDoor getLastDoor(Entity entity);
    void setLastDoor(BlockPos pos);
    void setLastWorld(ServerWorld world);
}
