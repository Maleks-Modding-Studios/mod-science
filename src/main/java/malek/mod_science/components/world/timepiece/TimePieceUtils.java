package malek.mod_science.components.world.timepiece;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class TimePieceUtils {
    public static long getTimePieceTicks(World world) {
        return TimePieceComponent.worldKey.get(world).getTimePieceTicks();
    }

    public static void setTimePieceTicks(World world, long ticks) {
        TimePieceComponent.worldKey.get(world).setTimePieceTicks(ticks);
    }
    public static PlayerEntity getTimePieceUser(World world) {
        return TimePieceComponent.worldKey.get(world).getTimePieceUser();
    }

    public static void setTimePieceUser(World world, PlayerEntity player) {
        TimePieceComponent.worldKey.get(world).setTimePieceUser(player);
    }
}
