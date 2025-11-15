package dev.vesper.AIUTD.neoforge;

//? neoforge {
/*import dev.vesper.AIUTD.config.Config;
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
        return Component.literal(message).setStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, URL)).withUnderlined(true).withColor(TextColor.fromLegacyFormat(ChatFormatting.RED)));
    }

    public Component toIgnore(){
        return Component.translatable("aiutd.runToIgnore")
                .setStyle(Style.EMPTY
                        .withColor(TextColor.fromLegacyFormat(ChatFormatting.GRAY))
                        .withItalic(true));
    }

    /^public Component ignoreMsg() {
        ClickEvent clickEvent = new ClickEvent.RunCommand("/shouldIgnore");
        return Component.translatable("aiutd.msg.ignoreClickable").setStyle(Style.EMPTY.withClickEvent(clickEvent).withUnderlined(true).withColor(TextColor.fromLegacyFormat(ChatFormatting.GRAY)));
    }^/

    @SubscribeEvent
    private void chatMessageDisplay(ClientPlayerNetworkEvent.LoggingIn event) {

        if (Config.chatAlert && needUpdate) {

            if (useCustomMessage && !Objects.equals(Config.customMessage, "This is a custom message!") && !shouldIgnore) {

                Minecraft minecraft = Minecraft.getInstance();
                if (minecraft.player != null) {
                    minecraft.execute(() ->
                            minecraft.player.displayClientMessage(Component.literal(Config.customMessage), false));
                }

                if (Config.linkChangelog) {
                    if (minecraft.player != null) {
                        minecraft.execute(() ->
                                minecraft.player.displayClientMessage(clickableLink("Read the changelog!", changelogLink), false));

                    }
                }
                assert minecraft.player != null;
                minecraft.player.displayClientMessage(toIgnore(), false);

            }

            else if (useModpackName && !Objects.equals(modpackName, "Default") && !useCustomMessage && !shouldIgnore) {
                Minecraft minecraft = Minecraft.getInstance();
                if (minecraft.player != null) {
                    minecraft.execute(() ->
                            minecraft.player.displayClientMessage(Component.translatable("aiutd.modPackNameMsg" + modpackName + "!"), false));
                }

                if (Config.linkChangelog) {
                    if (minecraft.player != null) {
                        minecraft.execute(() ->
                                minecraft.player.displayClientMessage(clickableLink("Read the changelog!", changelogLink), false));

                    }
                }
                assert minecraft.player != null;
                minecraft.player.displayClientMessage(toIgnore(), false);
            }

            else if (!shouldIgnore) {
                Minecraft minecraft = Minecraft.getInstance();
                if (minecraft.player != null) {
                    minecraft.execute(() ->
                            minecraft.player.displayClientMessage(Component.translatable("aiutd.defaultMsg"), false));
                }

                if (Config.linkChangelog) {
                    if (minecraft.player != null) {
                        minecraft.execute(() ->
                                minecraft.player.displayClientMessage(clickableLink("Read the changelog!", changelogLink), false));

                    }
                }
                assert minecraft.player != null;
                minecraft.player.displayClientMessage(toIgnore(), false);

            }
        }
    }
}
*///?}