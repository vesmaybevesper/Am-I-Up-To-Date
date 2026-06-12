package dev.vesper.AIUTD.fabric;

//? fabric {
import dev.vesper.AIUTD.AIUTD;
import net.fabricmc.api.ModInitializer;

public class FabricEntrypoint implements ModInitializer {

    @Override
    public void onInitialize() {
        AIUTD.init();
    }

}
//?}