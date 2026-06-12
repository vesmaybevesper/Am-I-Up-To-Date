package dev.vesper.AIUTD.neoforge;

//? neoforge {
/*import dev.vesper.AIUTD.AIUTD;
import dev.vesper.AIUTD.common.Util;
import dev.vesper.AIUTD.config.Config;
import dev.vesper.AIUTD.config.EndUserConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.common.NeoForge;

import java.net.URI;
import java.util.Objects;

import static dev.vesper.AIUTD.common.UpdateChecker.needUpdate;
import static dev.vesper.AIUTD.config.Config.*;
import static dev.vesper.AIUTD.config.EndUserConfig.shouldIgnore;

public class ChatMessagesNeoForge {
    public void chatMessageInit(){NeoForge.EVENT_BUS.register(this);}

    public Component clickableLink(String message, String URL) {
        ClickEvent clickEvent = new ClickEvent.OpenUrl(URI.create(URL));
        return Component.literal(message).setStyle(Style.EMPTY.withClickEvent(clickEvent).withUnderlined(true).withColor(Util.changelogColor));
    }

    // I cant remember why this didnt work and i would like to use it lmfao, hopefully in my next update
    public Component ignoreMsg() {
        ClickEvent clickEvent = new ClickEvent.RunCommand("/shouldIgnore");
        return Component.translatable("aiutd.msg.ignoreClickable").setStyle(Style.EMPTY.withClickEvent(clickEvent).withUnderlined(true)).withStyle(Util.ignoreMsgColor);
    }
    public Component toIgnore(){
        return Component.translatable("aiutd.runToIgnore")
                .setStyle(Style.EMPTY
                        .withColor(Util.ignoreMsgColor)
                        .withItalic(true));
    }

    @SubscribeEvent
    private void chatMessageDisplay(ClientPlayerNetworkEvent.LoggingIn event) {
        EndUserConfig.USERCONFIG.load();

        if (Config.chatAlert && needUpdate && !AIUTD.hasNotified && !shouldIgnore) {
            Minecraft client = Minecraft.getInstance();
            assert client.player != null;

            if (useCustomMessage && !Objects.equals(Config.customMessage, "This is a custom message!")) {
                client.player.sendSystemMessage(Component.literal(Config.customMessage).withStyle(Util.updateMsgColor));
            }
            else if (useModpackName && !Objects.equals(modpackName, "Default") && !useCustomMessage) {
                client.player.sendSystemMessage(Component.literal(Component.translatable("aiutd.modPackNameMsg").getString() + modpackName + "!").withStyle(Util.updateMsgColor));
            }
            else {
                client.player.sendSystemMessage(Component.translatable("aiutd.defaultMsg").withStyle(Util.updateMsgColor));
            }

            if (Config.linkChangelog) {
                client.player.sendSystemMessage(clickableLink("Read the changelog!", changelogLink));
            }
            
            client.player.sendSystemMessage(toIgnore());
            AIUTD.hasNotified = true;
        }
    }
}
*///?}