package malek.mod_science.mixin;

import malek.mod_science.blocks.ModBlocks;
import malek.mod_science.components.player.madness.Madness;
import malek.mod_science.components.player.timepiece.TimePieceUtils;
import malek.mod_science.worlds.dimensions.LSpaceDimension;
import malek.mod_science.statuseffects.ModStatusEffects;
import malek.mod_science.items.ModItems;
import malek.mod_science.util.general.LoggerInterface;
import malek.mod_science.util.general.MixinUtil;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTracker;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements LoggerInterface {
    @Shadow
    @Nullable
    protected PlayerEntity attackingPlayer;
    @Shadow public abstract Iterable<ItemStack> getArmorItems();
    @Shadow public abstract int getArmor();
    @Shadow @Final
    private DefaultedList<ItemStack> equippedArmor;
    public Boolean isItemEquipped = false;
    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @Shadow public abstract DamageTracker getDamageTracker();



    @Shadow protected abstract float applyArmorToDamage(DamageSource source, float amount);

    @Shadow public abstract ItemStack getActiveItem();

    @Shadow public abstract ItemStack getMainHandStack();

    @Shadow protected int riptideTicks;

    @Shadow protected abstract boolean blockedByShield(DamageSource source);

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
    @Inject(method = "jump()V", at = @At("TAIL"))
    private void injectJumpMethod(CallbackInfo info) {

        if (this.hasStatusEffect(ModStatusEffects.STICKY)) {
            setVelocity(getVelocity().subtract(0, 1, 0));
        }

        for(ItemStack item : this.getArmorItems())
        {
            if(item.getItem() == ModItems.BOOTS_OF_STRIDING) {
                setVelocity(getVelocity().add(0, 0.25F, 0));
            } else {
                setVelocity(getVelocity().add(0,0,0));
            }
        }

    }

    private static final double multiply = 1;

    @Inject(method = "tick", at = @At("HEAD"))
    private void lSpaceGravity(CallbackInfo info) {
        LivingEntity player = MixinUtil.cast(this);
        if (this.jumping) {
            if(this.world.getRegistryKey().equals(LSpaceDimension.WORLD_KEY)) {
                for (BlockPos pos : BlockPos.iterateOutwards(player.getBlockPos(), 30, 30, 30)) {
                    if (!world.getBlockState(pos).isAir()) {
                        BlockPos delta = player.getBlockPos().subtract(pos).multiply(1);
                        player.addVelocity(delta.getX() * multiply, delta.getY() * multiply, delta.getZ() * multiply);
                        break;
                    }
                }
            }
        }
        this.velocityDirty = true;
        return;
    }
    @Inject(method = "tick", at = @At("HEAD"))
    private void timePieceTick(CallbackInfo callbackInfo){
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        if(!world.isClient){
            if(livingEntity instanceof PlayerEntity){
                PlayerEntity user = (PlayerEntity) (Object) this;
                SoundEvent soundEventClick = SoundEvents.BLOCK_LEVER_CLICK;
                if(TimePieceUtils.getTimePieceTicks(user) >= 1){
                    if(TimePieceUtils.getTimePieceTicks(user) % 20 == 0){
                        world.playSound((PlayerEntity)null, user.getX(), user.getY(), user.getZ(), soundEventClick, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    }
                    if(TimePieceUtils.getTimePieceTicks(user) == 1){
                        System.out.println(user);
                        double d = user.getX();
                        double e = user.getY();
                        double f = user.getZ();

                        for(int i = 0; i < 16; ++i) {
                            double g = user.getX() + (user.getRandom().nextDouble() - 0.5D) * 16.0D;
                            double h = MathHelper.clamp(user.getY() + (double)(user.getRandom().nextInt(16) - 8), (double)world.getBottomY(), (double)(world.getBottomY() + ((ServerWorld)world).getLogicalHeight() - 1));
                            double j = user.getZ() + (user.getRandom().nextDouble() - 0.5D) * 16.0D;
                            if (user.hasVehicle()) {
                                user.stopRiding();
                            }

                            if (user.teleport(g, h, j, true)) {
                                SoundEvent soundEvent = SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT;
                                world.playSound((PlayerEntity)null, d, e, f, soundEvent, SoundCategory.PLAYERS, 1.0F, 1.0F);
                                user.playSound(soundEvent, 1.0F, 1.0F);
                                break;
                            }
                        }
                    }
                }
            }

        }
    }
    @Shadow
    protected abstract StatusEffectInstance getStatusEffect(StatusEffect jumpBoost);

    @Shadow
    protected abstract boolean hasStatusEffect(StatusEffect jumpBoost);

    @Shadow
    protected abstract float getJumpVelocity();


    @Shadow
    protected boolean jumping;
    public double posX =  getX();
    public double posY =  getY()-1;
    public double posZ =  getZ();

    @Inject(method = "tickMovement", at = @At("TAIL"))
    private void injectTickMovementMethod(CallbackInfo info) {

        for(ItemStack item : this.getArmorItems())
        {
            if(item.getItem() == ModItems.BOOTS_OF_STRIDING && this.world.getBlockState(this.getBlockPos().down(1)).getBlock() == Blocks.WATER)
            {
                this.world.setBlockState(this.getBlockPos().down(1), ModBlocks.WATER_STRIDE_BLOCK.getDefaultState(), 2);


            }else if(item.getItem() == ModItems.BOOTS_OF_STRIDING && this.world.getBlockState(this.getBlockPos().down(1)).getBlock() == Blocks.LAVA) {
                this.world.setBlockState(this.getBlockPos().down(1), ModBlocks.LAVA_STRIDE_BLOCK.getDefaultState(), 2);
            }

        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void injectTickMethod(CallbackInfo info) {
        //I hate java sometimes, and this is one of them. took an hour to figure that out.
        if(getEquippedStack(EquipmentSlot.FEET).getItem() == ModItems.BOOTS_OF_STRIDING){
            this.stepHeight = 1.6F;
        }else {
            this.stepHeight = 0.6F;
        }

//        if(this.getMainHandStack().getItem() == DevilryWeaponItems.WATER_CRYSTAL_SPEAR && this.world.getBlockState(this.getBlockPos().down(1)).getBlock() != Blocks.AIR && this.fallDistance >= 3){
//
//            this.fallDistance = 0;
//
//        }
        //PARTICLE EFFECTS FOR DEVS
        if(this.getUuidAsString() == "aefec30a-f16e-4d1e-a884-a9a28bc09d01") {
            //world.addParticle(Devilrycraft.JAVA_CUP, this.posX, this.posY, this.posZ, 0.5, 0.5, 0.5);
        }else if(this.getUuidAsString() == "2e7daccf-0eb0-4b5d-b102-93d38af50029"){
            //world.addParticle(new DustParticleEffect(((float)this.getX()), ((float) this.getY()), ((float)this.getZ()), 1.0F), this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
        }


    }




    @Override
    public Logger getLogger() {
        return LogManager.getLogger();
    }
}
