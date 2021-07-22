package malek.mod_science.mechanics.tinkering;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.ArrayList;

public class Tinkering {
    public static void tinker(MinecraftServer server, ServerPlayerEntity serverPlayerEntity) {
        serverPlayerEntity.getInventory().setStack(0, new ItemStack(Items.DIAMOND));
    }
}
