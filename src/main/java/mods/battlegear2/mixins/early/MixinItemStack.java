package mods.battlegear2.mixins.early;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import mods.battlegear2.api.core.BattlegearUtils;

@Mixin(ItemStack.class)
public class MixinItemStack {

    @Redirect(
            method = "damageItem",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/EntityPlayer;destroyCurrentEquippedItem()V"))
    private void battlegear2$onItemDestroy(EntityPlayer player) {
        BattlegearUtils.onBowStackDepleted(player, (ItemStack) (Object) this);
    }

}
