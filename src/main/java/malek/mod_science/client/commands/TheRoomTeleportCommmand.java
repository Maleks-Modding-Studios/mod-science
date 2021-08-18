package malek.mod_science.client.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import malek.mod_science.components.player.timeout.Timeout;
import malek.mod_science.worlds.dimensions.TheRoomDimension;
import malek.mod_science.worlds.generation.genUtils.TheRoomFeature;
import malek.mod_science.util.general.MixinUtil;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;


public class TheRoomTeleportCommmand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("room")
                                          .then(CommandManager
                                                        .argument("player", EntityArgumentType.player())
                                                        .executes(ctx -> {
                                                            ServerPlayerEntity player = EntityArgumentType.getPlayer(ctx, "player");
                                                            Timeout.TIMEOUT.get(MixinUtil.cast(player)).setTimeOut(20);
                                                            System.out.println(player.getDisplayName());
                                                            return teleport(player, TheRoomDimension.world, new Vec3d(0, 0, 0));
                                                        })
                                                        .then(CommandManager
                                                                      .argument("time", IntegerArgumentType.integer())
                                                                      .executes(ctx -> {
                                                                          ServerPlayerEntity player = EntityArgumentType.getPlayer(ctx, "player");
                                                                          Timeout.TIMEOUT.get(MixinUtil.cast(player)).setTimeOut(IntegerArgumentType.getInteger(ctx, "time"));
                                                                          return teleport(player, TheRoomDimension.world, new Vec3d(0, 0, 0));
                                                                      })

                                                        )
                                          )


        );
    }
    public static int teleport(Entity entity, ServerWorld dimension, Vec3d pos) {
        if(dimension.getRegistryKey() == TheRoomDimension.WORLD_KEY) {
            ((ServerPlayerEntity)(entity)).teleport(dimension, -5, TheRoomFeature.theRoomCenter.getY()+2, -5, 0f, 0f);
        } else {
            ((ServerPlayerEntity) (entity)).teleport(dimension, pos.x, pos.y, pos.z, 0f, 0f);
        }
        return Command.SINGLE_SUCCESS;
    }
}