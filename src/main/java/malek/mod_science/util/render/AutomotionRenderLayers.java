package malek.mod_science.util.render;

import alexiil.mc.lib.attributes.fluid.mixin.impl.RenderLayerAccessor;
import dev.architectury.utils.NbtType;
import net.minecraft.client.render.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    static {
//        NbtCompound tag = new NbtCompound();
//        ArrayList<String> strings = new ArrayList<>();
//        NbtList list = strings.stream().toList().stream().map(s -> {
//            NbtCompound mapping = new NbtCompound();
//            mapping.putString("string", s);
//            return mapping;
//        }).collect(Collectors.toCollection(NbtList::new));
//        tag.put("list", list);
//
//        NbtCompound tag = new NbtCompound();
//        NbtList list = tag.getList("list", NbtType.COMPOUND);
//        List<NbtCompound> strings = list.stream().filter(NbtCompound.class::isInstance).map(NbtCompound.class::cast).collect(Collectors.toList());

    }

}