package dev.vesper.AIUTD.neoforge;

//? neoforge {
/*import dev.vesper.AIUTD.AIUTD;
import dev.vesper.AIUTD.common.Util;
import dev.vesper.AIUTD.config.Config;
import dev.vesper.AIUTD.config.EndUserConfig;
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
//? >=1.21.5 {
    public Component clickableLink(String message, String URL) {
        ClickEvent clickEvent = new ClickEvent.OpenUrl(URI.create(URL));
        return Component.literal(message).setStyle(Style.EMPTY.withClickEvent(clickEvent).withUnderlined(true).withColor(TextColor.fromLegacyFormat(Util.changelogColor)));
    }

    public Component ignoreMsg() {
        ClickEvent clickEvent = new ClickEvent.RunCommand("/shouldIgnore");
        return Component.translatable("aiutd.msg.ignoreClickable").setStyle(Style.EMPTY.withClickEvent(clickEvent).withUnderlined(true).withColor(TextColor.fromLegacyFormat(Util.ignoreMsgColor)));
    }
    //?}


    //? <1.21.5 {
    /^public Component clickableLink(String message, String URL) {
        return Component.literal(message).setStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, URL)).withUnderlined(true).withColor(TextColor.fromLegacyFormat(Util.changelogColor)));
    }

    public Component ignoreMsg() {
        return Component.translatable("aiutd.msg.ignoreClickable").setStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/shouldIgnore")).withUnderlined(true).withColor(TextColor.fromLegacyFormat(Util.ignoreMsgColor)));
    }

    ^///?}
    public Component toIgnore(){
        return Component.translatable("aiutd.runToIgnore")
                .setStyle(Style.EMPTY
                        .withColor(TextColor.fromLegacyFormat(Util.ignoreMsgColor))
                        .withItalic(true));
    }

    @SubscribeEvent
    private void chatMessageDisplay(ClientPlayerNetworkEvent.LoggingIn event) {
        EndUserConfig.USERCONFIG.load();

        if (Config.chatAlert && needUpdate && !AIUTD.hasNotified && !shouldIgnore) {
            Minecraft client = Minecraft.getInstance();
            assert client.player != null;

            if (useCustomMessage && !Objects.equals(Config.customMessage, "This is a custom message!")) {
                client.player.displayClientMessage(Component.literal(Config.customMessage).withStyle(Util.updateMsgColor), false);
            }
            else if (useModpackName && !Objects.equals(modpackName, "Default") && !useCustomMessage) {
                client.player.displayClientMessage(Component.literal(Component.translatable("aiutd.modPackNameMsg").getString() + modpackName + "!").withStyle(Util.updateMsgColor), false);
            }
            else {
                client.player.displayClientMessage(Component.translatable("aiutd.defaultMsg").withStyle(Util.updateMsgColor), false);
            }

            if (Config.linkChangelog) {
                client.player.displayClientMessage(clickableLink("Read the changelog!", AIUTD.changelogLink), false);
            }

            client.player.displayClientMessage(toIgnore(), false);
            AIUTD.hasNotified = true;
        }
    }
}
*///?}