package vesper.aiutd;


import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.text.Text;
import static vesper.aiutd.ChatFunctions.chatMessage;
import static vesper.aiutd.MyConfig.*;
import static vesper.aiutd.VersionSet.setVersion;


public class AIUTDAmIUpToDateClient implements ClientModInitializer {

	public void onInitializeClient() {
		setVersion();

		ClientCommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess) -> {
			dispatcher.register(ClientCommandManager.literal("shouldIgnore").executes(context -> {
					context.getSource().sendFeedback(Text.literal("You have set chat notifications to be ignored!"));
					shouldIgnore = Boolean.TRUE;
                return 1;
            }));

		}));

			chatMessage();
	}
}
