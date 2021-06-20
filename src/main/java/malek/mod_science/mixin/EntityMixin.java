package malek.mod_science.mixin;

import malek.mod_science.fluids.ModFluidBlocks;
import malek.mod_science.util.general.LoggerInterface;
import net.minecraft.block.FluidBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin implements LoggerInterface {
    @Shadow
    public boolean horizontalCollision;
    private boolean isTouchingRewater;

    @Shadow
    public World world;

    @Shadow
    protected boolean firstUpdate;
    @Shadow
    public float fallDistance;

    @Shadow
    protected void onSwimmingStart() {

    }

    public boolean isTouchingRewater() {
        return isTouchingRewater;
    }

    @Inject(method = "checkWaterState", at = @At("HEAD"), cancellable = true)
    public void checkWaterStateMixin(CallbackInfo ci) throws NoSuchMethodException {

         if (this.updateMovementInFluid(ModFluidBlocks.REWATER, 0.014D)) {
            if (!this.isTouchingRewater && !this.firstUpdate) {
                this.onSwimmingStart();
            }

            this.fallDistance = 0.0F;
            this.isTouchingRewater = true;
            this.extinguish();
        } else {
            this.isTouchingRewater = false;
        }
    }
    @Shadow
    public abstract void extinguish();

    public boolean updateMovementInFluid(FluidBlock fluidBlock, double d) {
        if (this.isRegionUnloaded()) {
            return false;
        } else {
            Box box = this.getBoundingBox().contract(0.001D);
            int i = MathHelper.floor(box.minX);
            int j = MathHelper.ceil(box.maxX);
            int k = MathHelper.floor(box.minY);
            int l = MathHelper.ceil(box.maxY);
            int m = MathHelper.floor(box.minZ);
            int n = MathHelper.ceil(box.maxZ);
            double e = 0.0D;
            boolean bl = this.isPushedByFluids();
            boolean bl2 = false;
            Vec3d vec3d = Vec3d.ZERO;
            int o = 0;
            BlockPos.Mutable mutable = new BlockPos.Mutable();

            for(int p = i; p < j; ++p) {
                for(int q = k; q < l; ++q) {
                    for(int r = m; r < n; ++r) {
                        mutable.set(p, q, r);
                        FluidState fluidState = this.world.getFluidState(mutable);
                        if (fluidState.getBlockState().getBlock() == fluidBlock) {
                            double f = (double)((float)q + fluidState.getHeight(this.world, mutable));

                            if (f >= box.minY) {
                                bl2 = true;
                                e = Math.max(f - box.minY, e);
                                if (bl) {
                                    Vec3d vec3d2 = fluidState.getVelocity(this.world, mutable);
                                    if (e > -0.4D) {
                                        vec3d2 = vec3d2.multiply(-1*e);
                                    }

                                    vec3d = vec3d.add(vec3d2);
                                    ++o;
                                }
                            }

                        }
                    }
                }
            }

            if (vec3d.length() > 0.0D) {
                if (o > 0) {
                    vec3d = vec3d.multiply(1.0D / (double)o);
                }

                if (!(((Object)this) instanceof PlayerEntity)) {
                    vec3d = vec3d.normalize();
                }

                Vec3d vec3d3 = this.getVelocity();
                vec3d = vec3d.multiply(d * 1.0D);
                double g = 0.003D;
                if (Math.abs(vec3d3.x) < 0.003D && Math.abs(vec3d3.z) < 0.003D && vec3d.length() < 0.0045000000000000005D) {
                    vec3d = vec3d.normalize().multiply(0.0045000000000000005D);
                }
                if(this.horizontalCollision) {
                    log("horizontal collision");
                    this.setVelocity(this.getVelocity().add(new Vec3d(0, 0.09, 0)));
                }
                this.setVelocity(this.getVelocity().add(vec3d));
            }

            return bl2;
        }
    }

    @Shadow
    public abstract Box getBoundingBox();

    @Shadow
    public abstract boolean isPushedByFluids();

    @Shadow
    public abstract void setVelocity(Vec3d add);


    @Shadow
    public abstract Vec3d getVelocity();

    @Shadow
    public abstract boolean isRegionUnloaded();

    @Override
    public Logger getLogger() {
        return LogManager.getLogger();
    }
}
