package vesper.aiutd;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import java.net.URI;
import java.util.Objects;
import static vesper.aiutd.VersionCheck.needUpdate;
import static vesper.aiutd.MyConfig.*;

public class ChatFunctions {

    public static MutableText clickableLink(String message, String url) {
        ClickEvent clickEvent = new ClickEvent.OpenUrl(URI.create(url));
        return Text.literal(message).setStyle(Style.EMPTY.withClickEvent(clickEvent).withUnderline(true).withColor(TextColor.fromFormatting(Formatting.RED)));
    }

    public static MutableText ignoreMessage() {
        ClickEvent clickEvent = new ClickEvent.RunCommand("/shouldIgnore");
        return Text.literal("Ignore update messages").setStyle(Style.EMPTY.withClickEvent(clickEvent).withUnderline(true).withColor(TextColor.fromFormatting(Formatting.GRAY)));
    }


    private static void registerJoinMessage(MutableText message) {
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            client.execute(() -> {
                if (client.player != null) {
                    client.player.sendMessage(message, false);
                }
            });
        });
    }

    public static void chatMessage() {
        if (chatAlert && needUpdate && !shouldIgnore) {
            // Determine which primary message to send.
            if (Boolean.TRUE.equals(useCustomMessage) && !Objects.equals(customMessage, "This is a custom message!")) {
                registerJoinMessage((MutableText) Text.of(customMessage));
            } else if (Boolean.TRUE.equals(useModpackName) && !Objects.equals(modpackName, "Default") && Boolean.FALSE.equals(useCustomMessage)) {
                registerJoinMessage((MutableText) Text.of("There is an update available for " + modpackName + "!"));
            } else {
                registerJoinMessage((MutableText) Text.of("There is an update available for your modpack!"));
            }
            // Register changelog link if enabled.
            if (Boolean.TRUE.equals(linkChangelog)) {
                registerJoinMessage(clickableLink("Read the changelog!", changelogLink));
            }
            // Register ignore message.
            registerJoinMessage(ignoreMessage());
        }
    }
}