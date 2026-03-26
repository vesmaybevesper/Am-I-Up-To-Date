package dev.vesper.AIUTD.neoforge;

//? neoforge {
/*import dev.vesper.AIUTD.config.Config;
import dev.vesper.AIUTD.config.EndUserConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.common.NeoForge;

import java.net.URI;
import java.util.Objects;

import static dev.vesper.AIUTD.common.UpdateChecker.needUpdate;
import static dev.vesper.AIUTD.config.Config.*;
import static dev.vesper.AIUTD.config.EndUserConfig.shouldIgnore;

public class ChatMessagesNeoForge {
    public void chatMessageInit(){

        NeoForge.EVENT_BUS.register(this);
    }

    public Component clickableLink(String message, String URL) {
        ClickEvent clickEvent = new ClickEvent.OpenUrl(URI.create(URL));
        return Component.literal(message).setStyle(Style.EMPTY.withClickEvent(clickEvent).withUnderlined(true).withColor(TextColor.fromLegacyFormat(ChatFormatting.RED)));
    }

    public Component ignoreMsg() {
        ClickEvent clickEvent = new ClickEvent.RunCommand("/shouldIgnore");
        return Component.translatable("aiutd.msg.ignoreClickable").setStyle(Style.EMPTY.withClickEvent(clickEvent).withUnderlined(true).withColor(TextColor.fromLegacyFormat(ChatFormatting.GRAY)));
    }
    public Component toIgnore(){
        return Component.translatable("aiutd.runToIgnore")
                .setStyle(Style.EMPTY
                        .withColor(TextColor.fromLegacyFormat(ChatFormatting.GRAY))
                        .withItalic(true));
    }

    @SubscribeEvent
    private void chatMessageDisplay(ClientPlayerNetworkEvent.LoggingIn event) {
        EndUserConfig.USERCONFIG.load();

        if (Config.chatAlert && needUpdate) {

            if (useCustomMessage && !Objects.equals(Config.customMessage, "This is a custom message!") && !shouldIgnore) {

                Minecraft minecraft = Minecraft.getInstance();
                if (minecraft.player != null) {
                    minecraft.execute(() ->
                            minecraft.player.sendSystemMessage(Component.literal(Config.customMessage)));
                }

                if (Config.linkChangelog) {
                    if (minecraft.player != null) {
                        minecraft.execute(() ->
                                minecraft.player.sendSystemMessage(clickableLink("Read the changelog!", changelogLink)));

                    }
                }
                assert minecraft.player != null;
                minecraft.player.sendSystemMessage(toIgnore());

            }

            else if (useModpackName && !Objects.equals(modpackName, "Default") && !useCustomMessage && !shouldIgnore) {
                Minecraft minecraft = Minecraft.getInstance();
                if (minecraft.player != null) {
                    minecraft.execute(() ->
                            minecraft.player.sendSystemMessage(Component.literal(Component.translatable("aiutd.modPackNameMsg").getString() + modpackName + "!")));
                }

                if (Config.linkChangelog) {
                    if (minecraft.player != null) {
                        minecraft.execute(() ->
                                minecraft.player.sendSystemMessage(clickableLink("Read the changelog!", changelogLink)));

                    }
                }
                assert minecraft.player != null;
                minecraft.player.sendSystemMessage(toIgnore());
            }

            else if (!shouldIgnore) {
                Minecraft minecraft = Minecraft.getInstance();
                if (minecraft.player != null) {
                    minecraft.execute(() ->
                            minecraft.player.sendSystemMessage(Component.translatable("aiutd.defaultMsg")));
                }

                if (Config.linkChangelog) {
                    if (minecraft.player != null) {
                        minecraft.execute(() ->
                                minecraft.player.sendSystemMessage(clickableLink("Read the changelog!", changelogLink)));

                    }
                }
                assert minecraft.player != null;
                minecraft.player.sendSystemMessage(toIgnore());

            }
        }
    }
}
*///?}