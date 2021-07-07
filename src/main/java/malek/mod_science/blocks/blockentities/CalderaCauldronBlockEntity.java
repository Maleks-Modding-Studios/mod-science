package malek.mod_science.blocks.blockentities;

import alexiil.mc.lib.attributes.*;
import alexiil.mc.lib.attributes.fluid.amount.FluidAmount;
import alexiil.mc.lib.attributes.fluid.impl.SimpleFixedFluidInv;
import alexiil.mc.lib.attributes.fluid.volume.FluidVolume;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class CalderaCauldronBlockEntity extends BlockEntity implements BlockEntityClientSerializable {

    private static final FluidAmount CAPACITY = FluidAmount.BUCKET.mul(16);

    public final SimpleFixedFluidInv fluidInv;

    public CalderaCauldronBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CALDERA_CAULDRON_BLOCK_ENTITY, pos, state);
        fluidInv = new SimpleFixedFluidInv(1, CAPACITY);
    }
    public static <T extends BlockEntity> void tick(World world, BlockPos blockPos, BlockState state, T t) {
        if(world.isClient())
            return;
        ((CalderaCauldronBlockEntity)t).tick(world, blockPos, state);
    }

    private void tick(World world, BlockPos blockPos, BlockState state) {
        //System.out.println("HI");
        System.out.println(fluidInv.getInvFluid(0).amount());
    }

    @Override
    public NbtCompound writeNbt(NbtCompound tag) {
        tag = super.writeNbt(tag);
        FluidVolume invFluid = fluidInv.getInvFluid(0);
        if (!invFluid.isEmpty()) {
            tag.put("fluid", invFluid.toTag());
        }
        return tag;
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
        if (tag.contains("fluid")) {
            FluidVolume fluid = FluidVolume.fromTag(tag.getCompound("fluid"));
            fluidInv.setInvFluid(0, fluid, Simulation.ACTION);
        }
    }
    @Nonnull
    public <T> T getNeighbourAttribute(CombinableAttribute<T> attr, Direction dir) {
        return attr.get(getWorld(), getPos().offset(dir), SearchOptions.inDirection(dir));
    }

    public DefaultedList<ItemStack> removeItemsForDrop() {
        return DefaultedList.of();
    }

    protected void sendPacket(ServerWorld w, NbtCompound tag) {
        tag.putString("id", BlockEntityType.getId(getType()).toString());
        sendPacket(w, new BlockEntityUpdateS2CPacket(getPos(), 127, tag));
    }

    protected void sendPacket(ServerWorld w, BlockEntityUpdateS2CPacket packet) {
        w.getPlayers(player -> player.squaredDistanceTo(Vec3d.of(getPos())) < 24 * 24)
         .forEach(player -> player.networkHandler.sendPacket(packet));
    }

    @Override
    public NbtCompound toClientTag(NbtCompound tag) {
        tag = super.writeNbt(tag);
        FluidVolume invFluid = fluidInv.getInvFluid(0);
        if (!invFluid.isEmpty()) {
            tag.put("fluid", invFluid.toTag());
        }
        return tag;
    }

    @Override
    public void fromClientTag(NbtCompound tag) {
        super.readNbt(tag);
        if (tag.contains("fluid")) {
            FluidVolume fluid = FluidVolume.fromTag(tag.getCompound("fluid"));
            fluidInv.setInvFluid(0, fluid, Simulation.ACTION);
        }
    }

    public void onPlacedBy(LivingEntity placer, ItemStack stack) {}

    public ActionResult onUse(PlayerEntity player, Hand hand, BlockHitResult hit) {
        return ActionResult.PASS;
    }
}
