package malek.mod_science.client.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import malek.mod_science.components.player.madness.Madness;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;


public class MadnessCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("madness")

                                          .then(CommandManager
                                                        .argument("amount", IntegerArgumentType.integer())
                                                        .executes(ctx -> {
                                                            ServerPlayerEntity player = ctx.getSource().getPlayer();
                                                            return setMadness(player, IntegerArgumentType.getInteger(ctx, "amount"));
                                                        })

                                          )


        );
    }
    public static int setMadness(PlayerEntity player, int madness) {
        Madness.get(player).setMadness(madness);
        return Command.SINGLE_SUCCESS;
    }
}