package kaptainwutax.nexus.mixin;

import kaptainwutax.nexus.utility.Line;
import kaptainwutax.nexus.PathRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.client.util.math.Vector4f;
import net.minecraft.util.hit.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class MixinWorldRenderer {

	@Inject(at = @At("TAIL"), method = "drawHighlightedBlockOutline")
	public void drawHighlightedBlockOutline(Camera camera_1, HitResult hitResult_1, int int_1, CallbackInfo ci) {
		Line test = new Line();
		test.pos1 = new Vector3f(0, 100, 0);
		test.pos2 = new Vector3f(5, 106, 0);
		test.color = new Vector4f(1.0f, 0.5f, 0.3f, 1.0f);

		Line test1 = new Line();
		test1.pos1 = new Vector3f(8, 100, -3);
		test1.pos2 = new Vector3f(5, 106, 2);
		test1.color = new Vector4f(0.3f, 0.8f, 0.9f, 1.0f);

		PathRenderer.LINES.add(test);
		PathRenderer.LINES.add(test1);

		PathRenderer.render();
	}

}
