package malek.mod_science.components.player.madness;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import malek.mod_science.ModConfig;
import malek.mod_science.ModScienceInit;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import static malek.mod_science.ModScience.MOD_ID;

public class Madness implements MadnessInterface, EntityComponentInitializer, AutoSyncedComponent {
    public static final ComponentKey<Madness> MADNESS =
            ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(MOD_ID, "madness"), Madness.class);

    private double madness = 0;

    public static Madness get(Entity entity) {
        return MADNESS.get(entity);
    }

    @Override
    public void writeSyncPacket(PacketByteBuf buf, ServerPlayerEntity recipient) {
        buf.writeDouble(madness);
    }

    @Override
    public void applySyncPacket(PacketByteBuf buf) {
        madness = buf.readDouble();
    }

    @Override
    public double getMadness() {
        return madness;
    }

    @Override
    public void setMadness(double amount) {
        this.madness = amount;
    }

    @Override
    public void increaseMadness(double amount) {
        this.madness += amount;
    }

    @Override
    public void decreaseMadness(double amount) {
        this.madness -= amount;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.madness = tag.getDouble("madness");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putDouble("madness", this.madness);
    }

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(MADNESS, player -> new Madness(), RespawnCopyStrategy.ALWAYS_COPY);

    }

    /**
     * This method returns true if madness is above the low threshold,
     * including if it is above higher thresholds as well.
     * @return boolean representing if the threshold is reached
     */
    public boolean isLow() {
        return this.getMadness() >= ModScienceInit.getConfig().madness.lowMadness.thresholdAmount;
    }

    /**
     * This method returns true if madness is above the medium threshold,
     * including if it is above higher thresholds as well.
     * @return boolean representing if the threshold is reached
     */
    public boolean isMedium() {
        return this.getMadness() >= ModScienceInit.getConfig().madness.mediumMadness.thresholdAmount;
    }

    public static ModConfig.MadnessConfig getConfig() {
        return ModScienceInit.getConfig().madness;
    }
}
