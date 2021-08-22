package malek.mod_science.components.world.doors;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.world.WorldComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.world.WorldComponentInitializer;
import malek.mod_science.components.world.ley_knots.BlockState;
import malek.mod_science.components.world.ley_knots.LeyKnotMapInterface;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

import static malek.mod_science.ModScience.MOD_ID;

public class DoorsComponent implements LeyKnotMapInterface, WorldComponentInitializer, AutoSyncedComponent {
    public static final ComponentKey<DoorsComponent> DOORS = ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(MOD_ID, "doors"), DoorsComponent.class);
    private HashSet<DimPos> doors = new HashSet<>();

    public static HashSet<DimPos> get(World world) {
        return DOORS.get(world).doors;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        doors = new HashSet<>();
        NbtList list = tag.getList("doors_list", NbtType.COMPOUND);
        for (NbtElement element : list) {
            if (element instanceof NbtCompound compound) {
                BlockPos blockPos = BlockPos.CODEC.decode(NbtOps.INSTANCE, compound.get("block_pos")).getOrThrow(false, pog -> {}).getFirst();
                Identifier worldID = new Identifier(tag.getString("last_world_namespace"), tag.getString("last_world_path"));
                doors.add(new DimPos(blockPos, RegistryKey.of(RegistryKey.ofRegistry(worldID), worldID)));
            }
        }
    }

    /**
     * Writes this component's properties to a {@link }.
     *
     * @param tag
     *         a {@code NbtCompound} on which to write this component's serializable data
     */
    @Override
    public void writeToNbt(NbtCompound tag) {

        NbtList listTag;
        listTag = doors.stream().parallel().map(blockPos -> {
            NbtCompound mappingTag = new NbtCompound();
            mappingTag.put("block_pos", BlockPos.CODEC.encode(blockPos, NbtOps.INSTANCE, NbtOps.INSTANCE.empty()).getOrThrow(false, (string) -> {}));
            RegistryKey world = blockPos.dimension;
            tag.putString("last_world_namespace", world.getValue().getNamespace());
            tag.putString("last_world_path", world.getValue().getPath());
            return mappingTag;
        }).collect(Collectors.toCollection(NbtList::new));
        tag.put("doors_list", listTag);

    }
    @Override
    public void registerWorldComponentFactories(WorldComponentFactoryRegistry registry) {
        registry.register(DOORS, world -> new DoorsComponent());
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


}
