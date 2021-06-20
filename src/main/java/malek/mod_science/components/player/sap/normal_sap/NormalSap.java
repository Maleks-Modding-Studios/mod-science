package malek.mod_science.components.player.sap.normal_sap;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import static malek.mod_science.ModScience.MOD_ID;

public class NormalSap implements NormalSapInterface, EntityComponentInitializer, AutoSyncedComponent {
    public static final ComponentKey<NormalSap> NORMAL_SAP = ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(MOD_ID, "normal_sap"), NormalSap.class);

    private double normalSap = 0;

    public static NormalSap get(Entity entity) {
        return NORMAL_SAP.get(entity);
    }

    @Override
    public void writeSyncPacket(PacketByteBuf buf, ServerPlayerEntity recipient) {
        buf.writeDouble(normalSap);
    }

    @Override
    public void applySyncPacket(PacketByteBuf buf) {
        normalSap = buf.readDouble();
    }

    @Override
    public double getNormalSap() {
        return normalSap;
    }

    @Override
    public void setNormalSap(double amount) {
        this.normalSap = amount;
    }

    @Override
    public void increaseNormalSap(double amount) {
        this.normalSap += amount;
    }

    @Override
    public void decreaseNormalSap(double amount) {
        this.normalSap -= amount;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.normalSap = tag.getDouble("normal_sap");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putDouble("normal_sap", this.normalSap);
    }

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(NORMAL_SAP, player -> new NormalSap(), RespawnCopyStrategy.ALWAYS_COPY);

    }
}
