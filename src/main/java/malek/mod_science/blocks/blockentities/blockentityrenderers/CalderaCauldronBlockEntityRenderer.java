package malek.mod_science.blocks.blockentities.blockentityrenderers;

import alexiil.mc.lib.attributes.fluid.amount.FluidAmount;
import alexiil.mc.lib.attributes.fluid.render.FluidRenderFace;
import alexiil.mc.lib.attributes.fluid.volume.FluidKeys;
import malek.mod_science.blocks.CalderaCauldron;
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
        float height = 0;
        double offset = 1.0/16.0;

        height = (float) (entity.fluidInv.getInvFluid(0).amount().asInexactDouble() / CalderaCauldronBlockEntity.CAPACITY.asInexactDouble());
        List<FluidRenderFace> fluidRenderFaceArrayList = new ArrayList<>();
        //fluidRenderFaceArrayList.add(FluidRenderFace.createFlatFace(0, 0, 0, 1, 1, 1, 1.0D, Direction.UP));e
        if(height != 0) {
            FluidRenderFace.appendCuboid(offset, offset * 3.0, offset, 1 - offset, height - ( 3*offset), 1 - offset, 1.0D, java.util.EnumSet.allOf(Direction.class), fluidRenderFaceArrayList);
            fluidRenderFaceArrayList.forEach((fluidRenderFace -> fluidRenderFace.light = light));
            //FluidKeys.WATER.withAmount(FluidAmount.BUCKET).render(fluidRenderFaceArrayList, vertexConsumers, matrices);
            entity.fluidInv.getInvFluid(0).render(fluidRenderFaceArrayList, vertexConsumers, matrices);
        }
    }
    public CalderaCauldronBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {

    }

}
