package dev.vesper.AIUTD.neoforge;

//? neoforge {

/*import dev.vesper.AIUTD.AIUTD;
import dev.vesper.AIUTD.common.CommonClient;
import dev.vesper.AIUTD.config.Config;
import dev.vesper.AIUTD.config.EndUserConfig;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;


@Mod(AIUTD.MOD_ID)
@EventBusSubscriber // sample_content
public class NeoforgeEntrypoint {

    public NeoforgeEntrypoint() {
        AIUTD.init();
    }


    @EventBusSubscriber(modid = AIUTD.MOD_ID, value = Dist.CLIENT)
    public static class ClientEvents {
        @SubscribeEvent
        public static void onClientSetup(final FMLClientSetupEvent event) {
            AIUTD.LOG.info("Initializing {} Client", AIUTD.MOD_ID);
            // register command here
            Config.HANDLER.load();
            EndUserConfig.USERCONFIG.load();
            CommonClient.init();
            new ChatMessagesNeoForge().chatMessageInit();
        }
    }

}
*///?}