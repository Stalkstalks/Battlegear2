package mods.battlegear2.mixins.early;

import net.minecraft.client.network.NetHandlerPlayClient;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import mods.battlegear2.api.core.InventoryPlayerBattle;

@Mixin(NetHandlerPlayClient.class)
public class MixinNetHandlerPlayClient {

    @ModifyExpressionValue(
            method = "handleHeldItemChange",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/network/play/server/S09PacketHeldItemChange;func_149385_c()I",
                    ordinal = 1))
    private int battlegear2$isValidIventorySlot(int original) {
        // return a valid int e.g. between 0 and < 9
        return InventoryPlayerBattle.isValidSwitch(original) ? 0 : -1;
    }

}
