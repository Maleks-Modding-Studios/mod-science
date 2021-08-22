package malek.mod_science.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.FabricSpriteProvider;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

@Environment(EnvType.CLIENT)
public class RainbowParticle extends AnimatedParticle {
    RainbowParticle(ClientWorld clientWorld, double d, double e, double f, double g, double h, double i, FabricSpriteProvider spriteProvider) {
        super(clientWorld, d, e, f, spriteProvider, 0.0125F);
        this.velocityX = g;
        this.velocityY = h;
        this.velocityZ = i;
        this.scale *= 0.75F;
        this.maxAge = 60 + this.random.nextInt(12);
        this.setTargetColor(15916745);
        this.setSpriteForAge(spriteProvider);
    }

    public void move(double dx, double dy, double dz) {
        this.setBoundingBox(this.getBoundingBox().offset(dx, dy, dz));
        this.repositionFromBoundingBox();
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final FabricSpriteProvider spriteProvider;

        public Factory(FabricSpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return new RainbowParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider);
        }
    }
}
