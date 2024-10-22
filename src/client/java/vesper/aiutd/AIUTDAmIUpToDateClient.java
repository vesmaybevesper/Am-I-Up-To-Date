package vesper.aiutd;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import vesper.aiutd.MyConfig;

import java.nio.file.Files;
import java.nio.file.Path;


public class AIUTDAmIUpToDateClient implements ClientModInitializer {
	public void onInitializeClient() {
		MyConfig config = MyConfig.HANDLER.instance();
		Path configPath = FabricLoader.getInstance().getConfigDir().resolve("aiutd-mod-config.json5");

		if (!Files.exists(configPath)) {
			config.localVersion = "0.0.0";
			config.versionAPI = "https://api.modrinth.com/v2/project/<id>/version";
			config.changelogLink = "https://modrinth.com/modpack/<modpack URL>/changelog";
			config.HANDLER.save();
		} else {
			config.HANDLER.load();
		}
	}
}
