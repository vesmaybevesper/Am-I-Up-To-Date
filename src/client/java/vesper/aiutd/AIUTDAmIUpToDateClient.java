package vesper.aiutd;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import vesper.aiutd.MyConfig;


public class AIUTDAmIUpToDateClient implements ClientModInitializer {
	public void onInitializeClient() {
		MyConfig.HANDLER.load();
	}
}
