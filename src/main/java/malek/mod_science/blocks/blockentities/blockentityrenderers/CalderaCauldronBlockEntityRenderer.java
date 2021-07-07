package malek.mod_science.blocks.blockentities.blockentityrenderers;

import alexiil.mc.lib.attributes.fluid.amount.FluidAmount;
import alexiil.mc.lib.attributes.fluid.render.FluidRenderFace;
import alexiil.mc.lib.attributes.fluid.volume.FluidKeys;
import malek.mod_science.blocks.blockentities.CalderaCauldronBlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;

import java.util.ArrayList;
import java.util.List;

public class CalderaCauldronBlockEntityRenderer<T extends CalderaCauldronBlockEntity> implements BlockEntityRenderer<T> {
    @Override
    public void render(CalderaCauldronBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        float top = 1F;
        float north = 1F;
        float east = 0F;
        float bottom = 0F;
        float south = 0F;
        float west = 1F;
        System.out.println(entity.fluidInv.getInvFluid(0).fluidKey.spriteId);
        List<FluidRenderFace> fluidRenderFaceArrayList = new ArrayList<>();
        //fluidRenderFaceArrayList.add(FluidRenderFace.createFlatFace(0, 0, 0, 1, 1, 1, 1.0D, Direction.UP));
        FluidRenderFace.appendCuboid(0, 0, 0, 1, 1, 1, 1.0D, java.util.EnumSet.allOf(Direction.class), fluidRenderFaceArrayList);
        fluidRenderFaceArrayList.forEach((fluidRenderFace -> fluidRenderFace.light = light));
        //FluidKeys.WATER.withAmount(FluidAmount.BUCKET).render(fluidRenderFaceArrayList, vertexConsumers, matrices);
        entity.fluidInv.getInvFluid(0).render(fluidRenderFaceArrayList, vertexConsumers, matrices);
    }
    public CalderaCauldronBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {

    }

}
