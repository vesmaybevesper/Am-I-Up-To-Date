package dev.vesper.AIUTD.fabric;

//? fabric {
import dev.vesper.AIUTD.AIUTD;
import dev.vesper.AIUTD.common.Util;
import dev.vesper.AIUTD.config.EndUserConfig;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.*;

import java.net.URI;
import java.util.Objects;
import static dev.vesper.AIUTD.common.UpdateChecker.needUpdate;
import static dev.vesper.AIUTD.config.Config.*;

public class ChatMessagesFabric {
    public static MutableComponent clickableLink(String message, String url) {
        ClickEvent clickEvent = new ClickEvent.OpenUrl(URI.create(url));
        return Component.literal(message).setStyle(Style.EMPTY.withClickEvent(clickEvent).withUnderlined(true).withColor(TextColor.fromLegacyFormat(ChatFormatting.RED)));
    }

    public static MutableComponent ignoreMessage() {
        ClickEvent clickEvent = new ClickEvent.RunCommand("/shouldIgnore");
        return Component.translatable("aiutd.msg.ignoreClickable").setStyle(Style.EMPTY.withClickEvent(clickEvent).withUnderlined(true).withColor(TextColor.fromLegacyFormat(ChatFormatting.GRAY)));
    }

    public static void chatMessage() {
        /*if (chatAlert && needUpdate && !shouldIgnore) {
            // Determine which primary message to send.
            if (useCustomMessage && !Objects.equals(customMessage, "This is a custom message!")) {
                displayMessage(Component.literal(customMessage));
            } else if (useModpackName && !Objects.equals(modpackName, "Default") && !useCustomMessage) {
                displayMessage(Component.literal(Component.translatable("aiutd.modPackNameMsg") + modpackName + "!"));
            } else {
                displayMessage(Component.translatable("aiutd.defaultMsg"));
            }
            // Display changelog link if enabled.
            if (linkChangelog) {
                displayMessage(clickableLink("Read the changelog!", changelogLink));
            }
            displayMessage(ignoreMessage());
        }*/

        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {

            // Reload the config to get fresh shouldIgnore value
            EndUserConfig.USERCONFIG.load();

            if (!chatAlert || !needUpdate || EndUserConfig.shouldIgnore || AIUTD.hasNotified) {
                return;
            }

            client.execute(() -> {
                if (client.player != null) {
                    // Determine which primary message to send.
                    if (useCustomMessage && !Objects.equals(customMessage, "This is a custom message!")) {
                        client.player.sendSystemMessage(Component.literal(customMessage));
                    } else if (useModpackName && !Objects.equals(modpackName, "Default") && !useCustomMessage) {
                        client.player.sendSystemMessage(Component.literal(Component.translatable("aiutd.modPackNameMsg").getString() + modpackName + "!"));
                    } else {
                        client.player.sendSystemMessage(Component.translatable("aiutd.defaultMsg"));
                    }

                    // Display changelog link if enabled.
                    if (linkChangelog) {
                        client.player.sendSystemMessage(clickableLink("Read the changelog!", changelogLink));
                    }

                    client.player.sendSystemMessage(ignoreMessage());
                    AIUTD.hasNotified = true;
                }
            });
        });
    }
}
//?}
