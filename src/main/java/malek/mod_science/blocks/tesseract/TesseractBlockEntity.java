package malek.mod_science.blocks.tesseract;


import io.github.cottonmc.cotton.gui.PropertyDelegateHolder;
import malek.mod_science.blocks.ModBlockEntities;

import malek.mod_science.blocks.tesseract.tesseractgui.HexcraftingGuiDescription;
import malek.mod_science.blocks.tesseract.tesseractgui.HexcraftingScreen;
import malek.mod_science.blocks.tesseract.tesseractgui.UpgradedHexcraftingGuiDescription;
import malek.mod_science.blocks.tesseract.tesseractgui.WyldTesseractCraftingGuiDescription;
import malek.mod_science.recipes.hex_crafting.basic.BasicHexcraftingRecipe;
import malek.mod_science.recipes.hex_crafting.basic.BasicType;
import malek.mod_science.recipes.hex_crafting.upgraded.UpgradedHexcraftingRecipe;
import malek.mod_science.recipes.hex_crafting.upgraded.UpgradedType;
import malek.mod_science.recipes.hex_crafting.wylds.WyldsHexcraftingRecipe;
import malek.mod_science.recipes.hex_crafting.wylds.WyldsType;
import malek.mod_science.util.general.ImplementedInventory;
import malek.mod_science.util.general.LoggerInterface;
import malek.mod_science.worlds.dimensions.WyldsDimension;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TesseractBlockEntity extends BlockEntity implements LoggerInterface, ImplementedInventory, PropertyDelegateHolder, NamedScreenHandlerFactory, SidedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(17, ItemStack.EMPTY);
    private static final int MAX_PROGRESS = 20;//why
    private int currentCraftingTicks = 0;
    private int currentProgress = 0;
    int x = 0;
    int y = 0;
    int z = 0;
    public TesseractBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TESSERACT_BLOCK_ENTITY, pos, state);
        this.x = this.getPos().getX();
        this.y = this.getPos().getY();
        this.z = this.getPos().getZ();
    }
    public static <T extends BlockEntity> void tick(World world, BlockPos blockPos, BlockState state, T t) {
        if(world.isClient())
            return;
        ((TesseractBlockEntity)t).tick(world, blockPos, state);
    }
    private void tick(World world, BlockPos blockPos, BlockState state){

    }

    @Override
    public Logger getLogger() {
        return LogManager.getLogger();
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public PropertyDelegate getPropertyDelegate() {
        return propertyDelegate;
    }

    @Override
    public Text getDisplayName() {
        return new LiteralText("Tesseract");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new HexcraftingGuiDescription(syncId, inv, ScreenHandlerContext.create(world, pos));
        //this doesn't work yet lul
//        if (getVaildUpgradedHexcraftingStructure(this)) {
//            return new UpgradedHexcraftingGuiDescription(syncId, inv, ScreenHandlerContext.create(world, pos));
//        }else if(getVaildUpgradedHexcraftingStructure(this) && isInWylds(this)){
//            return new WyldTesseractCraftingGuiDescription(syncId, inv, ScreenHandlerContext.create(world, pos));
//        }else{
//            return new HexcraftingGuiDescription(syncId, inv, ScreenHandlerContext.create(world, pos));
//        }
    }

    private final PropertyDelegate propertyDelegate = new PropertyDelegate() {
        @Override
        public int get(int index) {
            switch (index) {
                case 0: return getPos().getX();
                case 1 : return getPos().getY();
                case 2 : return getPos().getZ();
            }
            return 0;
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0: x = value; break;
                case 1 : y = value; break;
                case 2 : z = value; break;
            }

        }

        @Override
        public int size() {
            return 3;
        }
    };
    public void testCraft(){
        List<BasicHexcraftingRecipe> matchBasic = world.getRecipeManager().getAllMatches(BasicType.INSTANCE, this::getItems, world);
        List<UpgradedHexcraftingRecipe> matchUpgraded = world.getRecipeManager().getAllMatches(UpgradedType.INSTANCE, this::getItems, world);
        List<WyldsHexcraftingRecipe> matchWylds = world.getRecipeManager().getAllMatches(WyldsType.INSTANCE, this::getItems, world);
        for(int i = 0; 1 < matchBasic.size(); i++){
            if(matchBasic.get(i).matches(this::getItems, world)){
                currentCraftingTicks++;
                if(currentCraftingTicks >= matchBasic.get(i).TICKS){
                    currentCraftingTicks = 0;
                    doCraft(matchBasic.get(i));

                }
            }
        }
        for(int i = 0; 1 < matchUpgraded.size(); i++){
            if(matchUpgraded.get(i).matches(this::getItems, world)){
                currentCraftingTicks++;
                if(currentCraftingTicks >= matchUpgraded.get(i).TICKS){
                    currentCraftingTicks = 0;
                    doCraft(matchUpgraded.get(i));

                }
            }
        }
        for(int i = 0; 1 < matchWylds.size(); i++){
            if(matchWylds.get(i).matches(this::getItems, world)){
                currentCraftingTicks++;
                if(currentCraftingTicks >= matchWylds.get(i).TICKS){
                    currentCraftingTicks = 0;
                    doCraft(matchWylds.get(i));

                }
            }
        }
    }

    @Override
    public int[] getAvailableSlots(Direction var1) {
        // Just return an array of all slots
        int[] result = new int[getItems().size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = i;
        }

        return result;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, Direction direction) {
        return direction != Direction.UP;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction direction) {
        return true;
    }

    public void doCraft(BasicHexcraftingRecipe recipe){
        if(this.getItems().get(1).getCount() == 1){
            for(int i2 = 0; i2 < this.size(); i2++){
                if(!this.getItems().get(i2).isEmpty()){
                    this.getItems().get(i2).decrement(1);
                }
            }
            this.getItems().set(12, recipe.craft(this::getItems));
        }
    }
    public void doCraft(UpgradedHexcraftingRecipe recipe){
        if(this.getItems().get(1).getCount() == 1){
            for(int i2 = 0; i2 < this.size(); i2++){
                if(!this.getItems().get(i2).isEmpty()){
                    this.getItems().get(i2).decrement(1);
                }
            }
            this.getItems().set(12, recipe.craft(this::getItems));
        }
    }
    public void doCraft(WyldsHexcraftingRecipe recipe){
        if(this.getItems().get(1).getCount() == 1){
            for(int i2 = 0; i2 < this.size(); i2++){
                if(!this.getItems().get(i2).isEmpty()){
                    this.getItems().get(i2).decrement(1);
                }
            }
            this.getItems().set(12, recipe.craft(this::getItems));
        }
    }
    public static Boolean getVaildUpgradedHexcraftingStructure(BlockEntity block){
        //here we get if there is a valid upgraded hexcrafting structure around the block, will be implimented later, so now it just returns true lol

        return false;
    }
    public static Boolean isInWylds(BlockEntity block){
        return block.getWorld().getRegistryKey().equals(WyldsDimension.WORLD_KEY);
    }
}
