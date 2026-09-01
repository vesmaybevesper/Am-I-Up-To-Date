package dev.vesper.aiutd.common.gui;

import dev.vesper.aiutd.AIUTD;
import dev.vesper.aiutd.common.ChangelogOpeners;
//? if 1.20.1
//import dev.vesper.aiutd.common.gui.BackgrounImageButton.BackgroundImageButton;
import net.minecraft.client.gui.components.Button;
//? if >=1.21.1
import net.minecraft.client.gui.components.SpriteIconButton;
//? if 1.20.1
//import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class Buttons {
	/**
	 * The update icon is an alteration of update by dokodemo from <a href="https://thenounproject.com/browse/icons/term/update/" target="_blank" title="update Icons">Noun Project</a> (CC BY 3.0)
	 */
	//? if >1.20.1 {
	public static SpriteIconButton smallButton(int posX, int posY) {
		SpriteIconButton button = SpriteIconButton.builder(Component.nullToEmpty("aiutd.menuNotice"), button1 -> ChangelogOpeners.pick(), true).width(20).sprite(Identifier.fromNamespaceAndPath(AIUTD.MOD_ID, "update"), 16, 16).build();
		button.setTooltip(Tooltip.create(Component.translatable("aiutd.menuNotice")));
		button.setPosition(posX, posY);
		return button;
	}
	//?} else {
	/*public static BackgroundImageButton smallButton(int posX, int posY){
		// This could be so much better, maybe something I will come back to at some point
		// Specifically:
		// I want the button to be 20 px while the icon is 16, without it trying to tile
		// The background of the button to look a little more like an actual button
		BackgroundImageButton imageButton = new BackgroundImageButton(posX, posY, 20, 20, 0, 0, 1, new Identifier(AIUTD.MOD_ID, "textures/gui/sprites/update.png"), 20, 20, new Button.OnPress() {
			@Override
			public void onPress(Button button) {
				ChangelogOpeners.pick();
			}
		});
		imageButton.setTooltip(Tooltip.create(Component.translatable("aiutd.menuNotice")));
		return imageButton;
	}
	*///?}

	public static Button largeButton(int posX, int posY){
		return Button.builder(Component.translatable("aiutd.menuNotice"), button1 -> {
			ChangelogOpeners.pick();
		}).bounds(posX, posY, 90, 20).build();
	}
}
