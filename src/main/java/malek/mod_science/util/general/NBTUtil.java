package malek.mod_science.util.general;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

import java.util.ArrayList;
import java.util.List;

public class NBTUtil {
    public static List<Integer> writeBlockPosToList(BlockPos pos){
        ArrayList<Integer> e = new ArrayList<>();
        e.add(pos.getX());
        e.add(pos.getY());
        e.add(pos.getZ());
        return e.stream().toList();

    }
    public static BlockPos writeBlockPosFromList(int[] list){

        return new BlockPos(list[0], list[1], list[2]);
    }
}
