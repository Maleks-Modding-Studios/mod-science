package malek.mod_science.screens;

import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

public class ModScreensClient {
    public static void init() {
        ScreenRegistry.<TranfusionMatrixGuiDescription, TranfusionMatrixScreen>register(ModScreens.TRANSFUSION_MATRIX_SCREEN_HANDLER, (gui, inventory, title) -> new TranfusionMatrixScreen(gui, inventory.player, title));
    }
}
