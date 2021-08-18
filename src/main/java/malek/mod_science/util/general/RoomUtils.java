package malek.mod_science.util.general;

import malek.mod_science.client.commands.DimTeleportCommand;
import malek.mod_science.components.player.timeout.Timeout;
import malek.mod_science.worlds.dimensions.ModDimensions;
import malek.mod_science.worlds.generation.genUtils.TheRoomFeature;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;

public class RoomUtils {
    public static int commandSendToRoom(Entity entity, int time) {
        Timeout.TIMEOUT.get(MixinUtil.cast(entity)).setTimeOut(time);
        Vec3d pos = new Vec3d(-5, TheRoomFeature.theRoomCenter.getY() + 2, -5);
        return DimTeleportCommand.teleport(entity, ModDimensions.PERSONAL_POCKET_DIMENSION, pos);

    }

    public static void setRoomTimeout(Entity entity, int time) {
        Timeout.TIMEOUT.get(MixinUtil.cast(entity)).setTimeOut(time);
    }

    public static void clearRoomTimeout(Entity entity) {
        Timeout.TIMEOUT.get(MixinUtil.cast(entity)).setTimeOut(0);
    }

    public static void sendToRoom(Entity entity, int time) {
        Timeout.TIMEOUT.get(MixinUtil.cast(entity)).setTimeOut(time);
        ((ServerPlayerEntity) (entity)).teleport(ModDimensions.PERSONAL_POCKET_DIMENSION, -5, TheRoomFeature.theRoomCenter.getY() + 2, -5, 0f, 0f);

    }
}
