package dev.vesper.AIUTD.fabric;

//? fabric {
import dev.vesper.AIUTD.AIUTD;
import dev.vesper.AIUTD.common.CommonClient;
import dev.vesper.AIUTD.config.Config;
import dev.vesper.AIUTD.config.EndUserConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.network.chat.Component;

public class FabricClientEntrypoint implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        AIUTD.LOG.info("Initializing {} Client", AIUTD.MOD_ID);

        ClientCommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("shouldIgnore").executes(context -> {
                context.getSource().sendFeedback(Component.translatable("You have set chat notifications to be ignored!"));
                EndUserConfig.shouldIgnore = Boolean.TRUE;
                return 1;
            }));
        }));
<<<<<<< Updated upstream
        Config.HANDLER.load();
        EndUserConfig.HANDLER.load();
=======
>>>>>>> Stashed changes
        CommonClient.init();
        ChatMessagesFabric.chatMessage();
    }

}
//?}