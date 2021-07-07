package malek.mod_science.util.render;

import alexiil.mc.lib.attributes.fluid.mixin.impl.RenderLayerAccessor;
import net.minecraft.client.render.*;

public abstract class AutomotionRenderLayers extends RenderLayer {
    public AutomotionRenderLayers(String name, VertexFormat vertexFormat, VertexFormat.DrawMode drawMode, int expectedBufferSize, boolean hasCrumbling, boolean translucent, Runnable startAction, Runnable endAction) {
        super(name, vertexFormat, drawMode, expectedBufferSize, hasCrumbling, translucent, startAction, endAction);
    }
    /*
    public static RenderLayer TRANSLUCENT = of("automotion:translucent", VertexFormats.POSITION_COLOR_LIGHT, VertexFormat.DrawMode.QUADS, 2097152, true, true, MultiPhaseParameters.builder().shader(new Shader(GameRenderer::getPositionColorTexShader)).lightmap(ENABLE_LIGHTMAP).transparency(TRANSLUCENT_TRANSPARENCY).build(true));
    public static RenderLayer TRANSLUCENT_UNLIT = of("automotion:translucent", VertexFormats.POSITION_COLOR, VertexFormat.DrawMode.QUADS, 262144, true, true, MultiPhaseParameters.builder().shader(new Shader(GameRenderer::getPositionColorTexShader)).transparency(RenderPhase.TRANSLUCENT_TRANSPARENCY).target(TRANSLUCENT_TARGET).build(true));
    public static RenderLayer TRANSLUCENT_TEXTURED = of("automotion:translucent_textured", VertexFormats.POSITION_TEXTURE, VertexFormat.DrawMode.QUADS, 262144, true, true, MultiPhaseParameters.builder().shader(new Shader(GameRenderer::getPositionColorTexShader)).transparency(RenderPhase.TRANSLUCENT_TRANSPARENCY).texture(MIPMAP_BLOCK_ATLAS_TEXTURE).target(TRANSLUCENT_TARGET).build(true));
    public static RenderLayer TRANSLUCENT_TEXTURED_COLORED = of("automotion:translucent_textured_colored", VertexFormats.POSITION_COLOR_TEXTURE, VertexFormat.DrawMode.QUADS, 262144, true, true, MultiPhaseParameters.builder().shader(new Shader(GameRenderer::getPositionColorTexShader)).transparency(RenderPhase.TRANSLUCENT_TRANSPARENCY).texture(MIPMAP_BLOCK_ATLAS_TEXTURE).target(TRANSLUCENT_TARGET).build(true));
    */
    /*
    public static AutomotionRenderLayers TRANSLUCENT_TEXTURED_COLORED = (RenderLayer) RenderLayer.of("automotion:translucent_textured_colored", VertexFormats.POSITION_COLOR_TEXTURE, VertexFormat.DrawMode.QUADS, 262144, MultiPhaseParameters.builder().shader(new Shader(GameRenderer::getPositionColorTexShader)).transparency(RenderPhase.TRANSLUCENT_TRANSPARENCY).texture(MIPMAP_BLOCK_ATLAS_TEXTURE).target(TRANSLUCENT_TARGET).build(true));
    public static RenderLayer RENDER_LAYER = RenderLayer.getSolid();
    public AutomotionRenderLayers(String name, VertexFormat vertexFormat, VertexFormat.DrawMode drawMode, int expectedBufferSize, boolean hasCrumbling, boolean translucent, Runnable startAction, Runnable endAction) {
        super(name, vertexFormat, drawMode, expectedBufferSize, hasCrumbling, translucent, startAction, endAction);
    }

     */



}