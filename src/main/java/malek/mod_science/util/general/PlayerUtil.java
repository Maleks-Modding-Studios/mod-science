package malek.mod_science.util.general;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;

import java.util.UUID;

public class PlayerUtil {

    public static PlayerEntity getPlayerFromUuid(UUID uuid) {
        return MinecraftClient.getInstance().getServer().getPlayerManager().getPlayer(uuid);
    }

}
