package malek.mod_science.util.general;

import malek.mod_science.ModScience;
import malek.mod_science.ModScienceInit;

import java.util.Set;

//This utility class allows for more easy checking if a mod is loaded, and editing behaviour based on that fact.
public class ModCompatibility {
    private boolean enabled = false;
    String modID;
    public ModCompatibility(String modId) {
        this.modID = modId;
        ModScienceInit.MODS.add(this);
    }
    public void enable() {
        enabled = true;
    }
    public boolean isEnabled() {return enabled;}
    public String getModID() {
        return modID;
    }
}
