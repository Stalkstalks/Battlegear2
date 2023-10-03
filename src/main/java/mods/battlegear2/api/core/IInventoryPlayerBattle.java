package mods.battlegear2.api.core;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

public interface IInventoryPlayerBattle {

    // The offsets used
    int ARMOR_OFFSET = 100;
    int OFFSET = 150;
    int WEAPON_SETS = 3;
    int EXTRA_ITEMS = WEAPON_SETS * 2;
    int EXTRA_INV_SIZE = EXTRA_ITEMS + 6 + 6;

    boolean battlegear2$isBattlemode();

    ItemStack battlegear2$getCurrentOffhandWeapon();

    void battlegear2$setInventorySlotContents(int index, ItemStack stack, boolean changed);

    boolean battlegear2$isDirty();

    void battlegear2$setDirty(boolean dirty);

    /**
     * Patch used for "set current slot" vanilla packets
     *
     * @param id the value to test for currentItem setting
     * @return true if it is possible for currentItem to be set with this value
     */
    static boolean isValidSwitch(int id) {
        return (id >= 0 && id < InventoryPlayer.getHotbarSize()) || (id >= OFFSET && id < OFFSET + EXTRA_ITEMS);
    }

}
