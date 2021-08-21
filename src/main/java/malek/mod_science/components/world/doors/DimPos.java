package malek.mod_science.components.world.doors;

import malek.mod_science.ModScience;
import malek.mod_science.worlds.dimensions.TheRoomDimension;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Position;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public class DimPos extends BlockPos{
    public RegistryKey<World> dimension;
    public World getWorld() {
        return ModScience.getServer().getWorld(dimension);
    }
    public DimPos(int i, int j, int k) {
        super(i, j, k);
    }

    public DimPos(double d, double e, double f) {
        super(d, e, f);
    }

    public DimPos(Vec3d pos) {
        super(pos);
    }

    public DimPos(Position pos) {
        super(pos);
    }

    public DimPos(Vec3i pos) {
        super(pos);
    }
    public DimPos(Vec3i pos, RegistryKey<World> dimension) {
        super(pos);
        this.dimension = dimension;
    }
}
