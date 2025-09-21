<<<<<<< Updated upstream
package dev.vesper.AIUTD.fabric;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.vesper.AIUTD.config.Config;
=======
/*package dev.vesper.AIUTD.fabric;
//? fabric {
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.vesper.AIUTD.config.Config;
import me.shedaniel.autoconfig.AutoConfig;
>>>>>>> Stashed changes

public class ModMenuInit implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
<<<<<<< Updated upstream
        return Config::configGui;
    }
}
=======
        return parent -> AutoConfig.getConfigScreen(Config.class, parent).get();
        };
    }
//?}*/
>>>>>>> Stashed changes
