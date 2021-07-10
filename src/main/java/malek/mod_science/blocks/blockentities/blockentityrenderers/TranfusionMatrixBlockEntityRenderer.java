package malek.mod_science.blocks.blockentities.blockentityrenderers;

import com.flowpowered.math.vector.Vector3f;
import malek.mod_science.blocks.blockentities.TransfusionMatrixBlockEntity;
import malek.mod_science.client.ItemModelProvider;
import malek.mod_science.util.general.MixinUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;
import software.bernie.shadowed.eliotlash.mclib.math.functions.limit.Min;

public class TranfusionMatrixBlockEntityRenderer<T extends TransfusionMatrixBlockEntity> implements BlockEntityRenderer<T> {
    @Override
    public void render(T entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        // Calculate the current offset in the y value
        double offset = Math.sin((entity.getWorld().getTime() + tickDelta) / 8.0) / 4.0;
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
