package vesper.aiutd.mixin.client;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.util.Objects;
import vesper.aiutd.AIUTDAmIUpToDateClient;
import vesper.aiutd.MyConfig;
import static vesper.aiutd.MyConfig.menuAlert;


@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {

    @Shadow @Final private static Logger LOGGER;
    // import strings from config



    protected TitleScreenMixin(Text title) {
        super(title);
    }

    public boolean needUpdate;

    @Inject(at = @At("RETURN"), method = "initWidgetsNormal")
    private void addUpdateNotice(int y, int spacingY, CallbackInfo ci) {
        super.init();
        // version Via ModrinthAPI, grabbed in VersionChecker
        String modpackVersion = AIUTDAmIUpToDateClient.getLatestVersion();
        //Local version
        String localVersion = MyConfig.localVersion;


        // Compare local version to version listed via Modrinth API
        if (Objects.equals(localVersion, modpackVersion)){
            needUpdate = Boolean.FALSE;
        }
        else {
            needUpdate = Boolean.TRUE;
        }

        //message should only display if there is an update
        if (needUpdate == Boolean.TRUE && menuAlert == Boolean.TRUE) {
            this.addDrawableChild(
            ButtonWidget.builder(Text.translatable("Update Available"), button -> {
                       try {
                            // URL to fetch from
                            URI url = new URI(MyConfig.changelogLink);
                            // Check if the Desktop class is supported and if the browser can be opened
                            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                                Desktop.getDesktop().browse(url);  // Open the browser with the URL
                            } else {
                                // alternative link opening logic
                                String os = System.getProperty("os.name").toLowerCase();
                                try {
                                    if (os.contains("win")) {
                                        Runtime.getRuntime().exec(new String[]{"rundll32", "url.dll,FileProtocolHandler", MyConfig.changelogLink});
                                    } else if (os.contains("mac")) {
                                        Runtime.getRuntime().exec(new String[]{"open", MyConfig.changelogLink});
                                    } else if (os.contains("nix") || os.contains("nux")) {
                                        Runtime.getRuntime().exec(new String[]{"xdg-open", MyConfig.changelogLink});
                                    } else {
                                        System.out.println("Unsupported OS for opening a browser.");
                                    }
                                } catch (IOException e) {
                                    LOGGER.info(String.valueOf(e));
                                }
                            }
                        } catch (Exception e) {
                            LOGGER.info(String.valueOf(e));
                        }
                    })
                    .dimensions(this.width / 2 - 100 + 205, y, 90, 20)
                    .build());
        }
    }
}

