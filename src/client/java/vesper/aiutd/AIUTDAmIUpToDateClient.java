package vesper.aiutd;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.hud.ChatHud;
import org.spongepowered.asm.mixin.Mixin;

public class AIUTDAmIUpToDateClient implements ClientModInitializer {

@Mixin (ChatHud.class)
public static class chatVersion {

}
	public void onInitializeClient() {
	}
}
