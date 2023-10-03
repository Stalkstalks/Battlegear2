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

    public static File debugOutputLocation;

    @Override
    public String[] getASMTransformerClass() {
        return new String[] { "mods.battlegear2.coremod.transformers.EntityAIControlledByPlayerTransformer",
                "mods.battlegear2.coremod.transformers.EntityOtherPlayerMPTransformer",
                "mods.battlegear2.coremod.transformers.EntityPlayerTransformer",
                "mods.battlegear2.coremod.transformers.ItemInWorldTransformer",
                "mods.battlegear2.coremod.transformers.MinecraftTransformer",
                "mods.battlegear2.coremod.transformers.NetClientHandlerTransformer",
                "mods.battlegear2.coremod.transformers.NetServerHandlerTransformer",
                "mods.battlegear2.coremod.transformers.PlayerControllerMPTransformer" };
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

    @Override
    public String getModContainerClass() {
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
        mixins.add("MixinInventoryPlayer");
        mixins.add("MixinNetHandlerPlayServer");
        if (FMLLaunchHandler.side().isClient()) {
            mixins.add("MixinEntityOtherPlayerMP");
            mixins.add("MixinInventoryPlayerClient");
            mixins.add("MixinItemRenderer");
            mixins.add("MixinModelBiped");
            mixins.add("MixinNetHandlerPlayClient");
        }
        return mixins;
    }

}
