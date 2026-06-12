package dev.vesper.AIUTD.fabric;

//? fabric {
/*import dev.vesper.AIUTD.AIUTD;
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
    //? >=1.21.5 {
    public static MutableComponent clickableLink(String message, String url) {
        ClickEvent clickEvent = new ClickEvent.OpenUrl(URI.create(url));
        return Component.literal(message).setStyle(Style.EMPTY.withClickEvent(clickEvent).withUnderlined(true).withColor(TextColor.fromLegacyFormat(Util.changelogColor)));
    }

    public static MutableComponent ignoreMessage() {
        ClickEvent clickEvent = new ClickEvent.RunCommand("/shouldIgnore");
        return Component.translatable("aiutd.msg.ignoreClickable").setStyle(Style.EMPTY.withClickEvent(clickEvent).withUnderlined(true).withColor(TextColor.fromLegacyFormat(Util.ignoreMsgColor)));
    }
    //?}
    //? <1.21.5 {
    /^public static MutableComponent clickableLink(String message, String url) {
        return Component.literal(message).setStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url)).withUnderlined(true).withColor(TextColor.fromLegacyFormat(Util.changelogColor)));
    }

    public static MutableComponent ignoreMessage() {
        return Component.translatable("aiutd.msg.ignoreClickable").setStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/shouldIgnore")).withUnderlined(true).withColor(TextColor.fromLegacyFormat(Util.ignoreMsgColor)));
    }
    ^///?}

    private static void displayMessage(MutableComponent message) {
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            client.execute(() -> {
                if (client.player != null) {
                    client.player.displayClientMessage(message, false);
                }
            });
        });
    }

    public static void chatMessage() {
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
                        client.player.displayClientMessage(Component.literal(customMessage).withStyle(Util.updateMsgColor), false);
                    } else if (useModpackName && !Objects.equals(modpackName, "Default") && !useCustomMessage) {
                        client.player.displayClientMessage(Component.literal(Component.translatable("aiutd.modPackNameMsg").getString() + modpackName + "!").withStyle(Util.updateMsgColor), false);
                    } else {
                        client.player.displayClientMessage(Component.translatable("aiutd.defaultMsg").withStyle(Util.updateMsgColor), false);
                    }

                    // Display changelog link if enabled.
                    if (linkChangelog) {
                        client.player.displayClientMessage(clickableLink("Read the changelog!", changelogLink), false);
                    }

                    client.player.displayClientMessage(ignoreMessage(), false);
                    AIUTD.hasNotified = true;
                }
            });
        });
    }
}
*///?}
