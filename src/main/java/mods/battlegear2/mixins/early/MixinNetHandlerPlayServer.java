package mods.battlegear2.mixins.early;

import net.minecraft.network.NetHandlerPlayServer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import mods.battlegear2.api.core.IInventoryPlayerBattle;

@Mixin(NetHandlerPlayServer.class)
public class MixinNetHandlerPlayServer {

    @ModifyExpressionValue(
            method = "processHeldItemChange",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/network/play/client/C09PacketHeldItemChange;func_149614_c()I",
                    ordinal = 1))
    private int battlegear2$isValidIventorySlot(int original) {
        // return a valid int e.g. between 0 and < 9
        return IInventoryPlayerBattle.isValidSwitch(original) ? 0 : -1;
    }

}
