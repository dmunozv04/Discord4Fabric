package me.reimnop.d4f.mixin;

import me.reimnop.d4f.events.PlayerDeathCallback;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {
    @Inject(method = "onDeath",
            at = @At(
                target = "Lnet/minecraft/server/network/ServerPlayNetworkHandler;sendPacket(Lnet/minecraft/network/Packet;Lnet/minecraft/network/PacketCallbacks;)V",
                value = "INVOKE",
                ordinal = 0),
            locals = LocalCapture.CAPTURE_FAILSOFT
    )
    private void onDeath(DamageSource damageSource, CallbackInfo ci, boolean bl, Text text){
        ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) (Object) this;
        PlayerDeathCallback.EVENT.invoker().onPlayerDeath(serverPlayerEntity, damageSource, text);
    }
}
