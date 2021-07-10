package malek.mod_science.blocks.blockentities;

import alexiil.mc.lib.attributes.Simulation;
import alexiil.mc.lib.attributes.fluid.amount.FluidAmount;
import alexiil.mc.lib.attributes.fluid.impl.SimpleFixedFluidInv;
import alexiil.mc.lib.attributes.fluid.volume.FluidKeys;
import alexiil.mc.lib.attributes.fluid.volume.FluidVolume;
import io.github.cottonmc.cotton.gui.PropertyDelegateHolder;
import malek.mod_science.blocks.TransfusionMatrixBlock;
import malek.mod_science.custom_recipes.ExtractionRecipe;
import malek.mod_science.custom_recipes.InsertionRecipe;
import malek.mod_science.custom_recipes.Recipe;
import malek.mod_science.items.ModItems;
import malek.mod_science.items.item_nbt.ChargeableItem;
import malek.mod_science.screens.TranfusionMatrixGuiDescription;
import malek.mod_science.util.TranfusionMatrixMode;
import malek.mod_science.util.general.ImplementedInventory;
import malek.mod_science.util.general.LoggerInterface;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static malek.mod_science.util.TranfusionMatrixMode.EXTRACT;
import static malek.mod_science.util.TranfusionMatrixMode.INSERT;

public class TransfusionMatrixBlockEntity extends BlockEntity implements LoggerInterface, ImplementedInventory, PropertyDelegateHolder, NamedScreenHandlerFactory, SidedInventory, BlockEntityClientSerializable {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);
    public static final FluidAmount CAPACITY = FluidAmount.BUCKET.mul(1);

    public final Set<Recipe> INSERTION_RECIPES = new HashSet<>();
    public final Set<Recipe> EXTRACTION_RECIPE = new HashSet<>();
    TranfusionMatrixMode mode = INSERT;
    public final SimpleFixedFluidInv fluidInv;
    private static final int MAX_PROGRESS = 20;
    boolean isDirty = false;
    public TransfusionMatrixBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TRANSFUSION_MATRIX_BLOCK_ENTITY, pos, state);
        fluidInv = new SimpleFixedFluidInv(1, CAPACITY);
        addInsertRecipe(Items.GOLD_INGOT, FluidKeys.get(Fluids.WATER).withAmount(FluidAmount.BUCKET), ModItems.AMALGAMETAL);
        addExtractRecipe(ModItems.MOLTEN_CORE, FluidKeys.get(Fluids.LAVA).withAmount(FluidAmount.BUCKET), ModItems.MOLTEN_CORE, ChargeableItem::isCharged);
        addInsertRecipe(ModItems.MOLTEN_CORE, FluidKeys.get(Fluids.LAVA).withAmount(FluidAmount.BUCKET), ModItems.MOLTEN_CORE, (s) -> !ChargeableItem.isCharged(s), (s) -> ChargeableItem.setCharged(s, true));
    }
    public void addInsertRecipe(Item item, FluidVolume fluidVolume, Item item2, Predicate<ItemStack> predicate, Consumer<ItemStack> consumer) {
        INSERTION_RECIPES.add(new InsertionRecipe(this, fluidInv, item, fluidVolume, item2, predicate, consumer));
    }
    public void addInsertRecipe(Item item, FluidVolume fluidVolume, Item item2) {
        addInsertRecipe(item, fluidVolume, item2, (s) -> true);
    }
    public void addInsertRecipe(Item item, FluidVolume fluidVolume, Item item2, Predicate<ItemStack> predicate) {
        addInsertRecipe(item, fluidVolume, item2, predicate, (s) -> {});
    }




    public void addExtractRecipe(Item item, FluidVolume fluidVolume, Item item2) {
        addExtractRecipe(item, fluidVolume, item2);
    }
    public void addExtractRecipe(Item item, FluidVolume fluidVolumem, Item item2, Predicate<ItemStack> predicate) {
        addExtractRecipe(item, fluidVolumem, item2, predicate, (s) -> {});
    }
    public void addExtractRecipe(Item item, FluidVolume fluidVolume, Item item2, Predicate<ItemStack> predicate, Consumer<ItemStack> consumer) {
        EXTRACTION_RECIPE.add(new ExtractionRecipe(this, fluidInv, item, fluidVolume, item2, predicate, consumer));
    }


    public void toggleMode() {
        if(this.mode == INSERT) {
            this.mode = EXTRACT;
        } else {
            this.mode = INSERT;
        }
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
            if(mode == INSERT) {
                for(Recipe insertionRecipe : INSERTION_RECIPES) {
                    if(insertionRecipe.matches()) {
                        insertionRecipe.doRecipe();
                    }
                }
            } else {
                for(Recipe extractionRecipe : EXTRACTION_RECIPE) {
                    if(extractionRecipe.matches()) {
                        extractionRecipe.doRecipe();
                    }
                }
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

    protected void sendPacket(ServerWorld w, BlockEntityUpdateS2CPacket packet) {
        w.getPlayers(player -> player.squaredDistanceTo(Vec3d.of(getPos())) < 24 * 24)
         .forEach(player -> player.networkHandler.sendPacket(packet));
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
        ItemStack stack = this.getStack(0).copy();
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
        clear();
        Inventories.readNbt(tag, this.inventory);
        if (tag.contains("fluid")) {
            FluidVolume fluid = FluidVolume.fromTag(tag.getCompound("fluid"));
            fluidInv.setInvFluid(0, fluid, Simulation.ACTION);
        }
    }
    @Override
    public NbtCompound writeNbt(NbtCompound tag) {
        super.writeNbt(tag);
        tag = super.writeNbt(tag);
        Inventories.writeNbt(tag, this.inventory);
        FluidVolume invFluid = fluidInv.getInvFluid(0);
        if (!invFluid.isEmpty()) {
            tag.put("fluid", invFluid.toTag());
        }
        return tag;
    }

    @Override
    public void fromClientTag(NbtCompound tag) {
        clear();
        Inventories.readNbt(tag, this.inventory);
        if (tag.contains("fluid")) {
            FluidVolume fluid = FluidVolume.fromTag(tag.getCompound("fluid"));
            fluidInv.setInvFluid(0, fluid, Simulation.ACTION);
        }
    }

    @Override
    public NbtCompound toClientTag(NbtCompound tag) {
        writeNbt(tag);
        tag = super.writeNbt(tag);
        FluidVolume invFluid = fluidInv.getInvFluid(0);
        tag.put("fluid", invFluid.toTag());
        return tag;
    }

    @Override
    public void markDirty() {
        isDirty = true;
    }
}
