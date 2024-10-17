package vesper.aiutd;


import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.text.Text;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.widget.ClickableWidget;


public class AIUTDAmIUpToDateClient implements ClientModInitializer {
	// @Override
	String modpackVersion = "1.1.4";
	Boolean needUpdate = Boolean.TRUE;

	public static class updateWidget extends ClickableWidget {
		public updateWidget(int x, int y, int width, int hieght){
			super(x,y,width,hieght, Text.empty());
		}

		@Override
		protected void renderWidget(DrawContext context,int mouseX,int mouseY,float delta) {
			int startColor = 0xFF00FF00;
			int endColor = 0xFF0000FF;

			context.fillGradient(getX(),getY(), +this.width,getY() + this.height,startColor,endColor);
		}

		@Override
		protected void appendClickableNarrations(NarrationMessageBuilder builder) {

		}
	};

	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
	if(needUpdate == Boolean.TRUE) {
		updateWidget isUpdate = new updateWidget(40, 80, 120, 20);
		//this.addDrawableChild(isUpdate);
		System.out.println("Loaded into if");
    }
		}

	}