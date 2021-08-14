package malek.mod_science.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import malek.mod_science.components.player.timeout.Timeout;
import malek.mod_science.dimensions.ModDimensions;
import malek.mod_science.generation.genUtils.TheRoomFeature;
import malek.mod_science.util.general.MixinUtil;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;

public class RoomCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("room")
                                          .then(
                                                  CommandManager.argument("Player", EntityArgumentType.player())
                                                                .executes(ctx ->{
                                                                    ServerPlayerEntity entity = EntityArgumentType.getPlayer(ctx, "room");
                                                                    Timeout.TIMEOUT.get(MixinUtil.cast(entity)).setTimeOut(20);
                                                                    Vec3d pos = new Vec3d(-5, TheRoomFeature.theRoomCenter.getY() + 2, -5);
                                                                    return DimTeleportCommand.teleport(entity, ModDimensions.PERSONAL_POCKET_DIMENSION, pos);
                                                                }).then(
                                                                        CommandManager.argument("time", IntegerArgumentType.integer(1, 72000)).executes(
                                                                          ctx -> {
                                                                              ServerPlayerEntity entity = EntityArgumentType.getPlayer(ctx, "room");
                                                                              int time = IntegerArgumentType.getInteger(ctx, "room");
                                                                              Timeout.TIMEOUT.get(MixinUtil.cast(entity)).setTimeOut(time);
                                                                              Vec3d pos = new Vec3d(-5, TheRoomFeature.theRoomCenter.getY() + 2, -5);
                                                                              return DimTeleportCommand.teleport(entity, ModDimensions.PERSONAL_POCKET_DIMENSION, pos);
                                                                          }))
                                          ));

    }
}
