package mods.battlegear2.mixins.early;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mods.battlegear2.api.core.IInventoryPlayerBattle;

@Mixin(InventoryPlayer.class)
public abstract class MixinInventoryPlayerClient implements IInventoryPlayerBattle {

    @Shadow
    public int currentItem;

    @Inject(method = "func_146030_a", at = @At("HEAD"), cancellable = true)
    private void battlegear2$setCurrentItem(Item itemIn, int metadataIn, boolean isMetaSpecific,
            boolean forceInEmptySlots, CallbackInfo ci) {
        if (battlegear2$isBattlemode()) {
            ci.cancel();
        }
    }

    @Inject(method = "changeCurrentItem", at = @At("HEAD"), cancellable = true)
    private void battlegear2$changeCurrentItem(int direction, CallbackInfo ci) {
        if (battlegear2$isBattlemode()) {
            if (direction > 0) {
                direction = 1;
            } else if (direction != 0) {
                direction = -1;
            }
            // noinspection StatementWithEmptyBody
            for (currentItem -= direction; currentItem < OFFSET; currentItem += WEAPON_SETS) {}
            while (currentItem >= OFFSET + WEAPON_SETS) {
                currentItem -= WEAPON_SETS;
            }
            ci.cancel();
        }
    }

}
