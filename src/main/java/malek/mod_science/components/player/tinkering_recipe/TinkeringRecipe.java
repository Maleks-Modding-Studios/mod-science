package malek.mod_science.components.player.tinkering_recipe;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import static malek.mod_science.ModScience.MOD_ID;

public class TinkeringRecipe implements EntityComponentInitializer, AutoSyncedComponent {
    public static final ComponentKey<TinkeringRecipe> TINKERING_RECIPE = ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(MOD_ID, "tinkering_recipe"), TinkeringRecipe.class);

    //TODO: FIGURE OUT HOW TO ACTUALLY WANT TO DO THIS STUFF
    private double wyldSap = 0;

    public static TinkeringRecipe get(Entity entity) {
        return TINKERING_RECIPE.get(entity);
    }

    @Override
    public void writeSyncPacket(PacketByteBuf buf, ServerPlayerEntity recipient) {
        buf.writeDouble(wyldSap);
    }

    @Override
    public void applySyncPacket(PacketByteBuf buf) {
        wyldSap = buf.readDouble();
    }


    @Override
    public void readFromNbt(NbtCompound tag) {
        this.wyldSap = tag.getDouble("wyld_sap");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putDouble("wyld_sap", this.wyldSap);
    }

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(TINKERING_RECIPE, player -> new TinkeringRecipe(), RespawnCopyStrategy.ALWAYS_COPY);
    }
}
