package vesper.aiutd;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.fabricmc.api.ClientModInitializer;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;



public class AIUTDAmIUpToDateClient implements ClientModInitializer {
	// TODO: grab version numbers from config instead of hardcoding to improve usability for other modpack authors
	// version Via ModrinthAPI, grabbed in VersionChecker
	String modpackVersion = "*.*.*";
	//Local version
	String currentVersion = "1.1.3";
	// Assume update as false to avoid spamming up-to-date clients in case of breaking
	public static Boolean needUpdate = Boolean.FALSE;

public static class VersionChecker {
		// grab version from Modrinth API
		public static String getLatestVersion(){
			String apiUrl = "https://api.modrinth.com/v2/project/ihN5bmrg/version";
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
				e.printStackTrace();
			}
			return null;
		}
}
	public void onInitializeClient() {

		modpackVersion = VersionChecker.getLatestVersion();
		// Compare local version to version listed via Modrinth API
		if (Objects.equals(modpackVersion, currentVersion)){
			needUpdate = Boolean.FALSE;
		}
		else {
			needUpdate = Boolean.TRUE;
		}
	}
}
