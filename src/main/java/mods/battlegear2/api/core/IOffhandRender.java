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

    ItemStack getOffHandItemToRender();

    void setOffHandItemToRender(ItemStack item);

    int getEquippedItemOffhandSlot();

    void serEquippedItemOffhandSlot(int slot);

    float getEquippedOffHandProgress();

    void setEquippedOffHandProgress(float progress);

    float getPrevEquippedOffHandProgress();

    void setPrevEquippedOffHandProgress(float progress);
}
