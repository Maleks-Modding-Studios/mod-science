package malek.mod_science.blocks.blockentities;

import alexiil.mc.lib.attributes.Simulation;
import alexiil.mc.lib.attributes.fluid.volume.FluidVolume;
import io.github.cottonmc.cotton.gui.PropertyDelegateHolder;
import malek.mod_science.blocks.ModBlocks;
import malek.mod_science.blocks.TransfusionMatrixBlock;
import malek.mod_science.screens.TranfusionMatrixGuiDescription;
import malek.mod_science.util.general.ImplementedInventory;
import malek.mod_science.util.general.LoggerInterface;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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

public class TransfusionMatrixBlockEntity extends BlockEntity implements LoggerInterface, ImplementedInventory, PropertyDelegateHolder, NamedScreenHandlerFactory, SidedInventory, BlockEntityClientSerializable {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);
    private static final int MAX_PROGRESS = 20;
    private int currentProgress = 0;
    int x = 0;
    int y = 0;
    int z = 0;
    boolean isDirty = false;
    public TransfusionMatrixBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TRANSFUSION_MATRIX_BLOCK_ENTITY, pos, state);
        this.x = this.getPos().getX();
        this.y = this.getPos().getY();
        this.z = this.getPos().getZ();
    }
    public static <T extends BlockEntity> void tick(World world, BlockPos blockPos, BlockState state, T t) {
        if(world.isClient())
            return;
        ((TransfusionMatrixBlockEntity)t).tick(world, blockPos, state);
    }

    private void tick(World world, BlockPos blockPos, BlockState state) {
            TransfusionMatrixBlock block = (TransfusionMatrixBlock) state.getBlock();
            if(isDirty) {
                this.isDirty = false;
                this.sync();
            }
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
        return null;
    }

    @Override
    public Text getDisplayName() {
        return new LiteralText("Transfusion Matrix");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new TranfusionMatrixGuiDescription(syncId, inv, ScreenHandlerContext.create(world, pos));
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

    public ItemStack putItemInPlayerHand(PlayerEntity playerEntity) {
        ItemStack stack = new ItemStack(this.getItems().get(0).getItem(), this.getItems().get(0).getCount());
        this.getItems().set(0, ItemStack.EMPTY);
        markDirty();
        return stack;
    }

    public void takeItemFromPlayerHand(PlayerEntity playerEntity, ItemStack stack) {
        if(!this.getItems().get(0).isEmpty()) {
            return;
        }
        this.getItems().set(0, stack);
        playerEntity.getInventory().setStack(playerEntity.getInventory().selectedSlot, ItemStack.EMPTY);
        markDirty();
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
        Inventories.readNbt(tag, this.inventory);
    }
    @Override
    public NbtCompound writeNbt(NbtCompound tag) {
        super.writeNbt(tag);
        tag = super.writeNbt(tag);
        Inventories.writeNbt(tag, this.inventory);
        return tag;
    }

    @Override
    public void fromClientTag(NbtCompound tag) {
        clear();
        Inventories.readNbt(tag, this.inventory);
    }

    @Override
    public NbtCompound toClientTag(NbtCompound tag) {
        writeNbt(tag);
        return tag;
    }

    @Override
    public void markDirty() {
        isDirty = true;
    }
}
