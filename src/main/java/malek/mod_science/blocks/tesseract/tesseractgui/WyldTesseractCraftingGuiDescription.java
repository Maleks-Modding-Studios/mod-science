package malek.mod_science.blocks.tesseract.tesseractgui;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.cotton.gui.widget.WPlayerInvPanel;
import io.github.cottonmc.cotton.gui.widget.WSprite;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import malek.mod_science.ModScience;
import malek.mod_science.screens.ModScreens;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.util.Identifier;

public class WyldTesseractCraftingGuiDescription extends SyncedGuiDescription {
    private static final int INVENTORY_SIZE = 17;
    BlockEntity blockEntity;
    public WyldTesseractCraftingGuiDescription(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(ModScreens.TESSERACT_SCREEN_HANDLER_3, syncId, playerInventory, getBlockInventory(context, INVENTORY_SIZE), getBlockPropertyDelegate(context, 3));
        WPlainPanel root = new WPlainPanel();
        setRootPanel(root);
        int sizeX = 170;
        int sizeY = 240;
        root.setSize(sizeX, sizeY);
        root.setInsets(Insets.ROOT_PANEL);


        WItemSlot itemSlot = WItemSlot.of(blockInventory, 0);
        WItemSlot itemSlot1 = WItemSlot.of(blockInventory, 2);
        WItemSlot itemSlot2 = WItemSlot.of(blockInventory, 3);
        WItemSlot itemSlot3 = WItemSlot.of(blockInventory, 4);
        WItemSlot itemSlot4 = WItemSlot.of(blockInventory, 5);
        WItemSlot itemSlot5 = WItemSlot.of(blockInventory, 6);
        WItemSlot outputSlot = WItemSlot.of(blockInventory, 1);

        int sizeOfSlot = 18;
        int halfSlot = 9;
        int diagonal = ((sizeOfSlot+halfSlot));
        int diagonalY = sizeOfSlot;
        int x = (sizeX/2)-halfSlot;
        int y = (int) ((sizeY / 2) - (sizeOfSlot * 3))+halfSlot+5;
        root.add(itemSlot, x, y-(sizeOfSlot+halfSlot));
        root.add(itemSlot1, x+diagonal+halfSlot+1, y-diagonalY+6);
        root.add(itemSlot2, x+37, y+18);
        root.add(itemSlot3, x-diagonal-10, y+diagonalY);
        root.add(itemSlot4, x, y+(sizeOfSlot+halfSlot)+6);
        root.add(itemSlot5, x-diagonal-10, y-diagonalY+6);

        root.add(outputSlot, x, y+3);

        WItemSlot m1 = WItemSlot.of(blockInventory, 7);
        WItemSlot m2 = WItemSlot.of(blockInventory, 8);
        WItemSlot m3 = WItemSlot.of(blockInventory, 9);
        WItemSlot m4 = WItemSlot.of(blockInventory, 10);
        WItemSlot m5 = WItemSlot.of(blockInventory, 11);
        WItemSlot m6 = WItemSlot.of(blockInventory, 12);

        int xMOffset = (int) ((2.25 * sizeOfSlot) - halfSlot) - 3;
        int yMOffset = (int) ((2.25 * sizeOfSlot) + halfSlot) + 8;

        root.add(m1, x-xMOffset, y-yMOffset);
        root.add(m2, x+xMOffset-1, y-yMOffset);

        root.add(m3, x+(4*sizeOfSlot) - 7, y+3);

        root.add(m4, x-xMOffset, y+yMOffset+7);
        root.add(m5, x+xMOffset-1, y+yMOffset+7);

        root.add(m6, x-(4*sizeOfSlot) + 7, y+3);


         WItemSlot a1 = WItemSlot.of(blockInventory, 13);
         WItemSlot a2 = WItemSlot.of(blockInventory, 14);
         WItemSlot a3 = WItemSlot.of(blockInventory, 15);
         WItemSlot a4 = WItemSlot.of(blockInventory, 16);

         root.add(a1, (int) (x - (sizeOfSlot * 3.75)) + halfSlot - 1, (int) (y - (sizeOfSlot * 3.75)) - halfSlot - 1);
         root.add(a2, (int) (x + (sizeOfSlot * 3.75)) - halfSlot + 1, (int) (y - (sizeOfSlot * 3.75)) - halfSlot - 1);
         root.add(a3, (int) (x + (sizeOfSlot * 3.75)) - halfSlot + 1, (int) (y + (sizeOfSlot * 3.75)) + sizeOfSlot );
         root.add(a4, (int) (x - (sizeOfSlot * 3.75)) + halfSlot - 1, (int) (y + (sizeOfSlot * 3.75)) + sizeOfSlot );



        WSprite sprite = new WSprite(new Identifier(ModScience.MOD_ID, "textures/gui/tesseract/tesseract_ui_tier3.png"));
        this.titleVisible = false;
        root.add(sprite, 0, -10, sizeX, sizeY);
        root.add(this.createPlayerInventoryPanel(WPlayerInvPanel.createInventoryLabel(playerInventory).setHorizontalAlignment(HorizontalAlignment.CENTER)), (halfSlot / 2), 175);

        root.validate(this);
    }
    private BlockEntity getBlockEntity(ScreenHandlerContext ctx) {
        return (BlockEntity) ctx.get((world, pos) -> {
            BlockState state = world.getBlockState(pos);
            Block b = state.getBlock();
            System.out.println(world.getBlockEntity(pos));
            blockEntity = world.getBlockEntity(pos);
            return world.getBlockEntity(pos);

        }).get();
    }
}
