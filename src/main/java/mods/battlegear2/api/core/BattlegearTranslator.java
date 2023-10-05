package mods.battlegear2.api.core;

import mods.battlegear2.asm.loader.BattlegearLoadingPlugin;

/**
 * Core Translator for Battlegear Coremod and Reflection usage Allows to run Battlegear in both dev and "srg" (post-
 * FMLDeobfuscatingRemapper) environments
 */
public class BattlegearTranslator {// TODO delete

    // Setting this to true will enable the output of all edited classes as .class files
    public static boolean debug = false;

    @Deprecated
    public static String getMapedFieldName(String className, String fieldName, String devName) {
        return getMapedFieldName(fieldName, devName);
    }

    public static String getMapedFieldName(String fieldName, String devName) {
        return BattlegearLoadingPlugin.isObf() ? fieldName : devName;
    }

    public static String getMapedClassName(String className) {
        return "net/minecraft/" + className.replace(".", "/");
    }

    @Deprecated
    public static String getMapedMethodName(String className, String methodName, String devName) {
        return getMapedMethodName(methodName, devName);
    }

    public static String getMapedMethodName(String methodName, String devName) {
        return BattlegearLoadingPlugin.isObf() ? methodName : devName;
    }

    @Deprecated
    public static String getMapedMethodDesc(String className, String methodName, String devDesc) {
        return devDesc;
    }
}
