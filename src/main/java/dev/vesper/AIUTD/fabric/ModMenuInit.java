package dev.vesper.AIUTD.fabric;
//? fabric {
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.vesper.AIUTD.config.Config;

public class ModMenuInit implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return Config::configGui;
    }
}
//?}