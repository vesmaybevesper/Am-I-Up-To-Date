package vesper.aiutd;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Objects;

import static vesper.aiutd.MyConfig.*;
import static vesper.aiutd.MyConfig.LoaderEnum.*;


public class MultiVersionSupport {
    private static final Logger log = LoggerFactory.getLogger(MultiVersionSupport.class);
    public static boolean needUpdate;
    public static int loaderLocation = 0;
    public static String localLoader;
    public static String  modpackVersion = localVersion;
    public static String versionLoader = "";
    public static String APIMcVersion = "";
    public static String clientVersion;
    public static void setVersion() {
        StringBuilder result = new StringBuilder();

        clientVersion = MinecraftClient.getInstance().getGameVersion();

        try {
            URI url = new URI(MyConfig.versionAPI);

            HttpURLConnection conn = (HttpURLConnection) url.toURL().openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
        } catch (Exception fetchVersionError) {
            log.error("fetchVersionError: ", fetchVersionError);
            log.info("If this is first launch this error can be ignored");
        }

        JsonArray jsonArray = JsonParser.parseString(result.toString()).getAsJsonArray();

        if (!jsonArray.isEmpty()) {
            if (multiVersion && multiLoaderBool) {
                // set which loader to look for
                if (multiLoader == FABRIC) {
                    localLoader = "fabric";
                } else if (multiLoader == QUILT) {
                    localLoader = "quilt";
                } else if (multiLoader == NEOFORGE) {
                    localLoader = "neoforge";
                }
                // Parse for first version with Identical loader and mc version
                while (loaderLocation < jsonArray.size() && !Objects.equals(localLoader, versionLoader) && !Objects.equals(clientVersion, APIMcVersion)) {
                    JsonElement getVersionElement = jsonArray.get(loaderLocation);
                    APIMcVersion = getVersionElement.getAsJsonObject().get("game_versions").getAsString();
                    versionLoader = getVersionElement.getAsJsonObject().get("loaders").getAsString();
                    loaderLocation = loaderLocation + 1;
                }
                // grab its version
                modpackVersion = VersionGrabber.getLatestVersion(loaderLocation);
            } else if (multiLoaderBool) {
                // set which loader to look for
                if (multiLoader == FABRIC) {
                    localLoader = "fabric";
                } else if (multiLoader == QUILT) {
                    localLoader = "quilt";
                } else if (multiLoader == NEOFORGE) {
                    localLoader = "neoforge";
                }
                // Parse for First version with identical loader name
                while (loaderLocation < jsonArray.size() && !Objects.equals(versionLoader, localLoader)) {
                    JsonElement getVersionElement = jsonArray.get(loaderLocation);
                    versionLoader = getVersionElement.getAsJsonObject().get("loaders").getAsString();
                    loaderLocation = loaderLocation + 1;
                }
                // get its version number
                modpackVersion = VersionGrabber.getLatestVersion(loaderLocation);
            } else if (multiVersion) {
                // Parse for first MP version with identical Minecraft version
                while (loaderLocation < jsonArray.size() && !Objects.equals(clientVersion, APIMcVersion)) {
                    JsonElement getVersionElement = jsonArray.get(loaderLocation);
                    APIMcVersion = getVersionElement.getAsJsonObject().get("game_versions").getAsString();
                    loaderLocation = loaderLocation + 1;
                }
                // Get its version number
                modpackVersion = VersionGrabber.getLatestVersion(loaderLocation);

            } else {
                // version Via ModrinthAPI, grabbed in VersionChecker
                modpackVersion = VersionGrabber.getLatestVersion(0);
            }
            // Compare local version to version listed via Modrinth API
            if (Objects.equals(localVersion, modpackVersion)) {
                needUpdate = Boolean.FALSE;
            } else {
                needUpdate = Boolean.TRUE;
            }
        }
    }
}
