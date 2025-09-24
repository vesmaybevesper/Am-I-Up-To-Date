package dev.vesper.AIUTD.fabric;

//? fabric {
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.*;
import java.util.Objects;
import static dev.vesper.AIUTD.common.UpdateChecker.needUpdate;
import static dev.vesper.AIUTD.config.Config.*;
import static dev.vesper.AIUTD.config.EndUserConfig.shouldIgnore;

public class ChatMessagesFabric {
    public static MutableComponent clickableLink(String message, String url) {
        return Component.literal(message)
                .setStyle(Style.EMPTY
                        .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url))
                        .withUnderlined(true)
                        .withColor(TextColor.fromLegacyFormat(ChatFormatting.RED)));
    }

    public static MutableComponent ignoreMessage() {

        return Component.translatable("Ignore update messages")
                .setStyle(Style.EMPTY
                        .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/shouldIgnore"))
                        .withUnderlined(true)
                        .withColor(TextColor.fromLegacyFormat(ChatFormatting.GRAY))
                );
    }

    private static void registerJoinMessage(MutableComponent message) {
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            client.execute(() -> {
                if (client.player != null) {
                    client.player.displayClientMessage(message, false);
                }
            });
        });
    }

    public static void chatMessage() {
        if (chatAlert && needUpdate && !shouldIgnore) {
            // Determine which primary message to send.
            if (useCustomMessage && !Objects.equals(customMessage, "This is a custom message!")) {
                registerJoinMessage((MutableComponent) Component.literal(customMessage));
            } else if (useModpackName && !Objects.equals(modpackName, "Default") && !useCustomMessage) {
                registerJoinMessage((MutableComponent) Component.translatable("There is an update available for" + modpackName + "!"));
            } else {
                registerJoinMessage((MutableComponent) Component.translatable("There is an update available for your modpack!"));
            }
            // Register changelog link if enabled.
            if (linkChangelog) {
                registerJoinMessage(clickableLink("Read the changelog!", changelogLink));
            }
            // Register ignore message.
            registerJoinMessage(ignoreMessage());
        }
    }
}
//?}
