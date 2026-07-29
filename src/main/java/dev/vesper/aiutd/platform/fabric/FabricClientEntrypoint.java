package dev.vesper.aiutd.platform.fabric;

//? fabric {

import dev.vesper.aiutd.AIUTD;
import dev.kikugie.fletching_table.annotation.fabric.Entrypoint;
import dev.vesper.aiutd.common.config.EndUserConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.ClientCommands;
import net.minecraft.network.chat.Component;

@Entrypoint("client")
public class FabricClientEntrypoint implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		ClientCommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess) -> {
			dispatcher.register(ClientCommands.literal("shouldIgnore").executes(context -> {
				context.getSource().sendFeedback(Component.translatable("aiutd.msgsIgnored"));
				EndUserConfig.USERCONFIG.load();
				EndUserConfig.shouldIgnore = Boolean.TRUE;
				EndUserConfig.USERCONFIG.save();
				EndUserConfig.USERCONFIG.load();
				return 1;
			}));
		}));
		AIUTD.onInitializeClient();
	}

}
//?}
