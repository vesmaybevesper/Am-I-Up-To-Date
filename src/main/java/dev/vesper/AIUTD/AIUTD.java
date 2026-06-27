package dev.vesper.AIUTD;

//? fabric {
import net.fabricmc.loader.api.FabricLoader;
//?}
//? neoforge {
/*import net.neoforged.fml.ModList;
*///?}
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dev.vesper.AIUTD.config.Config;

public class AIUTD {

    public static final String MOD_ID = "aiutd";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_ID);
    public static boolean hasNotified = false;
    public static String modrinthApiLink;
    public static String changelogLink;

    public static void init() {
        LOG.info("Initializing {} on {}", MOD_ID, Platform.INSTANCE.loader());
        Config.HANDLER.load();
        if (!Config.changelogLink.contains("https://modrinth.com/modpack/")) {
            changelogLink = "https://modrinth.com/modpack/" + Config.changelogLink + "/changelog".trim();
        } else  {
            changelogLink = Config.changelogLink;
        }

        if (!Config.versionAPI.contains("https://api.modrinth.com/v2/project/")){
            modrinthApiLink = "https://api.modrinth.com/v2/project/" + Config.versionAPI + "/version?include_changelog=false".trim();
        } else if (!Config.versionAPI.contains("/version?include_changelog=false")) {
            modrinthApiLink = Config.versionAPI + "/version?include_changelog=false".trim();
        } else {
            modrinthApiLink = Config.versionAPI;
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
