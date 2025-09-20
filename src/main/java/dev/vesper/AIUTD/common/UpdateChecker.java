package dev.vesper.AIUTD.common;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import dev.vesper.AIUTD.AIUTD;
import dev.vesper.AIUTD.config.Config;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import static dev.vesper.AIUTD.config.Config.*;

public class UpdateChecker {
    public static boolean needUpdate;
    public static boolean hasChecked = false;
    public static String clientVersion;
    public static int location = 0;
    public static String APIMcVersion = "";
    public static String versionLoader = "";
    public static String localLoader;

    @Nullable
    public static String getVersionNumber() throws URISyntaxException, IOException {
        clientVersion = Minecraft.getInstance().getLaunchedVersion();
        URI url = new URI(Config.versionAPI);
        StringBuilder result = new StringBuilder();

        HttpURLConnection urlConnection = (HttpURLConnection) url.toURL().openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0");

        int responseCode = urlConnection.getResponseCode();
        if (responseCode != 200) {
            AIUTD.LOG.error("HTTP request failed with response code: " + responseCode);
            AIUTD.LOG.info("Proceeding using cached version: " + versionCache);
            return versionCache;
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            while (reader.readLine() != null) {
                result.append(reader.readLine());
            }
        } catch (Exception e) {
            AIUTD.LOG.error("Error reading version API response: ", e);
            AIUTD.LOG.info("Proceeding using cached version: " + versionCache);
            return versionCache;
        }

        JsonArray array;
        try {
            array = JsonParser.parseString(result.toString()).getAsJsonArray();
        } catch (JsonSyntaxException e) {
            AIUTD.LOG.error("Failed to parse version JSON: ", e);
            AIUTD.LOG.info("Proceeding using cached version: " + versionCache);
            return versionCache;
        }

        if (array.isEmpty()) {
            AIUTD.LOG.error("Version JSON is Empty");
            AIUTD.LOG.info("Proceeding using cached version: " + versionCache);
            return versionCache;
        }

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

        // trying to avoid using max check logic again for the sake of simplicity, but it may be the only way to cover very niche situations
        while (location < array.size() && !hasChecked) {
            JsonObject version = array.get(location).getAsJsonObject();
            JsonArray gameVersions = version.getAsJsonArray("game_versions");
            JsonArray loaders = version.getAsJsonArray("loaders");

            if (multiLoaderBool && multiVersion) {
                boolean versionMatch = false;
                boolean loaderMatch = false;
                if (gameVersions != null && !gameVersions.isEmpty()) {
                    APIMcVersion = gameVersions.get(0).getAsString();
                    versionMatch = Objects.equals(clientVersion, APIMcVersion);
                }
                if (loaders != null && !loaders.isEmpty()) {
                    versionLoader = loaders.get(0).getAsString();
                    loaderMatch = Objects.equals(versionLoader, localLoader);
                }
                if (versionMatch && loaderMatch) {
                    break;
                }
            } else if (multiLoaderBool) {
                if (loaders != null && !loaders.isEmpty()) {
                    versionLoader = loaders.get(0).getAsString();
                    if (Objects.equals(versionLoader, localLoader)) {
                        break;
                    }
                }
            } else if (multiVersion) {
                if (gameVersions != null && !gameVersions.isEmpty()) {
                    APIMcVersion = gameVersions.get(0).getAsString();
                    if (Objects.equals(clientVersion, APIMcVersion)) {
                        break;
                    }
                }
            } else {
                location = 0;
                break;
            }
            location++;
        }

        try (BufferedReader rd = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
        }

        JsonArray jsonArray = JsonParser.parseString(result.toString()).getAsJsonArray();
        if (!jsonArray.isEmpty() && location < jsonArray.size()) {
            JsonObject versionObject = jsonArray.get(location).getAsJsonObject();
            String versionNumber = versionObject.get("version_number").getAsString();
            Config.versionCache = versionNumber;
            return versionNumber;
        }

        return null;
    }
}


