package malek.mod_science.client.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import malek.mod_science.components.chunk.distortion.Distortion;
import malek.mod_science.components.player.madness.Madness;
import net.minecraft.command.argument.DimensionArgumentType;
import net.minecraft.command.argument.Vec3ArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;


public class SetDistortionCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("distortion").then(CommandManager.literal("set").then(CommandManager
                                                                                                                 .argument("amount", IntegerArgumentType.integer())
                                                                                                                 .executes(ctx -> {
                                                                                                                     Distortion.set(ctx.getSource().getPlayer().getServerWorld()
                                                                                                                                       .getChunk(ctx.getSource().getPlayer().getChunkPos().x, ctx.getSource().getPlayer().getChunkPos().z), IntegerArgumentType.getInteger(ctx, "amount"));
                                                                                                                     ctx.getSource().getPlayer().sendSystemMessage(
                                                                                                                             Text.of("Distortion is : " +
                                                                                                                                     Distortion.get(ctx.getSource().getPlayer().getServerWorld()
                                                                                                                                                       .getChunk(ctx.getSource().getPlayer().getChunkPos().x, ctx.getSource().getPlayer().getChunkPos().z))),
                                                                                                                             ctx.getSource().getPlayer().getUuid());
                                                                                                                     return Command.SINGLE_SUCCESS;
                                                                                                                 })

                        )



        ));
    }
    public static int setDistortion(PlayerEntity player, int madness) {
        Madness.get(player).setMadness(madness);
        return Command.SINGLE_SUCCESS;
    }
}