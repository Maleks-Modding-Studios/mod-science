package malek.mod_science.components.player.last_door;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import malek.mod_science.util.general.NBTUtil;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;

import static malek.mod_science.ModScience.*;

public class LastDoor implements LastDoorInterface, EntityComponentInitializer {
    public static final ComponentKey<LastDoor> LAST_DOOR = ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(MOD_ID, "last_door"), LastDoor.class);

    private BlockPos lastDoorPos = new BlockPos(0, 0, 0);
    private RegistryKey world = getServer().getOverworld().getRegistryKey();

    @Override
    public LastDoor getLastDoor(Entity entity){
        return LAST_DOOR.get(entity);
    }

    @Override
    public void setLastWorld(RegistryKey worldd){
        this.world = worldd;
    }

//    @Override
//    public void writeSyncPacket(PacketByteBuf buf, ServerPlayerEntity recipient) {
//        buf.writeBlockPos(lastDoorPos);
//        buf.writeIdentifier(world.getValue());
//    }
//    @Override
//    public void applySyncPacket(PacketByteBuf buf) {
//        lastDoorPos = buf.readBlockPos();
//        world = RegistryKey.of(RegistryKey.ofRegistry(buf.readIdentifier()), buf.readIdentifier());
//    }

    public BlockPos getLastDoorBlockPos(){
        return lastDoorPos;
    }

    public RegistryKey getLastWorld(){return world;}

    @Override
    public void setLastDoor(BlockPos pos) {
        this.lastDoorPos = pos;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        Identifier WorldID = new Identifier(tag.getString("last_world_namespace"), tag.getString("last_world_path"));
        this.lastDoorPos = NBTUtil.writeBlockPosFromList(tag.getIntArray("last_door"));
        this.world = RegistryKey.of(RegistryKey.ofRegistry(WorldID), WorldID);
        System.out.println(RegistryKey.ofRegistry(WorldID));
        System.out.println(world);
    }
    @Override
    public Boolean isVoid(){
        return lastDoorPos.equals(new BlockPos(0, 0, 0));
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putIntArray("last_door", NBTUtil.writeBlockPosToList(lastDoorPos));
        tag.putString("last_world_namespace", world.getValue().getNamespace());
        tag.putString("last_world_path", world.getValue().getPath());
        System.out.println(world);
    }
    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(LAST_DOOR, player -> new LastDoor(), RespawnCopyStrategy.ALWAYS_COPY);

    }
}
