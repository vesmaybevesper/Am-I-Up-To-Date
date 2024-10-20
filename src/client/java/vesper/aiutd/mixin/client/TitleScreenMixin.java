package vesper.aiutd.mixin.client;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Objects;


@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin  extends Screen {

    @Shadow @Final private static Logger LOGGER;



    protected TitleScreenMixin(Text title) {
        super(title);
    }

    // grab version from Modrinth API
    private static String getLatestVersion(){
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL("https://api.modrinth.com/v2/project/ihN5bmrg/version");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();

            JsonArray jsonArray = JsonParser.parseString(result.toString()).getAsJsonArray();
            if (!jsonArray.isEmpty()) {
                JsonElement getVersionElement = jsonArray.get(0);
                String latestVersion = getVersionElement.getAsJsonObject().get("version_number").getAsString();
                return latestVersion;
            }
        } catch (Exception e) {
            LOGGER.info(String.valueOf(e));
        }
        return null;
    }

    public boolean needUpdate;

    @Inject(at = @At("RETURN"), method = "initWidgetsNormal")
    private void addUpdateNotice(int y, int spacingY, CallbackInfo ci) {
        super.init();

        // TODO: grab version numbers from config instead of hardcoding to improve usability for other modpack authors
        // version Via ModrinthAPI, grabbed in VersionChecker
        String modpackVersion;
        //Local version
        String currentVersion;
        currentVersion = "1.1.4";


        modpackVersion = getLatestVersion();
        // Compare local version to version listed via Modrinth API
        if (Objects.equals(currentVersion, modpackVersion)){
            needUpdate = Boolean.FALSE;
        }
        else {
            needUpdate = Boolean.TRUE;
        }

        //message should only display if there is an update
        if (needUpdate == Boolean.TRUE) {
            this.addDrawableChild(
            ButtonWidget.builder(Text.translatable("Update Available"), button -> {
                       try {
                            // URL to fetch from
                            URI url = new URI("https://modrinth.com/modpack/vespers-vanilla-enhanced/changelog");
                            // Check if the Desktop class is supported and if the browser can be opened
                            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                                Desktop.getDesktop().browse(url);  // Open the browser with the URL
                            } else {
                                // alternative link opening logic
                                String os = System.getProperty("os.name").toLowerCase();
                                try {
                                    if (os.contains("win")) {
                                        Runtime.getRuntime().exec(new String[]{"rundll32", "url.dll,FileProtocolHandler", "https://modrinth.com/modpack/vespers-vanilla-enhanced/changelog"});
                                    } else if (os.contains("mac")) {
                                        Runtime.getRuntime().exec(new String[]{"open", "https://modrinth.com/modpack/vespers-vanilla-enhanced/changelog"});
                                    } else if (os.contains("nix") || os.contains("nux")) {
                                        Runtime.getRuntime().exec(new String[]{"xdg-open", "https://modrinth.com/modpack/vespers-vanilla-enhanced/changelog"});
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
        //
          //         ButtonWidget.builder(Text.translatable("Update Available"), button -> this.client.setScreen(new SelectWorldScreen(this)))*/
             //              .dimensions(this.width / 2 - 100 + 205, y, 90, 20)
               //     .build();
        }
    }
}

