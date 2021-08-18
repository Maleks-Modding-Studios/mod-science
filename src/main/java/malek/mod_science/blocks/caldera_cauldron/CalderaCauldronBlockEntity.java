package malek.mod_science.blocks.caldera_cauldron;

import alexiil.mc.lib.attributes.*;
import alexiil.mc.lib.attributes.fluid.amount.FluidAmount;
import alexiil.mc.lib.attributes.fluid.impl.SimpleFixedFluidInv;
import alexiil.mc.lib.attributes.fluid.volume.FluidVolume;
import malek.mod_science.blocks.FluidInvGetter;
import malek.mod_science.blocks.ModBlockEntities;
import malek.mod_science.power.FindPowerPathsToGenerators;
import malek.mod_science.power.IIPowerGenerator;
import malek.mod_science.power.IPowerReceiver;
import malek.mod_science.power.PowerPath;
import malek.mod_science.properties.ModProperties;
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
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Set;

import static malek.mod_science.power.Side.IN;

public class CalderaCauldronBlockEntity extends BlockEntity implements BlockEntityClientSerializable, IIPowerGenerator, IPowerReceiver, FluidInvGetter {

    public static final FluidAmount CAPACITY = FluidAmount.BUCKET.mul(1);
    FindPowerPathsToGenerators findPowerPathsToGenerators;
    public final SimpleFixedFluidInv fluidInv;
    boolean networkDirty = false;
    boolean isDirty = false;
    public CalderaCauldronBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CALDERA_CAULDRON_BLOCK_ENTITY, pos, state);
        fluidInv = new SimpleFixedFluidInv(1, CAPACITY);
    }
    public static <T extends BlockEntity> void tick(World world, BlockPos blockPos, BlockState state, T t) {
        if(world.isClient())
            return;
        ((CalderaCauldronBlockEntity)t).tick(world, blockPos, state);
    }
    @Override
    public void markDirty() {
        isDirty = true;
    }
    private void tick(World world, BlockPos blockPos, BlockState state) {
        if(findPowerPathsToGenerators == null || networkDirty) {
            findPaths();
            networkDirty = false;
        }
        if(isDirty) {
            this.isDirty = false;
            this.sync();
        }
        this.tryPowerTransfer();
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
        super.writeNbt(tag);
        tag = super.writeNbt(tag);
        FluidVolume invFluid = fluidInv.getInvFluid(0);
        tag.put("fluid", invFluid.toTag());
        return writeNbt(tag);
    }

    @Override
    public void fromClientTag(NbtCompound tag) {
        readNbt(tag);
    }

    public void onPlacedBy(LivingEntity placer, ItemStack stack) {}

    public ActionResult onUse(PlayerEntity player, Hand hand, BlockHitResult hit) {
        return ActionResult.PASS;
    }

    @Override
    public void markNetworkDirty() {
        networkDirty = true;
    }

    @Override
    public boolean isNetworkDirty() {
        return networkDirty;
    }

    @Override
    public void findPaths() {
        findPowerPathsToGenerators = new FindPowerPathsToGenerators();
        long start = System.currentTimeMillis();
        for(Direction direction : Direction.values()) {
            BlockPos offsetPos = pos.offset(direction);
            if(world.getBlockState(pos).get(ModProperties.getSideFromDirection(direction)).equals(IN)) {
                findPowerPathsToGenerators.findTargets(world, new PowerPath(offsetPos));
            }
        }
        long time = System.currentTimeMillis()-start;
        System.out.println(time);
        for(PowerPath f : findPowerPathsToGenerators.paths) {
            System.out.println(f.toString());
        }
    }

    @Override
    public boolean wantsPower() {
        return !fluidInv.getInvFluid(0).amount().isGreaterThanOrEqual(CAPACITY);
    }

    @Override
    public Set<PowerPath> getPowerPaths() {
        return findPowerPathsToGenerators.paths;
    }

    @Override
    public double getFirePowerLevel() {
        return 0;
    }

    @Override
    public double getMaxFirePowerLevel() {
        return 0;
    }

    @Override
    public double getArcPowerLevel() {
        return 0;
    }

    @Override
    public double getMaxArcPowerLevel() {
        return 0;
    }

    @Override
    public double getLightPowerLevel() {
        return 0;
    }

    @Override
    public double getMaxLightPowerLevel() {
        return 0;
    }

    @Override
    public double getTimePowerLevel() {
        return 0;
    }

    @Override
    public double getMaxTimePowerLevel() {
        return 0;
    }


    @Override
    public SimpleFixedFluidInv getFluidInv() {
        return fluidInv;
    }
    @Override
    public FluidAmount getTransferRate() {
        return FluidAmount.of(1, 20);
    }
}
