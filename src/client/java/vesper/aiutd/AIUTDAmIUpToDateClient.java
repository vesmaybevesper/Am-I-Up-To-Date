package vesper.aiutd;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.minecraft.client.MinecraftClient;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.util.SelectionManager;
import net.minecraft.text.Text;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.widget.ClickableWidget;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AIUTDAmIUpToDateClient implements ClientModInitializer {
	// TODO: grab version numbers from config instead of hardcoding to improve usability for other modpack authors
	// version Via ModrinthAPI, grabbed in VersionChecker
	String modpackVersion = "Placeholder";
	//Local version
	String currentVersion = "1.1.3";
	// Assume update as false to avoid spamming up-to-date clients in case of breaking
	static Boolean needUpdate = Boolean.FALSE;


	public static final Logger PRINT_TO_LOGS = LoggerFactory.getLogger("modpackVersion");

	public static class UpdateWidget extends ClickableWidget {
		public UpdateWidget(int x, int y, int width, int hieght) {
			super(x, y, width, hieght, Text.empty());
		}

		@Override
		protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
			int startColor = 0xFFbcafab;
			int endColor = 0xFF252326;

			context.fillGradient(getX(), getY(), getX()+this.width, getY() + this.height, startColor, endColor);
			if (mouseClicked(1720,720,1)) {
				needUpdate = Boolean.FALSE;
				PRINT_TO_LOGS.info("CLICKED");
			}
		}

		@Override
		protected void appendClickableNarrations(NarrationMessageBuilder builder) {

		}
	}

	// some bullshit hacky way to get access to addDrawableChild cause it hates me and is transphobic (or im dumb(this is more likely)).
	class CustomScreen extends Screen {
		CustomScreen(Text title) {
			super(title);
		}

		@Override
		protected void init() {
			super.init();


			//some frankly insane math to center the pop-up
			double widthPercent = 0.2;
			double hieghtPercent =0.15;
			int popupWidth = (int) (this.width * widthPercent);
			int popupHeight = (int) (this.height *hieghtPercent);
			int x = (this.width/2) - (popupWidth/2);
			int y = (this.height/2) - (popupHeight/2);
			UpdateWidget customWidget = new UpdateWidget(x, y, popupWidth, popupHeight);
			this.addDrawableChild(customWidget);
		}

	}
public class VersionChecker {
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

		//pop-up should only display if there is an update
		ClientTickEvents.END_CLIENT_TICK.register(client -> {if (needUpdate == Boolean.TRUE && client.currentScreen == null) {
			CustomScreen windowPopup = new CustomScreen(Text.literal("test"));
			MinecraftClient.getInstance().setScreen(windowPopup);
            needUpdate = Boolean.FALSE;
		}
		});
	}
}
