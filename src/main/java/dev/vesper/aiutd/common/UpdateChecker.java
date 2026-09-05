package dev.vesper.aiutd.common;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import dev.vesper.aiutd.AIUTD;
import dev.vesper.aiutd.common.config.Config;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

import static dev.vesper.aiutd.common.config.Config.multiLoaderBool;
import static dev.vesper.aiutd.common.config.Config.multiVersion;
import static dev.vesper.aiutd.common.config.EndUserConfig.versionCache;

public class UpdateChecker {
	public static boolean needUpdate;
	public static boolean hasChecked = false;
	static String clientVersion;
	static String APIMcVersion = "";
	static String versionLoader = "";
	static String localLoader;

	@Nullable
	public static String getVersionNumber() throws URISyntaxException, IOException {
		clientVersion = Minecraft.getInstance().getLaunchedVersion();
		URI url = new URI(AIUTD.getApiLink());
		StringBuilder result = new StringBuilder();

		URL parsed = URI.create(AIUTD.getApiLink()).toURL();
		if (!"api.modrinth.com".equalsIgnoreCase(parsed.getHost()) || !"https".equals(parsed.getProtocol())) {
			throw new SecurityException("Invalid URL for update check: " + parsed.getHost());
		}

		HttpURLConnection urlConnection = (HttpURLConnection) url.toURL().openConnection();
		urlConnection.setConnectTimeout(5000);
		urlConnection.setReadTimeout(5000);
		urlConnection.setInstanceFollowRedirects(false);

		urlConnection.setRequestMethod("GET");
		urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0");

		int responseCode = urlConnection.getResponseCode();
		if (responseCode != 200) {
			AIUTD.LOG.error("HTTP request failed with response code: {}, falling back to cached version.", responseCode);
			useCachedVersionMsg();
			return versionCache;
		}

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {
			String line;
			while ((line = reader.readLine()) != null) {
				result.append(line);
			}
		} catch (Exception e) {
			AIUTD.LOG.error("Error reading version API response: ", e);
			useCachedVersionMsg();
			return versionCache;
		}

		JSONArray versionsArray;
		try {
			versionsArray = JSON.parseArray(result.toString());
		} catch (Exception e) {
			AIUTD.LOG.error("Failed to parse version JSON: ", e);
			useCachedVersionMsg();
			return versionCache;
		}

		if (versionsArray.isEmpty()) {
			AIUTD.LOG.error("Version JSON is Empty or Null");
			useCachedVersionMsg();
			return versionCache;
		}

		if (multiLoaderBool) {
			//? if fabric {
			if (Minecraft.checkModStatus().description().contains("fabric")){
				localLoader = "fabric";
			} else {
				localLoader = "quilt";
			}
			//?}
			//? if neoforge
			//localLoader = "neoforge";

			//? if forge
			//localLoader = "forge";
		}

		JSONObject matchedVersion = findMatchingVersion(versionsArray);

		if (matchedVersion != null){
			String versionNumber = matchedVersion.getString("version_number");
			if (versionNumber != null){
				Config.HANDLER.load();
				versionCache = versionNumber;
				Config.HANDLER.load();
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

			if (multiLoaderBool && multiVersion){
				JSONArray loaders = version.getJSONArray("loaders");
				JSONArray gameVersions = version.getJSONArray("game_versions");
				if (matchesGameVersion(gameVersions) && matchesLoader(loaders)){
					return version;
				}
			} else if (multiLoaderBool){
				JSONArray loaders = version.getJSONArray("loaders");
				if (matchesLoader(loaders)){
					return version;
				}
			} else if (multiVersion){
				JSONArray gameVersions = version.getJSONArray("game_versions");
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

	public static void useCachedVersionMsg(){
		AIUTD.LOG.info("Proceeding using cached version: {}", versionCache);
	}
}
