package malek.mod_science.blocks.tesseract.tesseractgui;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class TesseractScreen extends CottonInventoryScreen<TesseractGuiDescription> {
    public TesseractScreen(TesseractGuiDescription description, PlayerEntity player, Text title) {
        super(description, player, title);
    }
}
