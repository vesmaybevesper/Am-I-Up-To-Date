package dev.vesper.AIUTD;

//? fabric {
import dev.vesper.AIUTD.common.MigrationTool;
import net.fabricmc.loader.api.FabricLoader;
//?}
//? neoforge {
/*import net.neoforged.fml.ModList;
*///?}
import dev.vesper.AIUTD.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AIUTD {

    public static final String MOD_ID = "aiutd";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_ID);

    public static void init() {
        LOG.info("Initializing {} on {}", MOD_ID, Platform.INSTANCE.loader());
        // Make sure all config urls get updated, this will go away after a while, it's just to make sure as many people as possible get it
        MigrationTool.copy();
        Config.HANDLER.load();
        if (!Config.versionAPI.contains("?include_changelog=false")){
            LOG.warn("?include_changelog=false not detected in version API string, adding");
            Config.versionAPI = Config.versionAPI + "?include_changelog=false";
            Config.HANDLER.save();
        }
    }

    public static boolean isModLoaded(String modId){
        //? fabric {
        return FabricLoader.getInstance().isModLoaded(modId);
        //?}
        //? neoforge {
        /*return ModList.get().isLoaded(modId);
        *///?}
    }

}
