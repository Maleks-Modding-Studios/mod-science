package malek.mod_science.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import malek.mod_science.components.player.sap.normal_sap.NormalSap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.ClientChatListener;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.MessageType;
import net.minecraft.text.LiteralText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;

@Mixin(InGameHud.class)
abstract class InGameHudMixin extends DrawableHelper {

    public final Identifier CIRCLE = new Identifier("mod_science:textures/gui/normalsap.png");

    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    private int scaledHeight;

    @Shadow
    private int scaledWidth;

    @Shadow
    @Final
    private Map<MessageType, List<ClientChatListener>> listeners;

    @Shadow
    public abstract void render(MatrixStack matrices, float tickDelta);

    double sapWidth = 19.8;
    double sapHeight = 27.5;

    Integer sapAmount = 999;

    @Inject(method = "renderStatusBars", at = @At("TAIL"))
    public void addPetWheelToHUD(MatrixStack matrices, CallbackInfo ci) {

        String sapAmountString = sapAmount.toString();

            //Change image temporarily to custom texture
            RenderSystem.setShaderTexture(0, CIRCLE);

            //Draw wheel
            drawCustomTexture(matrices, (int) ((scaledWidth / 2) - (sapWidth / 2)), (int) (scaledHeight - 20 - sapHeight - ((sapHeight / 4) + (sapHeight / 8) - (sapHeight / 16))), 0, 0, (int)sapWidth, (int)sapHeight);
            this.client.getProfiler().swap("normalsap");

            this.client.textRenderer.draw(matrices, new LiteralText(sapAmountString), (scaledWidth / 2) - (this.client.textRenderer.getWidth(sapAmountString) / 2), (float) (scaledHeight - 20 - sapHeight - ((sapHeight / 4) + (sapHeight / 8) - (sapHeight / 16) - (((sapHeight / 3) * 2) - (this.client.textRenderer.fontHeight / 2)))), 0x000000);

        //Put back to normal texture
            RenderSystem.setShaderTexture(0, GUI_ICONS_TEXTURE);
        }

    public void drawCustomTexture(MatrixStack matrices, int x, int y, int u, int v, int width, int height) {
        drawTexture(matrices, x, y, 0, (float) u, (float) v, width, height, (int) sapHeight, (int) sapWidth);
    }
}