package vesper.aiutd;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;


import static vesper.aiutd.MyConfig.chatAlert;


public class AIUTDAmIUpToDateClient implements ClientModInitializer {


    private static final Logger log = LoggerFactory.getLogger(AIUTDAmIUpToDateClient.class);

    // grab version from Modrinth API
	public static String getLatestVersion(){
		StringBuilder result = new StringBuilder();
		try {
			URL url = new URL(MyConfig.versionAPI);

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
				return getVersionElement.getAsJsonObject().get("version_number").getAsString();
			}
		} catch (Exception e) {
			log.error("e: ", e);
		}
		return null;
	}

	public static boolean needUpdate;
	public static void setVersion() {
		// version Via ModrinthAPI, grabbed in VersionChecker
		String modpackVersion = AIUTDAmIUpToDateClient.getLatestVersion();
		//Local version
		String localVersion = MyConfig.localVersion;

		// Compare local version to version listed via Modrinth API
		if (Objects.equals(localVersion, modpackVersion)) {
			needUpdate = Boolean.FALSE;
		} else {
			needUpdate = Boolean.TRUE;
		}
	}

	public void onInitializeClient() {
		setVersion();
		if (chatAlert == Boolean.TRUE && needUpdate == Boolean.TRUE) {
			ClientPlayConnectionEvents.JOIN.register(((handler, sender, client) -> {
				client.execute(() -> {
					assert MinecraftClient.getInstance().player != null;
				MinecraftClient.getInstance().player.sendMessage(Text.of("There is an update available for your modpack!"), false);
				});
			}));
		}
	}
}
