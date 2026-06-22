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
        URI url = new URI(AIUTD.modrinthApiLink);
        StringBuilder result = new StringBuilder();

        HttpURLConnection urlConnection = (HttpURLConnection) url.toURL().openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0");

        int responseCode = urlConnection.getResponseCode();
        if (responseCode != 200) {
            AIUTD.LOG.error("HTTP request failed with response code: {}", responseCode);
            AIUTD.LOG.info("Proceeding using cached version: {}", versionCache);
            return versionCache;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            AIUTD.LOG.error("Error reading version API response: ", e);
            AIUTD.LOG.info("Proceeding using cached version: {}", versionCache);
            return versionCache;
        }

        JSONArray versionsArray;
        try {
            versionsArray = JSON.parseArray(result.toString());
        } catch (Exception e) {
            AIUTD.LOG.error("Failed to parse version JSON: ", e);
            AIUTD.LOG.info("Proceeding using cached version: {}", versionCache);
            return versionCache;
        }

        if (versionsArray.isEmpty() || versionsArray == null) {
            AIUTD.LOG.error("Version JSON is Empty or Null");
            AIUTD.LOG.info("Proceeding using cached version: {}", versionCache);
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

        JSONObject matchedVersion = findMatchingVersion(versionsArray);

        if (matchedVersion != null){
            String versionNumber = matchedVersion.getString("version_number");
            if (versionNumber != null){
                Config.HANDLER.load();
                versionCache = versionNumber;
                Config.HANDLER.save();
                hasChecked = true;
                return versionNumber;
            }
        }

        AIUTD.LOG.info("No matching version found, using cached version: {}", versionCache);
        return versionCache;
    }

    private static JSONObject findMatchingVersion(JSONArray versionsArray){
        for (int i = 0; i < versionsArray.size(); i++){
            JSONObject version = versionsArray.getJSONObject(i);
            if (version == null) continue;

            JSONArray gameVersions = version.getJSONArray("game_versions");
            JSONArray loaders = version.getJSONArray("loaders");

            if (multiLoaderBool && multiVersion){
                if (matchesGameVersion(gameVersions) && matchesLoader(loaders)){
                    return version;
                }
            } else if (multiLoaderBool){
                if (matchesLoader(loaders)){
                    return version;
                }
            } else if (multiVersion){
                if (matchesGameVersion(gameVersions)){
                    return version;
                }
            } else {
                return version;
            }
        }
        return null;
    }

    public static boolean matchesGameVersion(JSONArray gameVersions){
        if (gameVersions != null && !gameVersions.isEmpty()){
            APIMcVersion = gameVersions.getString(0);
            return Objects.equals(clientVersion, APIMcVersion);
        }
        return false;
    }

    private static boolean matchesLoader(JSONArray loaders) {
        if (loaders != null && !loaders.isEmpty()) {
            versionLoader = loaders.getString(0);
            return Objects.equals(versionLoader, localLoader);
        }
        return false;
    }
}