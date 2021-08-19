package malek.mod_science.screens;

import malek.mod_science.blocks.tesseract.tesseractgui.TesseractGuiDescription;
import malek.mod_science.blocks.tesseract.tesseractgui.TesseractScreen;
import malek.mod_science.blocks.transfusionMatrix.gui.TranfusionMatrixGuiDescription;
import malek.mod_science.blocks.transfusionMatrix.gui.TranfusionMatrixScreen;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

public class ModScreensClient {
    public static void init() {
        ScreenRegistry.<TranfusionMatrixGuiDescription, TranfusionMatrixScreen>register(ModScreens.TRANSFUSION_MATRIX_SCREEN_HANDLER, (gui, inventory, title) -> new TranfusionMatrixScreen(gui, inventory.player, title));
        ScreenRegistry.<TesseractGuiDescription, TesseractScreen>register(ModScreens.TESSERACT_SCREEN_HANDLER, (gui, inventory, title) -> new TesseractScreen(gui, inventory.player, title));
    }
}
