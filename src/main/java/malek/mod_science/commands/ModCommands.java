package malek.mod_science.commands;

import malek.mod_science.components.player.madness.Madness;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;

public class ModCommands {
    public static void init() {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            DimTeleportCommand.register(dispatcher);
            RoomCommand.register(dispatcher);
            MadnessCommand.register(dispatcher);
            TheRoomTeleportCommmand.register(dispatcher);
        });
    }
}
