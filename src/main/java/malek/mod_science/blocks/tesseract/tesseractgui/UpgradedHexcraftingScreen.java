package malek.mod_science.blocks.tesseract.tesseractgui;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class UpgradedHexcraftingScreen extends CottonInventoryScreen<UpgradedHexcraftingGuiDescription> {
    public UpgradedHexcraftingScreen(UpgradedHexcraftingGuiDescription description, PlayerEntity player, Text title) {
        super(description, player, title);
    }
}
