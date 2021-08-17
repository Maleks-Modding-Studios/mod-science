package malek.mod_science.components.world.timepiece;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;


public class TimePieceComponentImpl implements TimePieceComponent, AutoSyncedComponent {
    private World world;
    private long timePieceTicks = -1;
    private PlayerEntity timePieceUser = null;

    public TimePieceComponentImpl(World world) {
        this.world = world;
    }

    @Override
    public long getTimePieceTicks() {
        return timePieceTicks;
    }

    @Override
    public void setTimePieceTicks(long timePieceTicks) {
        this.timePieceTicks = timePieceTicks;
        TimePieceComponent.worldKey.sync(world);
    }

    @Override
    public PlayerEntity getTimePieceUser() {
        return timePieceUser;
    }

    @Override
    public void setTimePieceUser(PlayerEntity value) {
        timePieceUser = value;
        TimePieceComponent.worldKey.sync(world);
    }

    @Override
    public void serverTick() {
        timePieceTicks--;
        if (timePieceTicks < 0) {
            timePieceUser = null;
        }
        TimePieceComponent.worldKey.sync(world);
    }
    @Override
    public void writeSyncPacket(PacketByteBuf buf, ServerPlayerEntity recipient) {
        buf.writeVarLong(timePieceTicks);
        if (timePieceUser != null) {
            buf.writeUuid(timePieceUser.getUuid());
        }
    }

    @Override
    public void applySyncPacket(PacketByteBuf buf) {
        timePieceTicks = buf.readVarLong();
        try {
            timePieceUser = world.getPlayerByUuid(buf.readUuid());
        } catch (IndexOutOfBoundsException e) {
            timePieceUser = null;
        }
    }

    @Override
    public void readFromNbt(NbtCompound tag) {}

    @Override
    public void writeToNbt(NbtCompound tag) {}

}
