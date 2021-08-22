package malek.mod_science.screens;

import malek.mod_science.ModScience;
import malek.mod_science.blocks.tesseract.tesseractgui.HexcraftingGuiDescription;

import malek.mod_science.blocks.tesseract.tesseractgui.UpgradedHexcraftingGuiDescription;
import malek.mod_science.blocks.tesseract.tesseractgui.WyldTesseractCraftingGuiDescription;
import malek.mod_science.blocks.transfusionMatrix.gui.TranfusionMatrixGuiDescription;
import malek.mod_science.recipes.hex_crafting.upgraded.UpgradedHexcraftingRecipe;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

import static malek.mod_science.ModScience.MOD_ID;

public class ModScreens {
    public static ScreenHandlerType<TranfusionMatrixGuiDescription> TRANSFUSION_MATRIX_SCREEN_HANDLER;
    public static ScreenHandlerType<HexcraftingGuiDescription> TESSERACT_SCREEN_HANDLER_1;
    public static ScreenHandlerType<UpgradedHexcraftingGuiDescription> TESSERACT_SCREEN_HANDLER_2;
    public static ScreenHandlerType<WyldTesseractCraftingGuiDescription> TESSERACT_SCREEN_HANDLER_3;
    public static void init() {
        TRANSFUSION_MATRIX_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(MOD_ID, "transfusion_matrix"), (syncId, inventory) -> new TranfusionMatrixGuiDescription(syncId, inventory, ScreenHandlerContext.EMPTY));
        TESSERACT_SCREEN_HANDLER_1 = ScreenHandlerRegistry.registerSimple(ModScience.ModScienceId("tesseract1"), (syncId, inventory) -> new HexcraftingGuiDescription(syncId, inventory, ScreenHandlerContext.EMPTY));
        TESSERACT_SCREEN_HANDLER_2 = ScreenHandlerRegistry.registerSimple(ModScience.ModScienceId("tesseract2"), (syncId, inventory) -> new UpgradedHexcraftingGuiDescription(syncId, inventory, ScreenHandlerContext.EMPTY));
        TESSERACT_SCREEN_HANDLER_3 = ScreenHandlerRegistry.registerSimple(ModScience.ModScienceId("tesseract3"), (syncId, inventory) -> new WyldTesseractCraftingGuiDescription(syncId, inventory, ScreenHandlerContext.EMPTY));

    }
}
