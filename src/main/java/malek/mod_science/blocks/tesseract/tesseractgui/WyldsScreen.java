package malek.mod_science.blocks.tesseract.tesseractgui;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class WyldsScreen extends CottonInventoryScreen<WyldTesseractCraftingGuiDescription> {
    public WyldsScreen(WyldTesseractCraftingGuiDescription description, PlayerEntity player, Text title) {
        super(description, player, title);
    }
}
