package malek.mod_science.client.renderer;

import malek.mod_science.client.model.ClockworkModel;
import malek.mod_science.items.ClockworkItem;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class ClockworkRenderer extends GeoItemRenderer<ClockworkItem> {
    public ClockworkRenderer() {
        super(new ClockworkModel());
    }
}
