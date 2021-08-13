package malek.mod_science.entities.clank;

import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.NotNull;

public interface Clank {

    void read(@NotNull NbtCompound compound);

    void write(@NotNull NbtCompound compound);
}
