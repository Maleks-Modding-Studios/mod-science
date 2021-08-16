package malek.mod_science.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import malek.mod_science.components.player.madness.Madness;
import malek.mod_science.components.player.timeout.Timeout;
import malek.mod_science.dimensions.TheRoomDimension;
import malek.mod_science.generation.genUtils.TheRoomFeature;
import malek.mod_science.util.general.MixinUtil;
import net.minecraft.command.argument.DimensionArgumentType;
import net.minecraft.command.argument.Vec3ArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;


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