package malek.mod_science.client.renderer;

import malek.mod_science.blocks.ModBlockItemAnimated;
import malek.mod_science.client.model.ClockworkBlockItemModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class ClockworkBlockItemRender extends GeoItemRenderer<ModBlockItemAnimated> {
    public ClockworkBlockItemRender(){
        super(new ClockworkBlockItemModel());
    }
}
