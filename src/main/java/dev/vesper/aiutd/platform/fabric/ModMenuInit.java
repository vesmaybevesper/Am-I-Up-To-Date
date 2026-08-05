package dev.vesper.aiutd.platform.fabric;

//? fabric{
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.vesper.aiutd.common.config.Config;

public class ModMenuInit implements ModMenuApi {
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return Config::config;
	}
}
//?}
