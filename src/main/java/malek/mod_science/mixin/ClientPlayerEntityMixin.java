package malek.mod_science.mixin;

import com.mojang.authlib.GameProfile;
import malek.mod_science.dimensions.LSpaceDimension;
import malek.mod_science.util.general.MixinUtil;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {


    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }
    private static final double multiply = -0.01;
    @Inject(method = "tick", at = @At("HEAD"))
    public void tickMixin(CallbackInfo ci) {
        ClientPlayerEntity player = MixinUtil.cast(this);
        if (world.getRegistryKey().equals(LSpaceDimension.WORLD_KEY)) {
            for(BlockPos pos : BlockPos.iterateOutwards(player.getBlockPos(), 1, 1, 1)) {
                if (!world.getBlockState(pos).isAir()) {
                    player.setOnGround(true);
                }
            }
            //player.setNoGravity(true);
            for (BlockPos pos : BlockPos.iterateOutwards(player.getBlockPos(), 30, 30, 30)) {
                if (!world.getBlockState(pos).isAir()) {
                    BlockPos delta = player.getBlockPos().subtract(pos).multiply(1);
                    player.addVelocity(delta.getX()*multiply, delta.getY()*multiply, delta.getZ()*multiply);
                    break;
                }
            }
        }
    }

}
