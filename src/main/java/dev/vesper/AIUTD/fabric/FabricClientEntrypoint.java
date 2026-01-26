package dev.vesper.AIUTD.fabric;

//? fabric {
import dev.vesper.AIUTD.AIUTD;
import dev.vesper.AIUTD.common.CommonClient;
import dev.vesper.AIUTD.config.Config;
import dev.vesper.AIUTD.config.EndUserConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class FabricClientEntrypoint implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        AIUTD.LOG.info("Initializing {} Client", AIUTD.MOD_ID);

        ClientCommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("shouldIgnore").executes(context -> {
                context.getSource().sendFeedback(Component.translatable("aiutd.msgsIgnored"));
                EndUserConfig.USERCONFIG.load();
                EndUserConfig.shouldIgnore = Boolean.TRUE;
                EndUserConfig.USERCONFIG.save();
                EndUserConfig.USERCONFIG.load();
                return 1;
            }));
        }));
        Config.HANDLER.load();
        EndUserConfig.USERCONFIG.load();
        CommonClient.init();
        ChatMessagesFabric.chatMessage();
        //FancyMenuIntegration.init();
    }

}
//?}