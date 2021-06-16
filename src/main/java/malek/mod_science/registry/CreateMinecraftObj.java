package malek.mod_science.registry;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import java.util.function.Supplier;


public class CreateMinecraftObj<T> extends Thread {
    private T minecraftObject;
    Supplier<T> createObjectFunction;
    public CreateMinecraftObj(Supplier<T> createObjectFunction) {
        this.createObjectFunction = createObjectFunction;
    }
    @Override
    public void run() {
        minecraftObject = createObjectFunction.get();
    }
    public T get() {
        if(minecraftObject != null) {
            return minecraftObject;
        }
        else
        {
            LogManager.getLogger().log(Level.ERROR, "MINECRAFT OBJECT WAS NULL");
            return null;
        }
    }

}
