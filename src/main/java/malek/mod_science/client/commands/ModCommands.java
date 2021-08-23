package malek.mod_science.client.commands;

import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;

public class ModCommands {
    public static void init() {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            DimTeleportCommand.register(dispatcher);
            MadnessCommand.register(dispatcher);
            TheRoomTeleportCommmand.register(dispatcher);
            GetDistortionCommand.register(dispatcher);
            SetDistortionCommand.register(dispatcher);
        });
    }
}
