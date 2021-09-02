package malek.mod_science.items;

import net.minecraft.fluid.Fluid;
import net.minecraft.server.network.ServerPlayerEntity;

public class LividShadow extends Shadow{


    private static ServerPlayerEntity owner;
    public LividShadow(Fluid fluid, Settings settings, ServerPlayerEntity entity) {
        super(fluid, settings);
        owner = entity;
    }
    public static ServerPlayerEntity getOwner(LividShadow shadow){
        return shadow.owner;
    }
    public static void setOwner(ServerPlayerEntity owner1){
        owner = owner1;
    }

}
