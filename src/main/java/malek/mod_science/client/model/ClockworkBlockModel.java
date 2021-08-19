package malek.mod_science.client.model;


import malek.mod_science.ModScience;
import malek.mod_science.blocks.clockwork.ClockworkBlockEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ClockworkBlockModel extends AnimatedGeoModel<ClockworkBlockEntity> {
    @Override
    public Identifier getAnimationFileLocation(ClockworkBlockEntity entity) {
        return new Identifier(ModScience.MOD_ID, "animations/clockworkblock.animation.json");
    }

    @Override
    public Identifier getModelLocation(ClockworkBlockEntity animatable) {
        return new Identifier(ModScience.MOD_ID, "geo/clockworkblock.geo.json");
    }

    @Override
    public Identifier getTextureLocation(ClockworkBlockEntity entity) {
        return new Identifier(ModScience.MOD_ID, "textures/block/clockworkblock.png");
    }
}