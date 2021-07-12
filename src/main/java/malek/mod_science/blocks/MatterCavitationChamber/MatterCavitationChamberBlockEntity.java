package malek.mod_science.blocks.MatterCavitationChamber;

import malek.mod_science.items.ModItems;
import malek.mod_science.util.general.ImplementedInventory;
import malek.mod_science.util.general.MatterCavitationChamberScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static malek.mod_science.blocks.ModBlockEntities.MATTER_CAVITATION_CHAMBER_BLOCK_ENTITY;

public class MatterCavitationChamberBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(9, ItemStack.EMPTY);
    public int recipeTick;
    public static <T extends BlockEntity> void tick(World world, BlockPos blockPos, BlockState state, T t) {
        if(world.isClient()) {
            return;
        }else {
            ((MatterCavitationChamberBlockEntity) t).tick();
        }
    }

    public void tick() {
        for(int recipeLoopController = 0; recipeLoopController<9; recipeLoopController++) {
            if(inventory.get(recipeLoopController).isOf(Items.COBBLESTONE) && inventory.get(recipeLoopController).getCount() == 64){

                if(recipeTick >= 120) {
                    inventory.set(recipeLoopController, ModItems.SINGULITE_INGOT.getDefaultStack());
                    recipeTick = 0;
                }else{
                    recipeTick++;
                }

            }
        }


    }

    public MatterCavitationChamberBlockEntity(BlockPos pos, BlockState state) {
        super(MATTER_CAVITATION_CHAMBER_BLOCK_ENTITY, pos, state);
    }





    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;

    }

    //These Methods are from the NamedScreenHandlerFactory Interface
    //createMenu creates the ScreenHandler itself
    //getDisplayName will Provide its name which is normally shown at the top

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        //We provide *this* to the screenHandler as our class Implements Inventory
        //Only the Server has the Inventory at the start, this will be synced to the client in the ScreenHandler
        return new MatterCavitationChamberScreenHandler(syncId, playerInventory, this);
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText(getCachedState().getBlock().getTranslationKey());
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, this.inventory);
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, this.inventory);
        return nbt;
    }

    public BlockPos getBlockUp(BlockPos pos){
        BlockPos pos1 = pos.up();
        return pos1;
    }


}
