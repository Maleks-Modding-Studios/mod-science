package malek.mod_science.client;
import malek.mod_science.items.ore_magnet.ModScienceItemRegistrar;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.registry.Registry;

@Environment(EnvType.CLIENT)
public class UnderlainChildItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {
    private final ItemStack underlay;

    public UnderlainChildItemRenderer(Item underlay) {
        this.underlay = new ItemStack(underlay);
    }

    public UnderlainChildItemRenderer(ItemStack underlay) {
        this.underlay = underlay;
    }

    @Override
    public void render(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (!(stack.getItem() instanceof ModScienceItemRegistrar.ChildItem)) throw new UnsupportedOperationException("Can only use UnderlaidChildItemRenderer for ChildItems");
        ModScienceItemRegistrar.ChildItem childItem = (ModScienceItemRegistrar.ChildItem) stack.getItem();
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        //System.out.println("hello");
        MinecraftClient minecraft = MinecraftClient.getInstance();
        matrices.push();
        matrices.translate(0.5f, 0.5f, 0.5f);
        itemRenderer.renderItem(((ModScienceItemRegistrar.ChildItem) stack.getItem()).getOriginalItem().getDefaultStack(), ModelTransformation.Mode.NONE, light, overlay, matrices, vertexConsumers, 0);
        matrices.push();
        matrices.translate(-0.3f, 0, -0.3);
        matrices.scale(0.4f, 0.4f, 0.4f);
        if(stack.getTag() != null) {
            if(Registry.ITEM.get(Identifier.tryParse(stack.getTag().getString("block"))) instanceof BlockItem blockItem) {
                matrices.method_34425(new Matrix4f(new Quaternion(new Vec3f(0.5f, 0.5F, 0f), 90, true)));
                //        matrices.method_34425(new Matrix4f(new Quaternion(new Vec3f(0f, 1F, 0f), 90, true)));
                //        matrices.method_34425(new Matrix4f(new Quaternion(new Vec3f(1f, 0F, 0f), 90, true)));
                if (blockItem != null) {
                    minecraft.getBlockRenderManager().renderBlockAsEntity(blockItem.getBlock().getDefaultState(), matrices, vertexConsumers, light, overlay);
                }
            }
        }

        matrices.pop();
        matrices.pop();

    }
}
