package vesper.aiutd;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

import static vesper.aiutd.MyConfig.*;


public class AIUTDAmIUpToDateClient implements ClientModInitializer {


	private static final Logger log = LoggerFactory.getLogger(AIUTDAmIUpToDateClient.class);

	// grab version from Modrinth API
	public static String getLatestVersion() {
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

	public Text clickableLink(String message, String url) {
		Text linkLog = Text.literal(message)
				.setStyle(Style.EMPTY
						.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url))
						.withUnderline(true)
						.withColor(TextColor.fromFormatting(Formatting.RED)));
		return linkLog;
	}



	public Text ignoreMessage () {
			Text ignore = Text.literal("Ignore update messages")
					.setStyle(Style.EMPTY
							.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/shouldIgnore"))
							.withUnderline(true)
							.withColor(TextColor.fromFormatting(Formatting.GRAY))
					);

		return ignore;
	}

	public void onInitializeClient() {
		setVersion();

		ClientCommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess) -> {
			dispatcher.register(ClientCommandManager.literal("shouldIgnore").executes(context -> {
					context.getSource().sendFeedback(Text.literal("You have set chat notifications to be ignored!"));
					shouldIgnore = Boolean.TRUE;
                return 1;
            }));

		}));

		if (chatAlert == Boolean.TRUE && needUpdate == Boolean.TRUE) {
			if (useCustomMessage == Boolean.TRUE && !Objects.equals(customMessage, "This is a custom message!") && shouldIgnore == Boolean.FALSE) {
				ClientPlayConnectionEvents.JOIN.register(((handler, sender, client) -> {
					client.execute(() -> {
						assert MinecraftClient.getInstance().player != null;
						MinecraftClient.getInstance().player.sendMessage(Text.of(customMessage), false);
					});
				}));
				if (linkChangelog == Boolean.TRUE) {
					ClientPlayConnectionEvents.JOIN.register((((handler, sender, client) -> {
						client.execute(() -> {
							assert MinecraftClient.getInstance().player != null;
							MinecraftClient.getInstance().player.sendMessage(clickableLink("Read the changelog!", changelogLink));
					});
					})));
				}

				ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
					client.execute(() -> {
						assert MinecraftClient.getInstance().player != null;
						MinecraftClient.getInstance().player.sendMessage(ignoreMessage());
					});
				});

			} else if (useModpackName == Boolean.TRUE && !Objects.equals(modpackName, "Default") && useCustomMessage == Boolean.FALSE && shouldIgnore == Boolean.FALSE) {
				ClientPlayConnectionEvents.JOIN.register(((handler, sender, client) -> {
					client.execute(() -> {
						assert MinecraftClient.getInstance().player != null;
						MinecraftClient.getInstance().player.sendMessage(Text.of("There is an update available for " + modpackName + "!"), false);
					});
				}));

				if (linkChangelog == Boolean.TRUE) {
					ClientPlayConnectionEvents.JOIN.register((((handler, sender, client) -> {
						client.execute(() -> {
							assert MinecraftClient.getInstance().player != null;
							MinecraftClient.getInstance().player.sendMessage(clickableLink("Read the changelog!", changelogLink));
						});
					})));
				}

				ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
					client.execute(() -> {
						assert MinecraftClient.getInstance().player != null;
						MinecraftClient.getInstance().player.sendMessage(ignoreMessage());
					});
				});

			} else if (shouldIgnore == Boolean.FALSE) {
				ClientPlayConnectionEvents.JOIN.register(((handler, sender, client) -> {
					client.execute(() -> {
						assert MinecraftClient.getInstance().player != null;
						MinecraftClient.getInstance().player.sendMessage(Text.of("There is an update available for your modpack!"), false);
					});
				}));

				if (linkChangelog == Boolean.TRUE) {
					ClientPlayConnectionEvents.JOIN.register((((handler, sender, client) -> {
						client.execute(() -> {
							assert MinecraftClient.getInstance().player != null;
							MinecraftClient.getInstance().player.sendMessage(clickableLink("Read the changelog!", changelogLink));
						});
					})));
				}

				ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
					client.execute(() -> {
						assert MinecraftClient.getInstance().player != null;
						MinecraftClient.getInstance().player.sendMessage(ignoreMessage());
					});
				});

			}
		}
	}
}
