package malek.mod_science.screens;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WSprite;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class TranfusionMatrixGuiDescription extends SyncedGuiDescription {
    private static final int INVENTORY_SIZE = 2;
    public TranfusionMatrixGuiDescription(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(ModScreens.TRANSFUSION_MATRIX_SCREEN_HANDLER, syncId, playerInventory, getBlockInventory(context, INVENTORY_SIZE), getBlockPropertyDelegate(context, 2));
        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(150, 150);
        root.setInsets(Insets.ROOT_PANEL);

        WItemSlot itemSlot = WItemSlot.of(blockInventory, 0);
        WItemSlot outputSlot = WItemSlot.of(blockInventory, 1);
        root.add(itemSlot, 4, 1);
        root.add(outputSlot, 4, 3);
        WSprite progressBar = new WSprite(new Identifier(""));
        root.add(this.createPlayerInventoryPanel(), 0, 4);

        root.validate(this);
    }
}
