package dev.vesper.aiutd.common;

import dev.vesper.aiutd.AIUTD;
import dev.vesper.aiutd.common.config.EndUserConfig;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;

import java.net.URI;
import java.util.Objects;

import static dev.vesper.aiutd.common.UpdateChecker.needUpdate;
import static dev.vesper.aiutd.common.config.Config.chatAlert;
import static dev.vesper.aiutd.common.config.Config.customMessage;
import static dev.vesper.aiutd.common.config.Config.linkChangelog;
import static dev.vesper.aiutd.common.config.Config.modpackName;
import static dev.vesper.aiutd.common.config.Config.useCustomMessage;
import static dev.vesper.aiutd.common.config.Config.useModpackName;

//? fabric{
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
//?} neoforge{
/*import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
*///?} forge{

//?}

public class ChatMessages {

	public static MutableComponent openChangelog(String message, String url) {
		ClickEvent clickEvent = new ClickEvent.OpenUrl(URI.create(url));
		return Component.literal(message).setStyle(Style.EMPTY.withClickEvent(clickEvent).withUnderlined(true).withColor(TextColor.fromLegacyFormat(Variables.changelogColor)));
	}

	public static MutableComponent ignoreMessage() {
		ClickEvent clickEvent = new ClickEvent.RunCommand("/shouldIgnore");
		return Component.translatable("aiutd.msg.ignoreClickable").setStyle(Style.EMPTY.withClickEvent(clickEvent).withUnderlined(true).withColor(TextColor.fromLegacyFormat(Variables.ignoreMsgColor)));
	}

	//? fabric{
	public static void sendChatMessage() {
		ClientPlayConnectionEvents.JOIN.register((_, _, client) -> {

			// Reload the config to get fresh shouldIgnore value
			EndUserConfig.USERCONFIG.load();

			if (!chatAlert || !needUpdate || EndUserConfig.shouldIgnore || AIUTD.hasNotified) {
				return;
			}

			client.execute(() -> {
				if (client.player != null) {
					// Determine which primary message to send.
					if (useCustomMessage && !Objects.equals(customMessage, "This is a custom message!")) {
						client.player.sendSystemMessage(Component.literal(customMessage).withStyle(Variables.updateMsgColor));
					} else if (useModpackName && !Objects.equals(modpackName, "Default") && !useCustomMessage) {
						client.player.sendSystemMessage(Component.literal(Component.translatable("aiutd.modPackNameMsg").getString() + modpackName + "!").withStyle(Variables.updateMsgColor));
					} else {
						client.player.sendSystemMessage(Component.translatable("aiutd.defaultMsg").withStyle(Variables.updateMsgColor));
					}

					// Display changelog link if enabled.
					if (linkChangelog) {
						client.player.sendSystemMessage(openChangelog("Read the changelog!", AIUTD.changelogLink));
					}

					client.player.sendSystemMessage(ignoreMessage());
					AIUTD.hasNotified = true;
				}
			});
		});
	}
	//?} neoforge{
	/*@SubscribeEvent
	private void chatMessageDisplay(ClientPlayerNetworkEvent.LoggingIn event) {
		EndUserConfig.USERCONFIG.load();

		if (chatAlert && needUpdate && !AIUTD.hasNotified && !shouldIgnore) {
			Minecraft client = Minecraft.getInstance();
			assert client.player != null;

			if (useCustomMessage && !Objects.equals(customMessage, "This is a custom message!")) {
				client.player.sendSystemMessage(Component.literal(customMessage).withStyle(Util.updateMsgColor));
			}
			else if (useModpackName && !Objects.equals(modpackName, "Default") && !useCustomMessage) {
				client.player.sendSystemMessage(Component.literal(Component.translatable("aiutd.modPackNameMsg").getString() + modpackName + "!").withStyle(Util.updateMsgColor));
			}
			else {
				client.player.sendSystemMessage(Component.translatable("aiutd.defaultMsg").withStyle(Util.updateMsgColor));
			}

			if (linkChangelog) {
				client.player.sendSystemMessage(openChangelog("Read the changelog!", AIUTD.changelogLink));
			}

			client.player.sendSystemMessage(ignoreMessage());
			AIUTD.hasNotified = true;
		}
	}
	*///?} forge{

	//?}
}
