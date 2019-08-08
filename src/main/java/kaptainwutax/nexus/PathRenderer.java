package kaptainwutax.nexus;

import com.mojang.blaze3d.platform.GlStateManager;
import io.netty.util.internal.ConcurrentSet;
import kaptainwutax.nexus.utility.Line;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.math.Vec3d;

public class PathRenderer {

    public static MinecraftClient client = MinecraftClient.getInstance();
    public static ConcurrentSet<Line> LINES = new ConcurrentSet<Line>();

    public static void render() {
        GlStateManager.enableAlphaTest();

        GlStateManager.disableTexture();
        GlStateManager.disableBlend();

        for(Line line: LINES)drawLine(line, client.gameRenderer.getCamera().getPos());
        //LINES.clear();

        GlStateManager.enableBlend();
        GlStateManager.enableTexture();
    }

    private static void drawLine(Line line, Vec3d camPos) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBufferBuilder();
        GlStateManager.lineWidth(2.0F);
        bufferBuilder.begin(3, VertexFormats.POSITION_COLOR);

        putVertices(bufferBuilder, camPos, line, line.pos1);
        putVertices(bufferBuilder, camPos, line, line.pos2);

        tessellator.draw();

        double size = 0.1d;

        WorldRenderer.drawBoxOutline(
                line.pos1.getX() - size - camPos.x,
                line.pos1.getY() - size - camPos.y,
                line.pos1.getZ() - size - camPos.z,
                line.pos1.getX() + size - camPos.x,
                line.pos1.getY() + size - camPos.y,
                line.pos1.getZ() + size - camPos.z,
                line.color.getX(), line.color.getY(), line.color.getZ(), line.color.getW()
        );
    }

    public static void putVertices(BufferBuilder bufferBuilder, Vec3d camPos, Line line, Vector3f pos) {
        for(int i = 0; i < 2; i++) {
            bufferBuilder.vertex(
                    pos.getX() - camPos.x,
                    pos.getY() - camPos.y,
                    pos.getZ() - camPos.z
            ).color(
                    line.color.getX(),
                    line.color.getY(),
                    line.color.getZ(),
                    line.color.getW()
            ).next();
        }
    }

}
