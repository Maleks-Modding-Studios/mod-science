package malek.mod_science.client.renderer;

import malek.mod_science.blocks.clockwork.ClockworkBlockEntity;
import malek.mod_science.client.model.ClockworkBlockModel;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class ClockworkBlockRenderer extends GeoBlockRenderer<ClockworkBlockEntity> {
    public ClockworkBlockRenderer() {
        super(new ClockworkBlockModel());
    }

    @Override
    public RenderLayer getRenderType(ClockworkBlockEntity animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
    }
}