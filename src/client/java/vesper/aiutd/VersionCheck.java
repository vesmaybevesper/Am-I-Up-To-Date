package vesper.aiutd;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import static vesper.aiutd.MyConfig.*;

public class VersionCheck {
    private static final Logger log = LoggerFactory.getLogger(VersionCheck.class);
    public static boolean needUpdate;
    public static int loaderLocation = 0;
    public static String localLoader;
    public static String modpackVersion = localVersion;
    public static String versionLoader = "";
    public static String APIMcVersion = "";
    public static String clientVersion;

    public static void setVersion() throws URISyntaxException, IOException {
        clientVersion = MinecraftClient.getInstance().getGameVersion();
        URI url = new URI(MyConfig.versionAPI);
        StringBuilder result = new StringBuilder();

        HttpURLConnection conn = (HttpURLConnection) url.toURL().openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");

        // Check HTTP response code first.
        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            log.error("HTTP request failed with response code: " + responseCode);
            return;
        }

        // Read the API response.
        try (BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            log.error("Error reading version API response: ", e);
            return;
        }

        // Parse the JSON response.
        JsonArray jsonArray;
        try {
            jsonArray = JsonParser.parseString(result.toString()).getAsJsonArray();
        } catch (Exception e) {
            log.error("Failed to parse version JSON: ", e);
            return;
        }

        if (jsonArray.isEmpty()) {
            log.error("Version JSON is empty.");
            return;
        }

        // Set the desired loader name if multiLoader is enabled.
        if (multiLoaderBool) {
            switch (multiLoader) {
                case FABRIC:
                    localLoader = "fabric";
                    break;
                case QUILT:
                    localLoader = "quilt";
                    break;
                case NEOFORGE:
                    localLoader = "neoforge";
                    break;
            }
        }

        int timesChecked = 0;
        // Iterate through the JSON elements (with a maximum number of tries).
        while (loaderLocation < jsonArray.size() && timesChecked <= maxChecks) {
            JsonObject versionObject = jsonArray.get(loaderLocation).getAsJsonObject();
            JsonArray gameVersionsArray = versionObject.getAsJsonArray("game_versions");
            JsonArray loadersArray = versionObject.getAsJsonArray("loaders");

            // Check for multiLoader and multiVersion conditions.
            if (multiLoaderBool && multiVersion) {
                boolean versionMatch = false;
                boolean loaderMatch = false;
                if (gameVersionsArray != null && !gameVersionsArray.isEmpty()) {
                    // Assumes checking the first entry in the array.
                    APIMcVersion = gameVersionsArray.get(0).getAsString();
                    versionMatch = Objects.equals(clientVersion, APIMcVersion);
                }
                if (loadersArray != null && !loadersArray.isEmpty()) {
                    versionLoader = loadersArray.get(0).getAsString();
                    loaderMatch = Objects.equals(versionLoader, localLoader);
                }
                if (versionMatch && loaderMatch) {
                    break;
                }
            } else if (multiLoaderBool) {
                if (loadersArray != null && !loadersArray.isEmpty()) {
                    versionLoader = loadersArray.get(0).getAsString();
                    if (Objects.equals(versionLoader, localLoader)) {
                        break;
                    }
                }
            } else if (multiVersion) {
                if (gameVersionsArray != null && !gameVersionsArray.isEmpty()) {
                    APIMcVersion = gameVersionsArray.get(0).getAsString();
                    if (Objects.equals(clientVersion, APIMcVersion)) {
                        break;
                    }
                }
            } else {
                // If neither multiVersion nor multiLoaderBool are true, use index 0.
                loaderLocation = 0;
                break;
            }

            loaderLocation++;
            timesChecked++;
        }


        modpackVersion = VersionGrabber.getLatestVersion(loaderLocation);
        needUpdate = !Objects.equals(localVersion, modpackVersion);
    }
}