package dev.vesper.AIUTD.neoforge;

//? neoforge {

import dev.vesper.AIUTD.AIUTD;
import dev.vesper.AIUTD.common.CommonClient;
//? !1.21.10{
import dev.vesper.AIUTD.common.fancymenu.FancyMenuIntegration;
//?}
import dev.vesper.AIUTD.config.Config;
import dev.vesper.AIUTD.config.EndUserConfig;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(AIUTD.MOD_ID)
public class NeoforgeEntrypoint {

    public NeoforgeEntrypoint() {
        AIUTD.init();
    }


    @EventBusSubscriber(modid = AIUTD.MOD_ID, value = Dist.CLIENT)
    public static class ClientEvents {
        @SubscribeEvent
        public static void onClientSetup(final FMLClientSetupEvent event) {
            AIUTD.LOG.info("Initializing {} Client", AIUTD.MOD_ID);
            ModLoadingContext.get().registerExtensionPoint(
                    IConfigScreenFactory.class,
                    () -> (client, parent) -> Config.config(parent)
            );
            Config.HANDLER.load();
            EndUserConfig.USERCONFIG.load();
            CommonClient.init();
            new ChatMessagesNeoForge().chatMessageInit();
            //? !1.21.10{
            if (AIUTD.isModLoaded("fancymenu")) {
                FancyMenuIntegration.init();
            }
            //?}
        }
    }

}
//?}