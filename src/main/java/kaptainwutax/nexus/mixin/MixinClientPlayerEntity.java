package kaptainwutax.nexus.mixin;

import kaptainwutax.nexus.Nexus;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class MixinClientPlayerEntity {

    //@Inject(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/input/Input;tick(Z)V", shift = At.Shift.BEFORE))
    //public void tickMovement(CallbackInfo ci) {
    //    Nexus.getInstance().tick();
    //}

    @Inject(method = "tick", at = @At("HEAD"))
    public void tickMovement(CallbackInfo ci) {
        Nexus.getInstance().tick();
    }


}
