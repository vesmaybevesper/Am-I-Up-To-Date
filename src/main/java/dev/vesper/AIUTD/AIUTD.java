package dev.vesper.AIUTD;

//? fabric {
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
        /*if (AIUTD.isModLoaded("fancymenu")) {
            FancyMenuIntegration.init();
        }*/
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
