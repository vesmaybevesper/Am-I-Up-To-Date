package dev.vesper.AIUTD.fabric;

//? fabric {
import dev.vesper.AIUTD.AIUTD;
import dev.vesper.AIUTD.config.Config;
import dev.vesper.AIUTD.config.EndUserConfig;
import net.fabricmc.api.ClientModInitializer;

public class FabricClientEntrypoint implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        AIUTD.LOG.info("Initializing {} Client", AIUTD.MOD_ID);
        Config.HANDLER.load();
        EndUserConfig.HANDLER.load();
    }

}
//?}