package dev.vesper.AIUTD.mixin;

import dev.vesper.AIUTD.AIUTD;
import dev.vesper.AIUTD.config.Config;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import static dev.vesper.AIUTD.common.UpdateChecker.needUpdate;
import static dev.vesper.AIUTD.config.Config.menuAlert;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {

    protected TitleScreenMixin(Component component) {
        super(component);
    }
    @Unique
    private int buttonY;

    @Inject(method = "createNormalMenuOptions", at = @At("RETURN"))
    private void addUpdateNotice(int i, int j, CallbackInfoReturnable<Integer> cir) {
        if (!AIUTD.isModLoaded("fancymenu")) {
            if(AIUTD.isModLoaded("notebook")){
                 buttonY = i - 24;
             } else {
                 buttonY = i;
             }
            if (needUpdate && menuAlert){
                this.addRenderableWidget(
                        Button.builder(Component.translatable("aiutd.menuNotice"), button -> {
                            try {

                                URI url = new URI(Config.changelogLink);
                                // Check if the Desktop class is supported and if the browser can be opened
                                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                                    Desktop.getDesktop().browse(url);
                                } else {
                                    // alternative link opening logic
                                    String os = System.getProperty("os.name").toLowerCase();
                                    try {
                                        if (os.contains("win")) {
                                            // link: "https://modrinth.com/modpack/" + {} + "/changelog", Config.modrinthSlug"
                                            Runtime.getRuntime().exec(new String[]{"rundll32", "url.dll,FileProtocolHandler", Config.changelogLink});
                                        } else if (os.contains("mac")) {
                                            Runtime.getRuntime().exec(new String[]{"open", Config.changelogLink});
                                        } else if (os.contains("nix") || os.contains("nux")) {
                                            Runtime.getRuntime().exec(new String[]{"xdg-open", Config.changelogLink});
                                        } else {
                                            AIUTD.LOG.error("Unsupported OS for opening a browser.");
                                        }
                                    }    catch (IOException e) {
                                        AIUTD.LOG.info(String.valueOf(e));
                                    }
                                }
                            } catch (Exception e) {
                                AIUTD.LOG.info(String.valueOf(e));
                            }
                        })
                                .bounds(this.width / 2 - 100 + 205, buttonY, 90, 20)
                                .build());
            }
        }
    }
}