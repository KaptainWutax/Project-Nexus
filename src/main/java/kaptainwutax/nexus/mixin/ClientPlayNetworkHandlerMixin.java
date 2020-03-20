package kaptainwutax.nexus.mixin;

import kaptainwutax.nexus.Nexus;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.packet.s2c.play.ChunkDataS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin {

    @Shadow private ClientWorld world;

    @Inject(method = "onChunkData", at = @At("RETURN"))
    private void onChunkData(ChunkDataS2CPacket packet, CallbackInfo ci) {
        Nexus.getInstance().world.onChunkData(this.world, packet);
    }

}
