package malek.mod_science.components.world.timepiece;

import net.minecraft.entity.player.PlayerEntity;

public class TimePieceUtils {
    public static long getTimePieceTicks(PlayerEntity playerEntity) {
        return TimePieceComponent.entityKey.get(playerEntity).getTimePieceTicks();
    }

    public static void setTimePieceTicks(PlayerEntity playerEntity, long ticks) {
        TimePieceComponent.entityKey.get(playerEntity).setTimePieceTicks(ticks);
    }
    public static PlayerEntity getTimePieceUser(PlayerEntity playerEntity) {
        return TimePieceComponent.entityKey.get(playerEntity).getTimePieceUser();
    }

    public static void setTimePieceUser(PlayerEntity playerEntity) {
        TimePieceComponent.entityKey.get(playerEntity).setTimePieceUser(playerEntity);
    }
}
