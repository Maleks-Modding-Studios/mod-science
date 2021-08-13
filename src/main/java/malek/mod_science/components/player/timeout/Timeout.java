package malek.mod_science.components.player.timeout;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import malek.mod_science.components.player.madness.Madness;
import malek.mod_science.components.player.madness.MadnessInterface;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import static malek.mod_science.ModScience.MOD_ID;

public class Timeout implements TimeoutInterface, EntityComponentInitializer, AutoSyncedComponent {
    public static final ComponentKey<Timeout> TIMEOUT = ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(MOD_ID, "timeout"), Timeout.class);
    private double timeout = 0;
    private double time = System.currentTimeMillis()/1000;
    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(TIMEOUT, player -> new Timeout(), RespawnCopyStrategy.ALWAYS_COPY);
    }

    @Override
    public double getTimeOut() {
        return this.timeout;
    }


    @Override
    public void setTimeOut(double timeOut) {
        this.timeout = timeOut;
    }

    @Override
    public void setTime() {
        this.time = ((double)System.currentTimeMillis())/1000;
    }

    @Override
    public boolean isTimeOutOver() {
        return getTimeOut() <= (((double)System.currentTimeMillis())/1000) - time;
    }
    public boolean isTimeToLetOut(ServerPlayerEntity player) {
        return Timeout.TIMEOUT.get(player).getTimeOut() * 1000 <= System.currentTimeMillis() - time;
    }

    @Override
    public void writeSyncPacket(PacketByteBuf buf, ServerPlayerEntity recipient) {
        buf.writeDouble(timeout);
        buf.writeDouble(time);
    }

    @Override
    public void applySyncPacket(PacketByteBuf buf) {
        timeout = buf.readDouble();
        this.time = buf.readDouble();
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.timeout = tag.getDouble("timeout");
        this.time = tag.getDouble("time");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putDouble("timeout", timeout);
        tag.putDouble("time", time);
    }
}
