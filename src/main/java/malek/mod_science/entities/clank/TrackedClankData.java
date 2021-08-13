package malek.mod_science.entities.clank;

import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class TrackedClankData {
    public static final TrackedDataHandler<TrackedClankData> HANDLER = new TrackedDataHandler<>() {
        @Override
        public void write(PacketByteBuf buf, TrackedClankData value) {
            buf.writeNbt(value.write());
        }

        @Override
        public TrackedClankData read(PacketByteBuf buf) {
            return TrackedClankData.create(Objects.requireNonNull(buf.readNbt(), "Missing nbt in the packet's byte buffer"));
        }

        @Override
        public TrackedClankData copy(TrackedClankData value) {
            return new TrackedClankData(value.clank);
        }
    };

    private final Clank clank;

    private TrackedClankData(Clank clank) {
        this.clank = clank;
    }

    public NbtCompound write() {
        NbtCompound compound = new NbtCompound();

        compound.putString("key", Clanks.getIdentifierFrom(clank).toString());

        clank.write(compound);

        return compound;
    }

    public static @NotNull TrackedClankData create(NbtCompound compound) {
        Clank clank = Clanks.CLOCKWORKER;

        if (compound.contains("key", NbtType.STRING)) {
            clank = Clanks.getFromIdentifier(new Identifier(compound.getString("key")));

            clank.read(compound);
        }

        return new TrackedClankData(clank);
    }

    public static @NotNull TrackedClankData initial(Clank clank) {
        return new TrackedClankData(clank);
    }
}
