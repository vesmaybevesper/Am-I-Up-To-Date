package dev.vesper.AIUTD.fabric;

//? fabric {
import dev.vesper.AIUTD.config.EndUserConfig;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.*;

import java.net.URI;
import java.util.Objects;
import static dev.vesper.AIUTD.common.UpdateChecker.needUpdate;
import static dev.vesper.AIUTD.config.Config.*;
import static dev.vesper.AIUTD.config.EndUserConfig.shouldIgnore;

public class ChatMessagesFabric {
    //? >=1.21.5 {
    public static MutableComponent clickableLink(String message, String url) {
        ClickEvent clickEvent = new ClickEvent.OpenUrl(URI.create(url));
        return Component.literal(message).setStyle(Style.EMPTY.withClickEvent(clickEvent).withUnderlined(true).withColor(TextColor.fromLegacyFormat(ChatFormatting.RED)));
    }

    public static MutableComponent ignoreMessage() {
        ClickEvent clickEvent = new ClickEvent.RunCommand("/shouldIgnore");
        return Component.translatable("aiutd.msg.ignoreClickable").setStyle(Style.EMPTY.withClickEvent(clickEvent).withUnderlined(true).withColor(TextColor.fromLegacyFormat(ChatFormatting.GRAY)));
    }
    //?}
    //? <1.21.5 {
    /*public static MutableComponent clickableLink(String message, String url) {
        return Component.literal(message).setStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url)).withUnderlined(true).withColor(TextColor.fromLegacyFormat(ChatFormatting.RED)));
    }

    public static MutableComponent ignoreMessage() {
        return Component.translatable("aiutd.msg.ignoreClickable").setStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/shouldIgnore")).withUnderlined(true).withColor(TextColor.fromLegacyFormat(ChatFormatting.GRAY)));
    }
    *///?}

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
        // Force userConfig to reload before the rest of the code runs on server switch, may fix #10

        ClientPlayConnectionEvents.JOIN.register((clientPacketListener, packetSender, minecraft) -> {
            EndUserConfig.USERCONFIG.load();
        });

        if (chatAlert && needUpdate && !shouldIgnore) {
            // Determine which primary message to send.
            if (useCustomMessage && !Objects.equals(customMessage, "This is a custom message!")) {
                displayMessage(Component.literal(customMessage));
            } else if (useModpackName && !Objects.equals(modpackName, "Default") && !useCustomMessage) {
                displayMessage(Component.literal(Component.translatable("aiutd.modPackNameMsg") + modpackName + "!"));
            } else {
                displayMessage(Component.translatable("aiutd.defaultMsg"));
            }
            // Register changelog link if enabled.
            if (linkChangelog) {
                displayMessage(clickableLink("Read the changelog!", changelogLink));
            }
            displayMessage(ignoreMessage());
        }

    }
}
//?}
