package malek.mod_science.blocks.transfusionMatrix;

import alexiil.mc.lib.attributes.fluid.render.FluidRenderFace;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3f;

import java.util.ArrayList;
import java.util.List;

public class TranfusionMatrixBlockEntityRenderer<T extends TransfusionMatrixBlockEntity> implements BlockEntityRenderer<T> {
    @Override
    public void render(T entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        float height = 0;
        double offset = 1.0/16.0;

        height = (float) (entity.fluidInv.getInvFluid(0).amount().asInexactDouble() / TransfusionMatrixBlockEntity.CAPACITY.asInexactDouble());
        List<FluidRenderFace> fluidRenderFaceArrayList = new ArrayList<>();
        //fluidRenderFaceArrayList.add(FluidRenderFace.createFlatFace(0, 0, 0, 1, 1, 1, 1.0D, Direction.UP));e
        if(height != 0) {
            FluidRenderFace.appendCuboid(offset, offset * 2.0, offset, 1 - offset, height - ( 2*offset), 1 - offset, 1.0D, java.util.EnumSet.allOf(Direction.class), fluidRenderFaceArrayList);
            fluidRenderFaceArrayList.forEach((fluidRenderFace -> fluidRenderFace.light = light));
            //FluidKeys.WATER.withAmount(FluidAmount.BUCKET).render(fluidRenderFaceArrayList, vertexConsumers, matrices);
            entity.fluidInv.getInvFluid(0).render(fluidRenderFaceArrayList, vertexConsumers, matrices);
        }
        matrices.pop();
        matrices.push();
        // Calculate the current offset in the y value
        // Move the item
        matrices.translate(0.5, 0.35, 0.5);

        // Rotate the item
        int lightAbove = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getPos().up());
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((entity.getWorld().getTime() + tickDelta) * 4));
        MinecraftClient.getInstance().getItemRenderer().renderItem(entity.getStack(0), ModelTransformation.Mode.GROUND, lightAbove, overlay, matrices, vertexConsumers, 0);
        matrices.pop();
    }
    public TranfusionMatrixBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {

    }
}
