package malek.mod_science.blocks.transfusionMatrix.gui;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import malek.mod_science.screens.ModScreens;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandlerContext;

public class TranfusionMatrixGuiDescription extends SyncedGuiDescription {
    private static final int INVENTORY_SIZE = 2;
    BlockEntity blockEntity;
    public TranfusionMatrixGuiDescription(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(ModScreens.TRANSFUSION_MATRIX_SCREEN_HANDLER, syncId, playerInventory, getBlockInventory(context, INVENTORY_SIZE), getBlockPropertyDelegate(context, 3));
        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(150, 150);
        root.setInsets(Insets.ROOT_PANEL);

        WItemSlot itemSlot = WItemSlot.of(blockInventory, 0);
        WItemSlot outputSlot = WItemSlot.of(blockInventory, 1);
        root.add(itemSlot, 4, 1);
        root.add(outputSlot, 4, 3);




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
        root.add(this.createPlayerInventoryPanel(), 0, 4);

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
