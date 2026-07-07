package dev.vesper.AIUTD.mixin;

import dev.vesper.AIUTD.AIUTD;
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
    private void addUpdateNotice(int topPos, int spacing, CallbackInfoReturnable<Integer> cir) {
        if (!AIUTD.isModLoaded("fancymenu")) {
            if(AIUTD.isModLoaded("notebook")){
                 buttonY = topPos - 24;
             } else {
                 buttonY = topPos;
             }
            if (needUpdate && menuAlert){
                this.addRenderableWidget(
                        Button.builder(Component.translatable("aiutd.menuNotice"), button -> {
                            /*if (AIUTD.isModLoaded("mcef")) {
                                //? <26.2{
                                *//*Minecraft.getInstance().setScreen(new MCEFWindow(Component.literal("Modpack Changelog")));
                                *//*//?} >=26.2{
                                Minecraft.getInstance().gui.setScreen(new MCEFWindow(Component.literal("Modpack Changelog")));
                                //?}
                            } else {*/
                                try {
                                    URI url = new URI(AIUTD.changelogLink);
                                    // Check if the Desktop class is supported and if the browser can be opened
                                    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                                        Desktop.getDesktop().browse(url);
                                    } else {
                                        // alternative link opening logic
                                        String os = System.getProperty("os.name").toLowerCase();
                                        try {
                                            if (os.contains("win")) {
                                                Runtime.getRuntime().exec(new String[]{"rundll32", "url.dll,FileProtocolHandler", AIUTD.changelogLink});
                                            } else if (os.contains("mac")) {
                                                Runtime.getRuntime().exec(new String[]{"open", AIUTD.changelogLink});
                                            } else if (os.contains("nix") || os.contains("nux")) {
                                                Runtime.getRuntime().exec(new String[]{"xdg-open", AIUTD.changelogLink});
                                            } else {
                                                AIUTD.LOG.error("Unsupported OS for opening a browser.");
                                            }
                                        }    catch (IOException e) {
                                            AIUTD.LOG.info(String.valueOf(e));
                                        }
                                    }
                                } catch (Exception e) {
                                    AIUTD.LOG.error(String.valueOf(e));
                                }
                        })
                                .bounds(this.width / 2 - 100 + 205, buttonY, 90, 20)
                                .build());
            }
        }
    }
}