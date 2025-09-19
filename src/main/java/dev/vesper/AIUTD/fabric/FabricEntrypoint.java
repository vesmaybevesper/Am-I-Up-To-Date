package dev.vesper.AIUTD.fabric;

//? fabric {
import dev.vesper.AIUTD.ExampleEventHandler; // sample_content
import dev.vesper.AIUTD.AIUTD;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents; // sample_content
import net.minecraft.server.level.ServerPlayer; // sample_content

public class FabricEntrypoint implements ModInitializer {

    @Override
    public void onInitialize() {
        AIUTD.init();
        // sample_content
        ServerLivingEntityEvents.AFTER_DAMAGE.register((entity, source, baseDamage, damageTaken, blocked) -> { // sample_content
            if (entity instanceof ServerPlayer && damageTaken > 0) { // sample_content
                ExampleEventHandler.onPlayerHurt((ServerPlayer) entity); // sample_content
            } // sample_content
        }); // sample_content
    }

}
//?}