package mods.battlegear2.coremod.transformers;

import java.util.List;

import org.apache.logging.log4j.Level;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import mods.battlegear2.api.core.BattlegearTranslator;

public final class EntityPlayerTransformer extends TransformerBase {

    public EntityPlayerTransformer() {
        super("net.minecraft.entity.player.EntityPlayer");
    }

    private String entityPlayerClassName;
    private String playerInventoryFieldName;
    private String setCurrentItemArmourMethodName;
    private String setCurrentItemArmourMethodDesc;

    @Override
    boolean processFields(List<FieldNode> fields) {
        return true;
    }

    @Override
    boolean processMethods(List<MethodNode> methods) {
        int found = 0;
        for (MethodNode mn : methods) {
            if (mn.name.equals(setCurrentItemArmourMethodName) && mn.desc.equals(setCurrentItemArmourMethodDesc)) {
                sendPatchLog("setCurrentItemOrArmor");
                replaceInventoryArrayAccess(
                        mn,
                        entityPlayerClassName,
                        playerInventoryFieldName,
                        mn.maxStack,
                        mn.maxLocals);
                found++;
            }
        }
        logger.log(Level.INFO, "\tCreating new methods in EntityPlayer");
        return found == 1;
    }

    @Override
    void setupMappings() {
        entityPlayerClassName = BattlegearTranslator.getMapedClassName("entity.player.EntityPlayer");
        String itemStackClassName = BattlegearTranslator.getMapedClassName("item.ItemStack");
        playerInventoryFieldName = BattlegearTranslator.getMapedFieldName("field_71071_by", "inventory");
        setCurrentItemArmourMethodName = BattlegearTranslator
                .getMapedMethodName("func_70062_b", "setCurrentItemOrArmor");
        setCurrentItemArmourMethodDesc = "(IL" + itemStackClassName + ";)V";
    }

}
