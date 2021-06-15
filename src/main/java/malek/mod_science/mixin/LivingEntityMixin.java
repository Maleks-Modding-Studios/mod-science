package malek.mod_science.mixin;

import malek.mod_science.components.player.madness.Madness;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

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
}
