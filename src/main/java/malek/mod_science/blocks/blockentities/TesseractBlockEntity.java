package malek.mod_science.blocks.blockentities;


import io.github.cottonmc.cotton.gui.PropertyDelegateHolder;
import malek.mod_science.blocks.ModBlocks;
import malek.mod_science.blocks.TransfusionMatrixBlock;
import malek.mod_science.screens.TranfusionMatrixGuiDescription;
import malek.mod_science.screens.tesseractgui.TesseractGuiDescription;
import malek.mod_science.util.general.ImplementedInventory;
import malek.mod_science.util.general.LoggerInterface;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
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
import net.minecraft.world.explosion.Explosion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

public class TesseractBlockEntity extends BlockEntity implements LoggerInterface, ImplementedInventory, PropertyDelegateHolder, NamedScreenHandlerFactory, SidedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);
    private static final int MAX_PROGRESS = 20;
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
        return new TesseractGuiDescription(syncId, inv, ScreenHandlerContext.create(world, pos));
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


}
