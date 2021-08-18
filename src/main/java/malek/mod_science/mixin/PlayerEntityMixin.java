package malek.mod_science.mixin;

import malek.mod_science.worlds.dimensions.TheRoomDimension;
import malek.mod_science.util.general.LoggerInterface;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements LoggerInterface {
    // *Heavy New-York Accent*
    // HEY YOU! YA! IM TALKING TO YOU, DA FUCK YOU DOING IN PLAYER ENTITY MIXIN???
    // If ya wanna get shit done, do it where it counts, so it won't crash the game
    // The minute we try and launch a server, aka, unless ur doing client specific stuff
    // Do it in SERVERPLAYERENTITYMIXIN ya doofus.
    // Okay bye see ya
    // Also tell ya ma i love her cooking.
    // Mwa

    // hehe. that made my day. thanks malek. - gamma
    @Inject(method = "isBlockBreakingRestricted", at = @At("HEAD"), cancellable = true)
    public void isBlockBreakingRestricted(World world, BlockPos pos, GameMode gameMode, CallbackInfoReturnable<Boolean> cir) {
        if(world.getRegistryKey() == TheRoomDimension.WORLD_KEY)
        {
            cir.setReturnValue(false);
        }
        cir.setReturnValue(false);
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void tickMixin(CallbackInfo ci) {

    }
    @Override
    public Logger getLogger() {
        return LogManager.getLogger();
    }
}
