package vesper.aiutd;


import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;

import java.util.Objects;

import static vesper.aiutd.MyConfig.*;
import static vesper.aiutd.VersionSet.needUpdate;

public class ChatFunctions {

    public static Text clickableLink(String message, String url) {
        Text linkLog = Text.literal(message)
                .setStyle(Style.EMPTY
                        .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url))
                        .withUnderline(true)
                        .withColor(TextColor.fromFormatting(Formatting.RED)));
        return linkLog;
    }



    public static Text ignoreMessage() {
        Text ignore = Text.literal("Ignore update messages")
                .setStyle(Style.EMPTY
                        .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/shouldIgnore"))
                        .withUnderline(true)
                        .withColor(TextColor.fromFormatting(Formatting.GRAY))
                );

        return ignore;
    }

    public static void chatMessage() {

        if (chatAlert == Boolean.TRUE && needUpdate == Boolean.TRUE) {
            if (useCustomMessage == Boolean.TRUE && !Objects.equals(customMessage, "This is a custom message!") && shouldIgnore == Boolean.FALSE) {
                ClientPlayConnectionEvents.JOIN.register(((handler, sender, client) -> {
                    client.execute(() -> {
                        assert MinecraftClient.getInstance().player != null;
                        MinecraftClient.getInstance().player.sendMessage(Text.of(customMessage), false);
                    });
                }));
                if (linkChangelog == Boolean.TRUE) {
                    ClientPlayConnectionEvents.JOIN.register((((handler, sender, client) -> {
                        client.execute(() -> {
                            assert MinecraftClient.getInstance().player != null;
                            MinecraftClient.getInstance().player.sendMessage(clickableLink("Read the changelog!", changelogLink));
                        });
                    })));
                }

                ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
                    client.execute(() -> {
                        assert MinecraftClient.getInstance().player != null;
                        MinecraftClient.getInstance().player.sendMessage(ignoreMessage());
                    });
                });

            } else if (useModpackName == Boolean.TRUE && !Objects.equals(modpackName, "Default") && useCustomMessage == Boolean.FALSE && shouldIgnore == Boolean.FALSE) {
                ClientPlayConnectionEvents.JOIN.register(((handler, sender, client) -> {
                    client.execute(() -> {
                        assert MinecraftClient.getInstance().player != null;
                        MinecraftClient.getInstance().player.sendMessage(Text.of("There is an update available for " + modpackName + "!"), false);
                    });
                }));

                if (linkChangelog == Boolean.TRUE) {
                    ClientPlayConnectionEvents.JOIN.register((((handler, sender, client) -> {
                        client.execute(() -> {
                            assert MinecraftClient.getInstance().player != null;
                            MinecraftClient.getInstance().player.sendMessage(clickableLink("Read the changelog!", changelogLink));
                        });
                    })));
                }

                ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
                    client.execute(() -> {
                        assert MinecraftClient.getInstance().player != null;
                        MinecraftClient.getInstance().player.sendMessage(ignoreMessage());
                    });
                });

            } else if (shouldIgnore == Boolean.FALSE) {
                ClientPlayConnectionEvents.JOIN.register(((handler, sender, client) -> {
                    client.execute(() -> {
                        assert MinecraftClient.getInstance().player != null;
                        MinecraftClient.getInstance().player.sendMessage(Text.of("There is an update available for your modpack!"), false);
                    });
                }));

                if (linkChangelog == Boolean.TRUE) {
                    ClientPlayConnectionEvents.JOIN.register((((handler, sender, client) -> {
                        client.execute(() -> {
                            assert MinecraftClient.getInstance().player != null;
                            MinecraftClient.getInstance().player.sendMessage(clickableLink("Read the changelog!", changelogLink));
                        });
                    })));
                }

                ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
                    client.execute(() -> {
                        assert MinecraftClient.getInstance().player != null;
                        MinecraftClient.getInstance().player.sendMessage(ignoreMessage());
                    });
                });

            }
        }
    }

}
