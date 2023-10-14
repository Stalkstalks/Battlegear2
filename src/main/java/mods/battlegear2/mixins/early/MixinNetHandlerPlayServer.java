package mods.battlegear2.mixins.early;

import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.world.WorldServer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

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

    @Inject(
            method = "processPlayerBlockPlacement",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/entity/player/EntityPlayerMP;isChangingQuantityOnly:Z",
                    shift = At.Shift.AFTER,
                    ordinal = 1),
            locals = LocalCapture.CAPTURE_FAILSOFT,
            cancellable = true)
    private void battlegear2$fixNPE(C08PacketPlayerBlockPlacement packetIn, CallbackInfo ci, WorldServer worldserver,
            ItemStack itemstack, boolean flag, boolean placeResult, int i, int j, int k, int l, Slot slot) {
        if (slot == null) {
            ci.cancel();
        }
    }
}
