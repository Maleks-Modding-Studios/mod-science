package malek.mod_science.components.player.timepiece;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.entity.PlayerComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;


public class TimePieceComponentImpl implements TimePieceComponent, AutoSyncedComponent, PlayerComponent<TimePieceComponentImpl> {
    private PlayerEntity owner;
    private long timePieceTicks = -1;
    private PlayerEntity timePieceUser = null;

    public TimePieceComponentImpl(PlayerEntity owner) {
        this.owner = owner;
    }

    @Override
    public long getTimePieceTicks() {
        return timePieceTicks;
    }

    @Override
    public void setTimePieceTicks(long timePieceTicks) {
        this.timePieceTicks = timePieceTicks;
        TimePieceComponent.entityKey.sync(owner);
    }

    @Override
    public PlayerEntity getTimePieceUser() {
        return timePieceUser;
    }

    @Override
    public void setTimePieceUser(PlayerEntity value) {
        timePieceUser = value;
        TimePieceComponent.entityKey.sync(owner);
    }

    @Override
    public void serverTick() {
        timePieceTicks--;
        if (timePieceTicks < 0) {
            timePieceUser = null;
        }
        TimePieceComponent.entityKey.sync(owner);
    }


    @Override
    public void readFromNbt(NbtCompound tag) {}

    @Override
    public void writeToNbt(NbtCompound tag) {}

}
