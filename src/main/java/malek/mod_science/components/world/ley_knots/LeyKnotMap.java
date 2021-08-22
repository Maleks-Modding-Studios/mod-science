package malek.mod_science.components.world.ley_knots;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.world.WorldComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.world.WorldComponentInitializer;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static malek.mod_science.ModScience.MOD_ID;

public class LeyKnotMap implements LeyKnotMapInterface, WorldComponentInitializer, AutoSyncedComponent {
    public static final ComponentKey<LeyKnotMap> LEY_KNOT_MAP = ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(MOD_ID, "ley_knot_map"), LeyKnotMap.class);
    private Map<BlockPos, BlockState> leyKnots = new HashMap<>();

    public static Map<BlockPos, BlockState> get(World world) {
        return LEY_KNOT_MAP.get(world).leyKnots;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        leyKnots = new HashMap<>();
        NbtList list = tag.getList("ley_knot_map", NbtType.COMPOUND);
        for (NbtElement element : list) {
            if (element instanceof NbtCompound) {
                NbtCompound compound = (NbtCompound) element;

                BlockPos blockPos = BlockPos.CODEC.decode(NbtOps.INSTANCE, compound.get("block_pos")).getOrThrow(false, pog -> {}).getFirst();
                BlockState knot = new BlockState();
                knot.readFromNbt(compound.getCompound("ley_knot"));
                leyKnots.put(blockPos, knot);
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
        listTag = leyKnots.entrySet().stream().parallel().map(blockPos -> {
            NbtCompound mappingTag = new NbtCompound();
            mappingTag.put("block_pos", BlockPos.CODEC.encode(blockPos.getKey(), NbtOps.INSTANCE, NbtOps.INSTANCE.empty()).getOrThrow(false, (string) -> {}));
            mappingTag.put("ley_knot", leyKnots.get(blockPos.getKey()).writeToNbt(new NbtCompound()));
            return mappingTag;
        }).collect(Collectors.toCollection(NbtList::new));
        tag.put("ley_knot_map", listTag);

    }

    @Override
    public void registerWorldComponentFactories(WorldComponentFactoryRegistry registry) {
        registry.register(LEY_KNOT_MAP, world -> new LeyKnotMap());
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
