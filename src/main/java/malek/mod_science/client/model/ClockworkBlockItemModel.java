package malek.mod_science.client.model;

import malek.mod_science.ModScience;
import malek.mod_science.blocks.ModBlockItemAnimated;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ClockworkBlockItemModel extends AnimatedGeoModel<ModBlockItemAnimated> {
    @Override
    public Identifier getModelLocation(ModBlockItemAnimated object) {
        return new Identifier(ModScience.MOD_ID, "geo/clockworkblock.geo.json");
    }

    @Override
    public Identifier getTextureLocation(ModBlockItemAnimated object) {
        return new Identifier(ModScience.MOD_ID, "textures/block/clockworkblock.png");
    }

    @Override
    public Identifier getAnimationFileLocation(ModBlockItemAnimated animatable) {
        return new Identifier(ModScience.MOD_ID, "animations/clockworkblock.animation.json");
    }
}
