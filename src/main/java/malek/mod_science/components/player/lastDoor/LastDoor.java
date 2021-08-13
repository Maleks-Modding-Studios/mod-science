package malek.mod_science.components.player.lastDoor;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import malek.mod_science.components.player.madness.Madness;
import malek.mod_science.util.general.NBTUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

import static malek.mod_science.ModScience.*;

public class LastDoor implements LastDoorInterface, EntityComponentInitializer, AutoSyncedComponent {
    public static final ComponentKey<LastDoor> LAST_DOOR = ComponentRegistryV3.INSTANCE.getOrCreate(ModScienceId("last_door"), LastDoor.class);

    private BlockPos LastDoor = new BlockPos(0, 0, 0);
    private ServerWorld world = getServer().getOverworld();

    @Override
    public LastDoor getLastDoor(Entity entity){
        return LAST_DOOR.get(entity);
    }

    @Override
    public void setLastWorld(ServerWorld worldd){
        this.world = worldd;
    }

    @Override
    public void writeSyncPacket(PacketByteBuf buf, ServerPlayerEntity recipient) {
        buf.writeBlockPos(LastDoor);
        buf.writeIdentifier(world.getRegistryKey().getValue());
    }
    @Override
    public void applySyncPacket(PacketByteBuf buf) {
        LastDoor = buf.readBlockPos();
        world = getServer().getWorld(RegistryKey.of(RegistryKey.ofRegistry(buf.readIdentifier()), buf.readIdentifier()));
    }

    public BlockPos getLastDoorBlockPos(){
        return LastDoor;
    }

    public ServerWorld getLastWorld(){return world;}

    @Override
    public void setLastDoor(BlockPos pos) {
        this.LastDoor = pos;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        Identifier yeetusID = new Identifier(tag.getString("last_world"));
        this.LastDoor = NBTUtil.writeBlockPosFromList(tag.getIntArray("last_door"));
        this.world = getServer().getWorld(RegistryKey.of(RegistryKey.ofRegistry(yeetusID), yeetusID));
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putIntArray("last_door", NBTUtil.writeBlockPosToList(LastDoor));
        tag.putString("last_world", world.getRegistryKey().getValue().toString());
    }
    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(LAST_DOOR, player -> new LastDoor(), RespawnCopyStrategy.ALWAYS_COPY);

    }
}
