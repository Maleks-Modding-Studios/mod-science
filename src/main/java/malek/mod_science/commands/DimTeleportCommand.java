package malek.mod_science.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import malek.mod_science.ModScience;
import malek.mod_science.ModScienceInit;
import net.fabricmc.api.ModInitializer;
import net.minecraft.command.argument.DimensionArgumentType;
import net.minecraft.command.argument.Vec3ArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.EulerAngle;
import net.minecraft.util.math.Vec3d;


public class DimTeleportCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("dimteleport")

                                          .then(CommandManager
                                                        .argument("dimension", DimensionArgumentType.dimension())
                                                        .executes(ctx -> {
                                                            ServerPlayerEntity player = ctx.getSource().getPlayer();
                                                            return teleport(player, DimensionArgumentType.getDimensionArgument(ctx, "dimension"), player.getPos());
                                                        })
                                                        .then(CommandManager
                                                                      .argument("coordinates", Vec3ArgumentType.vec3())
                                                                      .executes(ctx -> {
                                                                          ServerPlayerEntity player = ctx.getSource().getPlayer();
                                                                          return teleport(player, DimensionArgumentType.getDimensionArgument(ctx, "dimension"), Vec3ArgumentType.getVec3(ctx, "coordinates"));
                                                                      })
                                                                      .then(CommandManager
                                                                                    .argument("yaw", FloatArgumentType.floatArg())
                                                                                    .executes( ctx -> {
                                                                                        ServerPlayerEntity player = ctx.getSource().getPlayer();
                                                                                        return teleport(player, DimensionArgumentType.getDimensionArgument(ctx, "dimension"), Vec3ArgumentType.getVec3(ctx, "coordinates"));
                                                                                    })
                                                                                    .then(CommandManager
                                                                                                  .argument("pitch", FloatArgumentType.floatArg())
                                                                                                  .executes( ctx -> {
                                                                                                      ServerPlayerEntity player = ctx.getSource().getPlayer();
                                                                                                      return teleport(player, DimensionArgumentType.getDimensionArgument(ctx, "dimension"), Vec3ArgumentType.getVec3(ctx, "coordinates"));
                                                                                                  })
                                                                                    )
                                                                      )
                                                        )
                                          )


        );
    }
    private static int teleport(Entity entity, ServerWorld dimension, Vec3d pos) {
        entity.moveToWorld(dimension);
//        if(entity instanceof PlayerEntity) {
//            DimensionalRegistry.getRiftRegistry().setOverworldRift(entity.getUuid(), new Location((ServerWorld) entity.getEntityWorld(), entity.getBlockPos()));
//        }
//        TeleportUtil.teleport(entity, dimension, pos, angle, entity.getVelocity());
//        return Command.SINGLE_SUCCESS;
        return Command.SINGLE_SUCCESS;
    }
}