package mods.battlegear2.mixins.early;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mods.battlegear2.api.core.IOffhandRender;
import mods.battlegear2.client.utils.BattlegearRenderHelper;

@Mixin(ItemRenderer.class)
public class MixinItemRenderer implements IOffhandRender {

    @Shadow
    private Minecraft mc;
    @Unique
    private ItemStack battlegear2$offHandItemToRender;
    @Unique
    private int battlegear2$equippedItemOffhandSlot = -1;
    @Unique
    private float battlegear2$equippedOffHandProgress = 0F;
    @Unique
    private float battlegear2$prevEquippedOffHandProgress = 0F;

    @Inject(method = "updateEquippedItem", at = @At("RETURN"))
    private void battlegear2$updateEquippedItem(CallbackInfo ci) {
        BattlegearRenderHelper.updateEquippedItem((ItemRenderer) (Object) this, this.mc);
    }

    @Inject(method = "renderItemInFirstPerson", at = @At("RETURN"))
    private void battlegear2$renderItemInFirstPerson(float p_78440_1_, CallbackInfo ci) {
        BattlegearRenderHelper.renderItemInFirstPerson(p_78440_1_, this.mc, (ItemRenderer) (Object) this);
    }

    @Override
    public ItemStack battlegear2$getOffHandItemToRender() {
        return this.battlegear2$offHandItemToRender;
    }

    @Override
    public void battlegear2$setOffHandItemToRender(ItemStack item) {
        this.battlegear2$offHandItemToRender = item;
    }

    @Override
    public int battlegear2$getEquippedItemOffhandSlot() {
        return this.battlegear2$equippedItemOffhandSlot;
    }

    @Override
    public void battlegear2$serEquippedItemOffhandSlot(int slot) {
        this.battlegear2$equippedItemOffhandSlot = slot;
    }

    @Override
    public float battlegear2$getEquippedOffHandProgress() {
        return this.battlegear2$equippedOffHandProgress;
    }

    @Override
    public void battlegear2$setEquippedOffHandProgress(float progress) {
        this.battlegear2$equippedOffHandProgress = progress;
    }

    @Override
    public float battlegear2$getPrevEquippedOffHandProgress() {
        return this.battlegear2$prevEquippedOffHandProgress;
    }

    @Override
    public void battlegear2$setPrevEquippedOffHandProgress(float progress) {
        this.battlegear2$prevEquippedOffHandProgress = progress;
    }
}
