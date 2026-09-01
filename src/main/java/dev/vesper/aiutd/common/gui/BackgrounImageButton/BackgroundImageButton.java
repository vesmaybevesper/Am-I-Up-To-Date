package dev.vesper.aiutd.common.gui.BackgrounImageButton;

//? if 1.20.1 {
/*import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.resources.Identifier;

public class BackgroundImageButton extends ImageButton {

	public BackgroundImageButton(int i, int j, int k, int l, int m, int n, int o, Identifier resourceLocation, int p, int q, Button.OnPress onPress) {
		super(i, j, k, l, m, n, o, resourceLocation, p, q, onPress, CommonComponents.EMPTY);
	}

	@Override
	public void renderWidget(GuiGraphics guiGraphics, int i, int j, float f) {
		int state = this.isHoveredOrFocused() ? 1 : 0;
		guiGraphics.blitNineSliced(AbstractButton.WIDGETS_LOCATION, this.getX(), this.getY(), this.getWidth(), this.getHeight(), 20, 4, 200, 20, 0, 46 + state * 20);

		super.renderWidget(guiGraphics, i, j, f);
	}
}
*///?}
