package malek.mod_science.components.chunk.distortion;

import dev.onyxstudios.cca.api.v3.chunk.ChunkComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.chunk.ChunkComponentInitializer;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import malek.mod_science.components.world.ley_knots.LeyKnotMapInterface;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import static malek.mod_science.ModScience.MOD_ID;

public class Distortion implements LeyKnotMapInterface, ChunkComponentInitializer, AutoSyncedComponent {
    public static final ComponentKey<Distortion> DISTORTION = ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(MOD_ID, "distortion"), Distortion.class);

    int distortion = 0;

    public static int get(Chunk chunk) {
        return DISTORTION.get(chunk).distortion;
    }
    public static void set(Chunk chunk, int distortion) {
        DISTORTION.get(chunk).distortion = distortion;
    }

    public static int getTrueDistortion(World world, Chunk chunk) {
        int c1 = get(world.getChunk(chunk.getPos().x+1, chunk.getPos().z+1));
        int c2 = get(world.getChunk(chunk.getPos().x-1, chunk.getPos().z+1));
        int c3 = get(world.getChunk(chunk.getPos().x+1, chunk.getPos().z-1));
        int c4 = get(world.getChunk(chunk.getPos().x-1, chunk.getPos().z-1));
        int norm = get(chunk);
        return (c1 + c2 + c3 + c4 + norm) / 5;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        distortion = tag.getInt("distortion");
    }

    /**
     * Writes this component's properties to a {@link }.
     *
     * @param tag
     *         a {@code NbtCompound} on which to write this component's serializable data
     */
    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putInt("distortion", distortion);
    }



    @Override
    public boolean shouldSyncWith(ServerPlayerEntity player) {
        return true;
    }

    @Override
    public void writeSyncPacket(PacketByteBuf buf, ServerPlayerEntity recipient) {
        AutoSyncedComponent.super.writeSyncPacket(buf, recipient);
    }

    @Override
    public void applySyncPacket(PacketByteBuf buf) {
        AutoSyncedComponent.super.applySyncPacket(buf);
    }


    @Override
    public void registerChunkComponentFactories(ChunkComponentFactoryRegistry registry) {
        registry.register(DISTORTION, chunk -> new Distortion());

    }
}
