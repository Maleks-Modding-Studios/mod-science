package malek.mod_science.util.general;

import malek.mod_science.mixin.PlayerEntityMixin;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;

import java.util.UUID;

public class PlayerUtil {

    public static PlayerEntity getPlayerFromUuid(UUID uuid) {
        return MinecraftClient.getInstance().getServer().getPlayerManager().getPlayer(uuid);
    }

}
