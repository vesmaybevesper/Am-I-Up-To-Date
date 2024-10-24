package vesper.aiutd;

import eu.midnightdust.core.MidnightLib;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import vesper.aiutd.MyConfig;

import java.nio.file.Files;
import java.nio.file.Path;


public class AIUTDAmIUpToDateClient implements ClientModInitializer {
	public void onInitializeClient() {
		MidnightConfig.init("AIUTD", MyConfig.class);
	}
}
