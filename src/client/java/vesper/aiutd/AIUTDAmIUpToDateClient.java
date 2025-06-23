package vesper.aiutd;


import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.text.Text;

import java.io.IOException;
import java.net.URISyntaxException;

import static vesper.aiutd.ChatFunctions.chatMessage;
import static vesper.aiutd.MyConfig.*;


public class AIUTDAmIUpToDateClient implements ClientModInitializer {

	public void onInitializeClient() {
        // Register /shouldIgnore
        ClientCommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess) -> {
			dispatcher.register(ClientCommandManager.literal("shouldIgnore").executes(context -> {
					context.getSource().sendFeedback(Text.literal("You have set chat notifications to be ignored!"));
					shouldIgnore = Boolean.TRUE;
                return 1;
            }));

		}));
        try {
            // Check version ONLY if it hasn't already happened this run
            if (!VersionCheck.hasChecked) {
                VersionCheck.setVersion();
                VersionCheck.hasChecked = true;
            }
        } catch (URISyntaxException | IOException ignored) {

        }
        // Run chat msg code
			chatMessage();
	}
}
