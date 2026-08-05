package dev.vesper.aiutd.platform.neoforge;

//? neoforge {

/*import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import dev.vesper.aiutd.AIUTD;
import dev.vesper.aiutd.common.config.Config;
import dev.vesper.aiutd.common.config.EndUserConfig;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;


@Mod(AIUTD.MOD_ID)
public class NeoforgeEntrypoint {

	public NeoforgeEntrypoint() {
		AIUTD.onInitialize();
		AIUTD.onInitializeClient();
	}

	@EventBusSubscriber(modid = AIUTD.MOD_ID, value = Dist.CLIENT)
	private static class ClientEvents {
		@SubscribeEvent
		public static void onClientSetup(final FMLClientSetupEvent event) {
			AIUTD.LOG.info("Initializing {} Client", AIUTD.MOD_ID);
			ModLoadingContext.get().registerExtensionPoint(
					IConfigScreenFactory.class,
					() -> (client, parent) -> Config.config(parent)
			);
		}

		@EventBusSubscriber(modid = AIUTD.MOD_ID, value = Dist.CLIENT)
		private static class RegisterCommand {
			private static int shouldIgnore(CommandContext<?> context) {
				assert Minecraft.getInstance().player != null;
				//? >=26.1{
				Minecraft.getInstance().player.sendSystemMessage(Component.translatable("aiutd.msgsIgnored"));
				//?} <= 1.21.11{
				/^Minecraft.getInstance().player.displayClientMessage(Component.translatable("aiutd.msgsIgnored"), false);
				^///?}
				EndUserConfig.shouldIgnore = true;
				EndUserConfig.USERCONFIG.save();
				return 1;
			}
		}

		@SubscribeEvent
		public static void onRegisterCommands(RegisterClientCommandsEvent event) {
			AIUTD.LOG.info(String.valueOf(event.getBuildContext()));
			CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
			dispatcher.register(
					Commands.literal("shouldIgnore")
							.executes(RegisterCommand::shouldIgnore)
			);
		}
	}
}
*///?}
