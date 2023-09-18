package mods.battlegear2.coremod.transformers;

import java.util.Iterator;
import java.util.List;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import mods.battlegear2.api.core.BattlegearTranslator;

public final class NetServerHandlerTransformer extends TransformerBase {

    public NetServerHandlerTransformer() {
        super("net.minecraft.network.NetHandlerPlayServer");
        setDebug(true);
    }

    private String entityPlayerMPClassName;
    private String entityPlayerClassName;
    private String netServiceHandelerClassName;
    private String inventoryPlayerClassName;
    private String itemStackClassName;

    private String playerInventoryFieldName;
    private String netServiceHandelerPlayerField;

    private String handlePlaceMethodName;
    private String handlePlaceMethodDesc;
    private String inventoryGetCurrentMethodName;
    private String inventoryGetCurrentMethodDesc;
    private String itemStackCopyStackMethodName;
    private String itemStackCopyStackMethodDesc;

    @Override
    boolean processMethods(List<MethodNode> methods) {
        int found = 0;
        for (MethodNode mn : methods) {
            if (mn.name.equals(handlePlaceMethodName) && mn.desc.equals(handlePlaceMethodDesc)) {
                if (processPlaceMethod(mn)) found++;
            }
        }
        return found == 1;
    }

    private boolean processPlaceMethod(MethodNode mn) {
        sendPatchLog("processPlayerBlockPlacement");
        InsnList newList = new InsnList();
        Iterator<AbstractInsnNode> it = mn.instructions.iterator();

        int fieldCount = 0;
        while (it.hasNext()) {
            AbstractInsnNode nextNode = it.next();

            if (nextNode instanceof FieldInsnNode && ((FieldInsnNode) nextNode).owner.equals(entityPlayerMPClassName)
                    && ((FieldInsnNode) nextNode).name.equals(playerInventoryFieldName)) {
                fieldCount++; // count number of playerEntity.inventory use

                if (fieldCount == 3) {

                    while (it.hasNext() && nextNode.getOpcode() != ACONST_NULL) { // visit till pushing null onto stack
                        nextNode = it.next();
                    }

                    newList.add(nextNode);
                    newList.add(
                            new MethodInsnNode(
                                    INVOKESTATIC,
                                    UTILITY_CLASS,
                                    "setPlayerCurrentItem",
                                    "(L" + entityPlayerClassName + ";L" + itemStackClassName + ";)V"));
                    it.next(); // BattlegearUtils.setPlayerCurrentItem(playerEntity, null);

                } else if (fieldCount == 4) {

                    while (it.hasNext() && nextNode.getOpcode() != AASTORE) { // visit till storing into array
                        nextNode = it.next();
                    }

                    // BattlegearUtils.setPlayerCurrentItem(playerEntity,
                    // ItemStack.copyItemStack(this.playerEntity.inventory.getCurrentItem().copy()));
                    newList.add(new VarInsnNode(ALOAD, 0));
                    newList.add(
                            new FieldInsnNode(
                                    GETFIELD,
                                    netServiceHandelerClassName,
                                    netServiceHandelerPlayerField,
                                    "L" + entityPlayerMPClassName + ";"));
                    newList.add(
                            new FieldInsnNode(
                                    GETFIELD,
                                    entityPlayerMPClassName,
                                    playerInventoryFieldName,
                                    "L" + inventoryPlayerClassName + ";"));
                    newList.add(
                            new MethodInsnNode(
                                    INVOKEVIRTUAL,
                                    inventoryPlayerClassName,
                                    inventoryGetCurrentMethodName,
                                    inventoryGetCurrentMethodDesc));
                    newList.add(
                            new MethodInsnNode(
                                    INVOKESTATIC,
                                    itemStackClassName,
                                    itemStackCopyStackMethodName,
                                    itemStackCopyStackMethodDesc));
                    newList.add(
                            new MethodInsnNode(
                                    INVOKESTATIC,
                                    UTILITY_CLASS,
                                    "setPlayerCurrentItem",
                                    "(L" + entityPlayerClassName + ";L" + itemStackClassName + ";)V"));
                } else {
                    newList.add(nextNode);
                }
            } else {
                newList.add(nextNode);
            }
        }

        mn.instructions = newList;
        return fieldCount > 4;
    }

    @Override
    boolean processFields(List<FieldNode> fields) {
        return true;
    }

    @Override
    void setupMappings() {
        netServiceHandelerClassName = BattlegearTranslator.getMapedClassName("network.NetHandlerPlayServer");
        entityPlayerMPClassName = BattlegearTranslator.getMapedClassName("entity.player.EntityPlayerMP");
        inventoryPlayerClassName = BattlegearTranslator.getMapedClassName("entity.player.InventoryPlayer");
        itemStackClassName = BattlegearTranslator.getMapedClassName("item.ItemStack");
        entityPlayerClassName = BattlegearTranslator.getMapedClassName("entity.player.EntityPlayer");

        playerInventoryFieldName = BattlegearTranslator.getMapedFieldName("field_71071_by", "inventory");
        netServiceHandelerPlayerField = BattlegearTranslator.getMapedFieldName("field_147369_b", "playerEntity");

        handlePlaceMethodName = BattlegearTranslator.getMapedMethodName("func_147346_a", "processPlayerBlockPlacement");
        handlePlaceMethodDesc = "(Lnet/minecraft/network/play/client/C08PacketPlayerBlockPlacement;)V";

        inventoryGetCurrentMethodName = BattlegearTranslator.getMapedMethodName("func_70448_g", "getCurrentItem");
        inventoryGetCurrentMethodDesc = "()L" + itemStackClassName + ";";

        itemStackCopyStackMethodName = BattlegearTranslator.getMapedMethodName("func_77944_b", "copyItemStack");
        itemStackCopyStackMethodDesc = "(L" + itemStackClassName + ";)L" + itemStackClassName + ";";
    }
}
