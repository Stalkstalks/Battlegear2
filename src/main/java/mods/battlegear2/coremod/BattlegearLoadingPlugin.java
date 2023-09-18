package mods.battlegear2.coremod;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.gtnewhorizon.gtnhmixins.IEarlyMixinLoader;

import cpw.mods.fml.relauncher.FMLLaunchHandler;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.Name;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.SortingIndex;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;
import mods.battlegear2.api.core.BattlegearTranslator;

@TransformerExclusions({ "mods.battlegear2.coremod" })
@Name("Mine and Blade: Battlegear2")
@SortingIndex(1500)
@MCVersion("1.7.10")
public final class BattlegearLoadingPlugin implements IEarlyMixinLoader, IFMLLoadingPlugin {

    public static final String EntityPlayerTransformer = "mods.battlegear2.coremod.transformers.EntityPlayerTransformer";
    public static final String ModelBipedTransformer = "mods.battlegear2.coremod.transformers.ModelBipedTransformer";
    public static final String NetClientHandlerTransformer = "mods.battlegear2.coremod.transformers.NetClientHandlerTransformer";
    public static final String NetServerHandlerTransformer = "mods.battlegear2.coremod.transformers.NetServerHandlerTransformer";
    public static final String PlayerControllerMPTransformer = "mods.battlegear2.coremod.transformers.PlayerControllerMPTransformer";
    public static final String ItemRendererTransformer = "mods.battlegear2.coremod.transformers.ItemRendererTransformer";
    public static final String MinecraftTransformer = "mods.battlegear2.coremod.transformers.MinecraftTransformer";
    public static final String ItemInWorldTransformer = "mods.battlegear2.coremod.transformers.ItemInWorldTransformer";
    public static final String EntityAIControlledTransformer = "mods.battlegear2.coremod.transformers.EntityAIControlledByPlayerTransformer";
    public static final String EntityOtherPlayerMPTransformer = "mods.battlegear2.coremod.transformers.EntityOtherPlayerMPTransformer";
    public static File debugOutputLocation;

    public static final String[] transformers = new String[] { EntityPlayerTransformer, ModelBipedTransformer,
            NetClientHandlerTransformer, NetServerHandlerTransformer, PlayerControllerMPTransformer,
            ItemRendererTransformer, MinecraftTransformer, ItemInWorldTransformer, EntityAIControlledTransformer,
            EntityOtherPlayerMPTransformer, };

    @Override
    public String[] getASMTransformerClass() {
        return transformers;
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

    @Override
    public String getModContainerClass() {
        // return "mods.battlegear2.coremod.BattlegearCoremodContainer";
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
        debugOutputLocation = new File(data.get("mcLocation").toString(), "bg edited classes");
        BattlegearTranslator.obfuscatedEnv = (boolean) data.get("runtimeDeobfuscationEnabled");
    }

    @Override
    public String getMixinConfig() {
        return "mixins.battlegear2.early.json";
    }

    @Override
    public List<String> getMixins(Set<String> loadedCoreMods) {
        final List<String> mixins = new ArrayList<>();
        mixins.add("MixinEntityPlayer");
        mixins.add("MixinItemStack");
        mixins.add("MixinNetHandlerPlayServer");
        if (FMLLaunchHandler.side().isClient()) {}
        return mixins;
    }

}
