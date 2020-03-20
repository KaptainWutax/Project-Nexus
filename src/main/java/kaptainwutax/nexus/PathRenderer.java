package kaptainwutax.nexus;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import io.netty.util.internal.ConcurrentSet;
import kaptainwutax.nexus.render.Renderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

import java.util.Set;

public class PathRenderer {

    public static MinecraftClient client = MinecraftClient.getInstance();
    public static Set<Renderer> RENDERERS = new ConcurrentSet<>();

    public static void render(MatrixStack matrixStack) {
        RenderSystem.pushMatrix();
        RenderSystem.multMatrix(matrixStack.peek().getModel());

        GlStateManager.disableTexture();
        GlStateManager.disableDepthTest();

        for(Renderer renderer: RENDERERS) {
            renderer.render();
        }

        RenderSystem.popMatrix();
    }

}
