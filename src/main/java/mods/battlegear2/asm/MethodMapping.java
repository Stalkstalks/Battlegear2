package mods.battlegear2.asm;

import mods.battlegear2.asm.loader.BattlegearLoadingPlugin;

public enum MethodMapping {

    ENTITYAICONTROLLEDBYPLAYER$UPDATETASK("func_75246_d", "updateTask", 1),
    ENTITYOTHERPLAYERMP$SETCURRENTITEMORARMOR("func_70062_b", "setCurrentItemOrArmor", 1),
    ENTITYPLAYER$SETCURRENTITEMORARMOR("func_70062_b", "setCurrentItemOrArmor", 1),
    ITEMINWORLDMANAGER$TRYUSEITEM("func_73085_a", "tryUseItem", 2),
    MINECRAFT$RIGHTCLICKMOUSE("func_147121_ag", "func_147121_ag", 1),
    NETHANDLERPLAYCLIENT$HANDLESPAWNPLAYER("func_147237_a", "handleSpawnPlayer", 2),
    NETHANDLERPLAYSERVER$PROCESSPLAYERBLOCKPLACEMENT("func_147346_a", "processPlayerBlockPlacement", 2),
    PLAYERCONTROLLERMP$SENDUSEITEM("func_78769_a", "sendUseItem", 2);

    private final String srgName;
    public final String clearName;
    public final int targetCount;

    MethodMapping(String srgName, String clearName, int targetCount) {
        this.srgName = srgName;
        this.clearName = clearName;
        this.targetCount = targetCount;
    }

    public String getName() {
        return BattlegearLoadingPlugin.isObf() ? this.srgName : this.clearName;
    }

}
