package malek.mod_science.screens.tesseractgui;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WItem;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import malek.mod_science.screens.ModScreens;
import malek.mod_science.screens.TranfusionMatrixGuiDescription;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.text.Text;

public class TesseractGuiDescription extends SyncedGuiDescription {
    private static final int INVENTORY_SIZE = 17;
    BlockEntity blockEntity;
    public TesseractGuiDescription(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(ModScreens.TESSERACT_SCREEN_HANDLER, syncId, playerInventory, getBlockInventory(context, INVENTORY_SIZE), getBlockPropertyDelegate(context, 3));
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
        root.add(itemSlot1, x+diagonal, y-diagonalY);
        root.add(itemSlot2, x+diagonal, y+diagonalY);
        root.add(itemSlot3, x-diagonal, y+diagonalY);
        root.add(itemSlot4, x, y+(sizeOfSlot+halfSlot));
        root.add(itemSlot5, x-diagonal, y-diagonalY);

        root.add(outputSlot, x, y);

        WItemSlot m1 = WItemSlot.of(blockInventory, 7);
        WItemSlot m2 = WItemSlot.of(blockInventory, 8);
        WItemSlot m3 = WItemSlot.of(blockInventory, 9);
        WItemSlot m4 = WItemSlot.of(blockInventory, 10);
        WItemSlot m5 = WItemSlot.of(blockInventory, 11);
        WItemSlot m6 = WItemSlot.of(blockInventory, 12);

        int xMOffset = (int) ((2.25 * sizeOfSlot) - halfSlot);
        int yMOffset = (int) ((2.25 * sizeOfSlot) + halfSlot);

        root.add(m1, x-xMOffset, y-yMOffset);
        root.add(m2, x+xMOffset, y-yMOffset);

        root.add(m3, x+(3*sizeOfSlot), y);

        root.add(m4, x-xMOffset, y+yMOffset);
        root.add(m5, x+xMOffset, y+yMOffset);

        root.add(m6, x-(3*sizeOfSlot), y);


        WItemSlot a1 = WItemSlot.of(blockInventory, 13);
        WItemSlot a2 = WItemSlot.of(blockInventory, 14);
        WItemSlot a3 = WItemSlot.of(blockInventory, 15);
        WItemSlot a4 = WItemSlot.of(blockInventory, 16);

        root.add(a1, (int) (x - (sizeOfSlot * 3.75)), (int) (y - (sizeOfSlot * 3.75)));
        root.add(a2, (int) (x + (sizeOfSlot * 3.75)), (int) (y - (sizeOfSlot * 3.75)));
        root.add(a3, (int) (x + (sizeOfSlot * 3.75)), (int) (y + (sizeOfSlot * 3.75)));
        root.add(a4, (int) (x - (sizeOfSlot * 3.75)), (int) (y + (sizeOfSlot * 3.75)));




        /*context.run((world, blockpos) -> {
            //System.out.println("hell");

        });
        BlockPos blockpos = new BlockPos(propertyDelegate.get(0), propertyDelegate.get(1), propertyDelegate.get(2));
//        System.out.println(blockpos);
        //System.out.println(world);


            CalderaCauldronBlockEntity calderaCauldronBlockEntity = null;
            for (Direction direction : Direction.values()) {
                if (world.getBlockState(blockpos.offset(direction)).getBlock() == ModBlocks.CALDERA_CAULDRON) {

                    calderaCauldronBlockEntity = (CalderaCauldronBlockEntity) world.getBlockEntity(blockpos.offset(direction));
                    WSprite fluidSprit = new WSprite(calderaCauldronBlockEntity.fluidInv.getInvFluid(0).getFlowingSprite());
                    System.out.println(calderaCauldronBlockEntity.fluidInv.getInvFluid(0).getFlowingSprite());
                    root.add(fluidSprit, 5, 2);
                }
            }*/


        //WSprite fluidSprite = new WSprite(new Identifier(MOD_ID, "textures/block/water_flow.png"));
        //root.add(fluidSprite, 1, 1);
        root.add(this.createPlayerInventoryPanel(), (halfSlot/2), 175);

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
