package mods.battlegear2.api.core;

import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;

/**
 * Interface added to {@link ItemRenderer} to support offhand rendering Note that, they only provide access to added
 * fields for the offhand, NOT the fields for the mainhand
 *
 * @author GotoLink
 */
public interface IOffhandRender {

    ItemStack battlegear2$getOffHandItemToRender();

    void battlegear2$setOffHandItemToRender(ItemStack item);

    int battlegear2$getEquippedItemOffhandSlot();

    void battlegear2$serEquippedItemOffhandSlot(int slot);

    float battlegear2$getEquippedOffHandProgress();

    void battlegear2$setEquippedOffHandProgress(float progress);

    float battlegear2$getPrevEquippedOffHandProgress();

    void battlegear2$setPrevEquippedOffHandProgress(float progress);
}
