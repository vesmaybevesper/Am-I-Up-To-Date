package dev.vesper.aiutd.platform.fabric;

//? fabric {

import dev.vesper.aiutd.AIUTD;
import dev.kikugie.fletching_table.annotation.fabric.Entrypoint;
import net.fabricmc.api.ClientModInitializer;

@Entrypoint("client")
public class FabricClientEntrypoint implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		AIUTD.onInitializeClient();
	}

}
//?}
