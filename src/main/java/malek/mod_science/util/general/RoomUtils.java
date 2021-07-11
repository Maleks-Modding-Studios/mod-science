package malek.mod_science.util.general;

import io.netty.handler.codec.spdy.SpdyWindowUpdateFrame;
import malek.mod_science.components.player.timeout.Timeout;
import malek.mod_science.dimensions.ModDimensions;
import malek.mod_science.dimensions.TheRoomDimension;
import malek.mod_science.generation.genUtils.TheRoomFeature;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public class RoomUtils {
    public void sendToRoom(Entity entity, int time){
        Timeout.TIMEOUT.get(MixinUtil.cast(entity)).setTimeOut(time);
        ((ServerPlayerEntity)(entity)).teleport(ModDimensions.PERSONAL_POCKET_DIMENSION, -5, TheRoomFeature.theRoomCenter.getY() + 2, -5, 0f, 0f);

    }
    public void setRoomTimeout(Entity entity, int time){
        Timeout.TIMEOUT.get(MixinUtil.cast(entity)).setTimeOut(time);
    }
    public void clearRoomTimeout(Entity entity){
        Timeout.TIMEOUT.get(MixinUtil.cast(entity)).setTimeOut(0);
    }
}
