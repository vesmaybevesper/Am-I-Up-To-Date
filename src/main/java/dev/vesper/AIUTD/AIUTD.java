package dev.vesper.AIUTD;

//? fabric {
import net.fabricmc.loader.api.FabricLoader;
//?}
//? neoforge {
/*import net.neoforged.fml.ModList;
*///?}
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dev.vesper.AIUTD.common.Util;

public class AIUTD {

    public static final String MOD_ID = "aiutd";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_ID);
    public static boolean hasNotified = false;
    public static String modrinthId;

    public static void init() {
        LOG.info("Initializing {} on {}", MOD_ID, Platform.INSTANCE.loader());
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
