package dev.vesper.aiutd.platform.forge;

//? forge {

/*import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import dev.vesper.aiutd.AIUTD;
import dev.vesper.aiutd.common.config.Config;
import dev.vesper.aiutd.common.config.EndUserConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod(AIUTD.MOD_ID)
public class ForgeEntrypoint {

	public ForgeEntrypoint() {
		AIUTD.onInitialize();
		AIUTD.onInitializeClient();
	}

	@Mod.EventBusSubscriber(modid = AIUTD.MOD_ID, value = Dist.CLIENT)
	private static class Client {
		@SubscribeEvent
		public void onClientSetup(FMLClientSetupEvent event){
			AIUTD.LOG.info("Initializing {} Client", AIUTD.MOD_ID);
			ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((mc, screen) -> Config.config(screen)));
		}


		@Mod.EventBusSubscriber(modid = AIUTD.MOD_ID, value = Dist.CLIENT)
		private static class RegisterCommand{
			private static int shouldIgnore(CommandContext<?> context){
				assert Minecraft.getInstance().player != null;
				Minecraft.getInstance().player.displayClientMessage(Component.translatable("aiutd.msgsIgnored"), false);
				EndUserConfig.shouldIgnore = true;
				EndUserConfig.USERCONFIG.save();
				return 1;
			}
		}

		@SubscribeEvent
		public static void onRegisterCommands(RegisterClientCommandsEvent event){
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
