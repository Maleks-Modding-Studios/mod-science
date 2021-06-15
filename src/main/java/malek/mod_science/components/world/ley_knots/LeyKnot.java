package malek.mod_science.components.world.ley_knots;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.world.WorldComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.world.WorldComponentInitializer;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import static malek.mod_science.ModScience.MOD_ID;
//TODO: Set up sync between client and server for this, look at madness component for example

/**
 * Ley Knots are positions in the world that hold sap and are some amount unstable, they generate ley lines by
 * playing connect the dots. They passivly regenerate sap.
 */
public class LeyKnot implements LeyKnotInterface {
    private int x;
    private int z;
    private double sap = 0.0;
    private double sapRegenAmount = 0.0;
    private double instability = 0.0;
    private boolean unravelled = false;



    @Override
    public int getX() {
        return x;
    }

    public void setFields(int x, int z, double sap, double sapRegenAmount, double instability, boolean unravelled) {
        setX(x);
        setZ(z);
        setSap(sap);
        setSapRegenAmount(sapRegenAmount);
        setInstability(instability);
        setUnravelled(unravelled);
    }

    @Override
    public int getZ() {
        return z;
    }

    @Override
    public double getSap() {
        return sap;
    }

    @Override
    public double getSapRegenerationAmount() {
        return sapRegenAmount;
    }

    @Override
    public double getInstability() {
        return instability;
    }

    @Override
    public boolean isUnravelled() {
        return unravelled;
    }

    @Override
    public void setUnravelled(boolean unravelled) {
        this.unravelled = unravelled;
    }

    @Override
    public void setInstability(double amount) {
        instability = amount;
    }

    @Override
    public void setSapRegenAmount(double amount) {
        sapRegenAmount = amount;
    }

    @Override
    public void setSap(double amount) {
        sap = amount;
    }

    @Override
    public void setZ(int z) {
        this.z = z;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }
//Here lies fallen code, all of which i typed by hand. Watch and mourn, then move on.
//    public static int getZ(World world) {
//        return get(world).getZ();
//    }
//
//    public static double getSap(World world) {
//        return get(world).getSap();
//    }
//
//    public static double getSapRegenAmount(World world) {
//        return get(world).getSapRegenerationAmount();
//    }
//
//    public static double getInstability(World world) {
//        return get(world).getInstability();
//    }
//
//    public static boolean isUnravelled(World world) {
//        return get(world).isUnravelled();
//    }
//
//    public static void setX(World world, int x) {
//        get(world).setX(x);
//    }
//
//    public static void setZ(World world, int z) {
//        get(world).setZ(z);
//    }
//
//    public static void setSap(World world, double amount) {
//        get(world).setSap(amount);
//    }
//
//    public static void setInstability(World world, double amount) {
//        get(world).setInstability(amount);
//    }
//
//    public static void setSapRegenAmount(World world, double amount) {
//        get(world).setSapRegenAmount(amount);
//    }
//
//    public static void addSap(World world, int amount) {
//        get(world).addSap(amount);
//    }
//
//    public static void addInstability(World world, int amount) {
//        get(world).addInstability(amount);
//    }
//
//    public static void removeSap(World world, int amount) {
//        get(world).removeSap(amount);
//    }
//
//    public static void removeInstability(World world, int amount) {
//        get(world).removeInstability(amount);
//    }
//
//    public static boolean isEmpty(World world) {
//        return get(world).isEmpty();
//    }




    public void readFromNbt(NbtCompound tag) {
        x = tag.getInt("x");
        z = tag.getInt("z");
        sap = tag.getDouble("sap");
        sapRegenAmount = tag.getDouble("sap_regen_amount");
        instability = tag.getDouble("instability");
        unravelled = tag.getBoolean("unravelled");
    }


    public NbtCompound writeToNbt(NbtCompound tag) {
        tag.putInt("x", x);
        tag.putInt("z", z);
        tag.putDouble("sap", sap);
        tag.putDouble("sap_regen_amount", sapRegenAmount);
        tag.putDouble("instability", instability);
        tag.putBoolean("unravelled", unravelled);
        return tag;
    }


}
