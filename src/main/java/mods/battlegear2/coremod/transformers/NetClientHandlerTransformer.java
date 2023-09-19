package mods.battlegear2.coremod.transformers;

import java.util.List;

import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import mods.battlegear2.api.core.BattlegearTranslator;

public final class NetClientHandlerTransformer extends TransformerBase {

    public NetClientHandlerTransformer() {
        super("net.minecraft.client.network.NetHandlerPlayClient");
    }

    private String entityOtherPlayerMPClassName;
    private String playerInventoryFieldName;

    private String netClientHandlerHandleNamedEntitySpawnMethodName;
    private String netClientHandlerHandleNamedEntitySpawnMethodDesc;

    @Override
    boolean processMethods(List<MethodNode> methods) {
        int found = 0;
        for (MethodNode method : methods) {
            if (method.name.equals(netClientHandlerHandleNamedEntitySpawnMethodName)
                    && method.desc.equals(netClientHandlerHandleNamedEntitySpawnMethodDesc)) {
                sendPatchLog("handleSpawnPlayer");
                replaceInventoryArrayAccess(method, entityOtherPlayerMPClassName, playerInventoryFieldName, 9, 14);
                found++;
            }
        }
        return found == 1;
    }

    @Override
    boolean processFields(List<FieldNode> fields) {
        return true;
    }

    @Override
    void setupMappings() {
        entityOtherPlayerMPClassName = BattlegearTranslator.getMapedClassName("client.entity.EntityOtherPlayerMP");
        playerInventoryFieldName = BattlegearTranslator.getMapedFieldName("field_71071_by", "inventory");
        netClientHandlerHandleNamedEntitySpawnMethodName = BattlegearTranslator
                .getMapedMethodName("func_147237_a", "handleSpawnPlayer");
        netClientHandlerHandleNamedEntitySpawnMethodDesc = "(Lnet/minecraft/network/play/server/S0CPacketSpawnPlayer;)V";
    }
}
