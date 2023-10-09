package mods.battlegear2.asm;

import mods.battlegear2.asm.loader.BattlegearLoadingPlugin;

public enum MethodMapping {

    ENTITYAICONTROLLEDBYPLAYER$UPDATETASK("e()V", "updateTask()V", 1),
    ENTITYOTHERPLAYERMP$SETCURRENTITEMORARMOR("c(ILadd;)V", "setCurrentItemOrArmor(ILnet/minecraft/item/ItemStack;)V",
            1),
    ENTITYPLAYER$SETCURRENTITEMORARMOR("c(ILadd;)V", "setCurrentItemOrArmor(ILnet/minecraft/item/ItemStack;)V", 1),
    ITEMINWORLDMANAGER$TRYUSEITEM("a(Lyz;Lahb;Ladd;)Z",
            "tryUseItem(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;)Z",
            2),
    MINECRAFT$RIGHTCLICKMOUSE("am()V", "func_147121_ag()V", 1),
    NETHANDLERPLAYCLIENT$HANDLESPAWNPLAYER("a(Lgb;)V",
            "handleSpawnPlayer(Lnet/minecraft/network/play/server/S0CPacketSpawnPlayer;)V", 2),
    NETHANDLERPLAYSERVER$PROCESSPLAYERBLOCKPLACEMENT("a(Ljo;)V",
            "processPlayerBlockPlacement(Lnet/minecraft/network/play/client/C08PacketPlayerBlockPlacement;)V", 2),
    PLAYERCONTROLLERMP$SENDUSEITEM("a(Lyz;Lahb;Ladd;)Z",
            "sendUseItem(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;)Z",
            2);

    private final String obfMapping;
    public final String clearMapping;
    public final int targetCount;

    MethodMapping(String obfMapping, String clearMapping, int targetCount) {
        this.obfMapping = obfMapping;
        this.clearMapping = clearMapping;
        this.targetCount = targetCount;
    }

    public String getMapping() {
        return BattlegearLoadingPlugin.isObf() ? this.obfMapping : this.clearMapping;
    }

}
