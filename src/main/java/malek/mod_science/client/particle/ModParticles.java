package malek.mod_science.client.particle;

import malek.mod_science.ModScience;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;


public class ModParticles {

    public static DefaultParticleType RAINBOW;

   public static void init() {
       RAINBOW = Registry.register(Registry.PARTICLE_TYPE, new Identifier(ModScience.MOD_ID, "rainbow"), FabricParticleTypes.simple());

       ParticleFactoryRegistry.getInstance().register(RAINBOW, RainbowParticle.Factory::new);

   }
}
