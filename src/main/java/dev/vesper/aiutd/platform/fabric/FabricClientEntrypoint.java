package dev.vesper.aiutd.platform.fabric;

//? fabric {

import dev.vesper.aiutd.AIUTD;
import dev.kikugie.fletching_table.annotation.fabric.Entrypoint;
import dev.vesper.aiutd.common.config.EndUserConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
//? >=26.1{
import net.fabricmc.fabric.api.client.command.v2.ClientCommands;
//?} <=1.21.11{
/*import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
*///?}
import net.minecraft.network.chat.Component;

@Entrypoint("client")
public class FabricClientEntrypoint implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		ClientCommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess) -> {
			//? >=26.1{
			dispatcher.register(ClientCommands.literal("shouldIgnore").executes(context -> {
				context.getSource().sendFeedback(Component.translatable("aiutd.msgsIgnored"));
				//?} <=1.21.11{
				/*dispatcher.register(ClientCommandManager.literal("shouldIgnore").executes(context -> {
					context.getSource().sendFeedback(Component.translatable("aiutd.msgsIgnored"));
				*///?}
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
