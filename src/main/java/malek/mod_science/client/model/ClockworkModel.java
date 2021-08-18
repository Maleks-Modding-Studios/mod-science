package malek.mod_science.client.model;

import malek.mod_science.ModScience;
import malek.mod_science.items.ClockworkItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ClockworkModel extends AnimatedGeoModel<ClockworkItem> {
    @Override
    public Identifier getModelLocation(ClockworkItem object) {
        return new Identifier(ModScience.MOD_ID, "geo/clockwork.geo.json");
    }

    @Override
    public Identifier getTextureLocation(ClockworkItem object) {
        return new Identifier(ModScience.MOD_ID, "textures/item/clockwork.png");
    }

    @Override
    public Identifier getAnimationFileLocation(ClockworkItem animatable) {
        return new Identifier(ModScience.MOD_ID, "animations/clockwork.animation.json");
    }
}