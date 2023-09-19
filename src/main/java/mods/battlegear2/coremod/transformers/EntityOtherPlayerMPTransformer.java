package mods.battlegear2.coremod.transformers;

import java.util.List;

import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import mods.battlegear2.api.core.BattlegearTranslator;

public final class EntityOtherPlayerMPTransformer extends TransformerBase {

    public EntityOtherPlayerMPTransformer() {
        super("net.minecraft.client.entity.EntityOtherPlayerMP");
    }

    private String entityOtherPlayerMPClassName;
    private String playerInventoryFieldName;
    private String setCurrentItemMethodName;
    private String setCurrentItemMethodDesc;

    @Override
    boolean processMethods(List<MethodNode> methods) {
        int found = 0;
        for (MethodNode mn : methods) {
            if (mn.name.equals(setCurrentItemMethodName) && mn.desc.equals(setCurrentItemMethodDesc)) {
                sendPatchLog("setCurrentItem");
                replaceInventoryArrayAccess(mn, entityOtherPlayerMPClassName, playerInventoryFieldName, 4, 3, 3);
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
        String itemStackClassName = BattlegearTranslator.getMapedClassName("item.ItemStack");
        entityOtherPlayerMPClassName = BattlegearTranslator.getMapedClassName("client.entity.EntityOtherPlayerMP");
        playerInventoryFieldName = BattlegearTranslator.getMapedFieldName("field_71071_by", "inventory");
        setCurrentItemMethodName = BattlegearTranslator.getMapedMethodName("func_70062_b", "setCurrentItemOrArmor");
        setCurrentItemMethodDesc = "(IL" + itemStackClassName + ";)V";
    }
}
