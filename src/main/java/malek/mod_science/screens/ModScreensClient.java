package malek.mod_science.screens;

import malek.mod_science.screens.tesseractgui.TesseractGuiDescription;
import malek.mod_science.screens.tesseractgui.TesseractScreen;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.screen.ScreenHandlerContext;

public class ModScreensClient {
    public static void init() {
        ScreenRegistry.<TranfusionMatrixGuiDescription, TranfusionMatrixScreen>register(ModScreens.TRANSFUSION_MATRIX_SCREEN_HANDLER, (gui, inventory, title) -> new TranfusionMatrixScreen(gui, inventory.player, title));
        ScreenRegistry.<TesseractGuiDescription, TesseractScreen>register(ModScreens.TESSERACT_SCREEN_HANDLER, (gui, inventory, title) -> new TesseractScreen(gui, inventory.player, title));
    }
}
