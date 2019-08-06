package kaptainwutax.nexus;

import com.mojang.blaze3d.platform.GlStateManager;
import kaptainwutax.nexus.utility.Line;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class PathRenderer {

    public static MinecraftClient client = MinecraftClient.getInstance();
    public static List<Line> LINES = new ArrayList<Line>();

    public static void render() {
        GlStateManager.enableAlphaTest();

        GlStateManager.disableTexture();
        GlStateManager.disableBlend();

        for(Line line: LINES)drawLine(line, client.gameRenderer.getCamera().getPos());
        LINES.clear();

        GlStateManager.enableBlend();
        GlStateManager.enableTexture();
    }

    private static void drawLine(Line line, Vec3d camPos) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBufferBuilder();
        GlStateManager.lineWidth(1.0F);
        bufferBuilder.begin(3, VertexFormats.POSITION_COLOR);

        for(int i = 0; i < 2; i++) {
            bufferBuilder.vertex(
                    line.pos1.getX() - camPos.x,
                    line.pos1.getY() - camPos.y,
                    line.pos1.getZ() - camPos.z
            ).color(
                    line.color.getX(),
                    line.color.getY(),
                    line.color.getZ(),
                    line.color.getW()
            ).next();
        }

        for(int i = 0; i < 2; i++) {
            bufferBuilder.vertex(
                    line.pos2.getX() - camPos.x,
                    line.pos2.getY() - camPos.y,
                    line.pos2.getZ() - camPos.z
            ).color(
                    line.color.getX(),
                    line.color.getY(),
                    line.color.getZ(),
                    line.color.getW()
            ).next();
        }

        tessellator.draw();
    }

}
