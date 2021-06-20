package malek.mod_science.mixin;

import malek.mod_science.components.player.madness.Madness;
import malek.mod_science.util.general.LoggerInterface;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.apache.commons.logging.Log;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements LoggerInterface {
    @Shadow
    @Nullable
    protected PlayerEntity attackingPlayer;

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @ModifyArg(method = "dropXp",
               at = @At(value = "INVOKE",
                        target = "Lnet/minecraft/entity/ExperienceOrbEntity;spawn(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/Vec3d;I)V"),
               index = 2
    )
    private int multiplyXpByMadness(int originalXp) {
        if (this.attackingPlayer != null && Madness.get(this.attackingPlayer).isMedium()) {
            return (originalXp * Madness.getConfig().mediumMadness.xpMultiplier);
        }
        return originalXp;
    }


    /*
    @Inject(method = "travel", at = @At("HEAD"), cancellable = true)
    public void travelMixin(Vec3d movementInput, CallbackInfo ci) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        if (this.canMoveVoluntarily() || this.isLogicalSideForUpdatingMovement()) {
            double d = 0.08D;
            boolean bl = this.getVelocity().y <= 0.0D;
            Vec3d originalVelocity = this.getVelocity();
            if (bl && this.hasStatusEffect(StatusEffects.SLOW_FALLING)) {
                d = 0.01D;
            }

            FluidState fluidState = this.world.getFluidState(this.getBlockPos());
            float j;
            double e;
            Entity entity = (Entity) this;
            Method m = Entity.class.getMethod("isTouchingRewater");
            if (((Boolean)m.invoke(entity))) {
                e = this.getY();
                j = this.isSprinting() ? 0.9F : this.getBaseMovementSpeedMultiplier();
                float g = 0.02F;
                float h = (float) EnchantmentHelper.getDepthStrider((LivingEntity)(Object)this);
                if (h > 3.0F) {
                    h = 3.0F;
                }

                if (!this.onGround) {
                    h *= 0.5F;
                }

                if (h > 0.0F) {
                    j += (0.54600006F - j) * h / 3.0F;
                    g += (this.getMovementSpeed() - g) * h / 3.0F;
                }

                if (this.hasStatusEffect(StatusEffects.DOLPHINS_GRACE)) {
                    j = 0.96F;
                }

                this.updateVelocity(g, movementInput);
                this.move(MovementType.SELF, this.getVelocity());
                Vec3d vec3d = this.getVelocity();

                if (this.horizontalCollision) {
                    vec3d = new Vec3d(vec3d.x, 100D, vec3d.z);
                }
                double reverse = 2;
                this.setVelocity(vec3d.multiply((double)j * -reverse, 0.800000011920929D, (double)j *-reverse));
                Vec3d vec3d2 = this.method_26317(d, bl, this.getVelocity());
                this.setVelocity(vec3d2);
                /*
                if (this.horizontalCollision && this.doesNotCollide(vec3d2.x, vec3d2.y + 0.6000000238418579D - this.getY() + e, vec3d2.z)) {
                    this.setVelocity(vec3d2.x, 0.30000001192092896D, vec3d2.z);
                    }
                 */
                /*
                Vec3d changedVelocity = new Vec3d(this.getVelocity().getX() - originalVelocity.x, 0, this.getVelocity().z - originalVelocity.z);
                int reverseAmount = -4;
                this.setVelocity(new Vec3d(changedVelocity.x*reverseAmount, 0, changedVelocity.z*reverseAmount));
                this.setVelocity(originalVelocity.add(this.getVelocity()));

                 */
      /*      }
        }

    }
    */

    @Shadow
    public abstract float getMovementSpeed();

    @Shadow
    public abstract boolean hasStatusEffect(StatusEffect dolphinsGrace);

    @Shadow
    public abstract boolean isClimbing();

    @Shadow
    public abstract Vec3d method_26317(double d, boolean bl, Vec3d velocity);

    @Shadow
    public abstract boolean canWalkOnFluid(Fluid fluid);

    @Shadow
    public abstract boolean canMoveVoluntarily();

    @Shadow
    protected abstract float getBaseMovementSpeedMultiplier();

    @Shadow
    protected abstract boolean shouldSwimInFluids();

    @Override
    public Logger getLogger() {
        return LogManager.getLogger();
    }
}
