package kaptainwutax.nexus.mixin;

import kaptainwutax.nexus.Nexus;
import kaptainwutax.nexus.PathRenderer;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientWorld.class)
public class MixinClientWorld {

	@Inject(method = "disconnect", at = @At("HEAD"))
	private void disconnect(CallbackInfo ci) {
		Nexus.getInstance().world.clear();
		Nexus.getInstance().pathFinder.reset();
		PathRenderer.RENDERERS.clear();
	}

}
