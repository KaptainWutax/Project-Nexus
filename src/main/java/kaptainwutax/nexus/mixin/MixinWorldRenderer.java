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
		PathRenderer.render();
	}

}
