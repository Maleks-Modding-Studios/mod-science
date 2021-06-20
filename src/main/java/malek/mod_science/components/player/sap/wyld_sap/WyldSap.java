package malek.mod_science.components.player.sap.wyld_sap;

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

public class WyldSap implements WyldSapInterface, EntityComponentInitializer, AutoSyncedComponent {
    public static final ComponentKey<WyldSap> WYLD_SAP = ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(MOD_ID, "wyld_sap"), WyldSap.class);

    private double wyldSap = 0;

    public static WyldSap get(Entity entity) {
        return WYLD_SAP.get(entity);
    }

    @Override
    public void writeSyncPacket(PacketByteBuf buf, ServerPlayerEntity recipient) {
        buf.writeDouble(wyldSap);
    }

    @Override
    public void applySyncPacket(PacketByteBuf buf) {
        wyldSap = buf.readDouble();
    }

    @Override
    public double getWyldSap() {
        return wyldSap;
    }

    @Override
    public void setWyldSap(double amount) {
        this.wyldSap = amount;
    }

    @Override
    public void increaseWyldSap(double amount) {
        this.wyldSap += amount;
    }

    @Override
    public void decreaseWyldSap(double amount) {
        this.wyldSap -= amount;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.wyldSap = tag.getDouble("wyld_sap");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putDouble("wyld_sap", this.wyldSap);
    }

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(WYLD_SAP, player -> new WyldSap(), RespawnCopyStrategy.ALWAYS_COPY);
    }
}
