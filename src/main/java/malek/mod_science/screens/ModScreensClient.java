package malek.mod_science.screens;


import malek.mod_science.blocks.tesseract.tesseractgui.*;

import malek.mod_science.blocks.transfusionMatrix.gui.TranfusionMatrixGuiDescription;
import malek.mod_science.blocks.transfusionMatrix.gui.TranfusionMatrixScreen;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

public class ModScreensClient {
    public static void init() {
        ScreenRegistry.<TranfusionMatrixGuiDescription, TranfusionMatrixScreen>register(ModScreens.TRANSFUSION_MATRIX_SCREEN_HANDLER, (gui, inventory, title) -> new TranfusionMatrixScreen(gui, inventory.player, title));
        ScreenRegistry.<HexcraftingGuiDescription, HexcraftingScreen>register(ModScreens.TESSERACT_SCREEN_HANDLER_1, (gui, inventory, title) -> new HexcraftingScreen(gui, inventory.player, title));
        ScreenRegistry.<UpgradedHexcraftingGuiDescription, UpgradedHexcraftingScreen>register(ModScreens.TESSERACT_SCREEN_HANDLER_2, (gui, inventory, title) -> new UpgradedHexcraftingScreen(gui, inventory.player, title));
        ScreenRegistry.<WyldTesseractCraftingGuiDescription, WyldsScreen>register(ModScreens.TESSERACT_SCREEN_HANDLER_3, (gui, inventory, title) -> new WyldsScreen(gui, inventory.player, title));

    }
}
