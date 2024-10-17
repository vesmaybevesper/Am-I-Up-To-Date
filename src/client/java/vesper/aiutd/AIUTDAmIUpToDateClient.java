package vesper.aiutd;


import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse.*;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.widget.ClickableWidget;
import org.jetbrains.annotations.Nullable;


public class AIUTDAmIUpToDateClient implements ClientModInitializer {
	// @Override
	String modpackVersion = "1.1.4";
	Boolean needUpdate = Boolean.TRUE;


	public static class UpdateWidget extends ClickableWidget {
		public UpdateWidget(int x, int y, int width, int hieght) {
			super(x, y, width, hieght, Text.empty());
		}

		@Override
		protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
			int startColor = 0xFF00FF00;
			int endColor = 0xFF0000FF;

			context.fillGradient(getX(), getY(), +this.width, getY() + this.height, startColor, endColor);
		}

		@Override
		protected void appendClickableNarrations(NarrationMessageBuilder builder) {

		}
	}

	// some bullshit hacky way to get access to addDrawableChild cause it hates me and is transphobic (or im dumb(this is more likely)).
	class CustomScreen extends Screen {
		CustomScreen(Text title) {
			super(title);
		}

		@Override
		protected void init() {
			super.init();



			UpdateWidget customWidget = new UpdateWidget(40, 80, 120, 20);
			this.addDrawableChild(customWidget);
		}

	}
	public static final Logger LOGGER = LoggerFactory.getLogger("loaded");

	public void onInitializeClient() {
		//pop-up should only display if there is an update
		ClientTickEvents.END_CLIENT_TICK.register(client -> {if (needUpdate == Boolean.TRUE && client.currentScreen == null) {
			CustomScreen windowPopup = new CustomScreen(Text.literal("test"));
			MinecraftClient.getInstance().setScreen(windowPopup);
		}
		});
	}
}
