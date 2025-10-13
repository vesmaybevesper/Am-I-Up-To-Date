package dev.vesper.AIUTD.common;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
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

        JSONArray array1;
        try {
            array1 = JSON.parseArray(String.valueOf(result));
        } catch (Exception e) {
            AIUTD.LOG.error("Failed to parse version JSON: ", e);
            AIUTD.LOG.info("Proceeding using cached version: " + versionCache);
            return versionCache;
        }

        if (array1.isEmpty()) {
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
        while (location < array1.size() && !hasChecked) {
            JSONObject version1 = JSONObject.from(array1.get(location));
            JSONArray gameVersions1 = JSONArray.of(version1.get("game_versions"));
            JSONArray loaders1 = JSONArray.of(version1.get("loaders"));

            if (multiLoaderBool && multiVersion) {
                boolean versionMatch = false;
                boolean loaderMatch = false;
                if (gameVersions1 != null && !gameVersions1.isEmpty()) {
                    APIMcVersion = gameVersions1.getFirst().toString();
                    versionMatch = Objects.equals(clientVersion, APIMcVersion);
                }
                if (loaders1 != null && !loaders1.isEmpty()) {
                    versionLoader = loaders1.getFirst().toString();
                    loaderMatch = Objects.equals(versionLoader, localLoader);
                }
                if (versionMatch && loaderMatch) {
                    break;
                }
            } else if (multiLoaderBool) {
                if (loaders1 != null && !loaders1.isEmpty()) {
                    versionLoader = loaders1.getFirst().toString();
                    if (Objects.equals(versionLoader, localLoader)) {
                        break;
                    }
                }
            } else if (multiVersion) {
                if (gameVersions1 != null && !gameVersions1.isEmpty()) {
                    APIMcVersion = gameVersions1.getFirst().toString();
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

        JSONArray jsonArray1 = JSONArray.of(result.toString());
        if (!jsonArray1.isEmpty() && location < jsonArray1.size()) {
            JSONObject versionObject = (JSONObject) jsonArray1.get(location);
            String versionNumber = versionObject.get("version_number").toString();
            Config.versionCache = versionNumber;
            return versionNumber;
        }

        return null;
    }
}


