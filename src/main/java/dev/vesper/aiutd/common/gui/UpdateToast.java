package dev.vesper.aiutd.common.gui;

import dev.vesper.aiutd.AIUTD;
import dev.vesper.aiutd.common.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
//~ if >=26.1 'import net.minecraft.client.gui.GuiGraphics;' -> 'import net.minecraft.client.gui.GuiGraphicsExtractor;'
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.toasts.Toast;
//? if >1.21.1
import net.minecraft.client.gui.components.toasts.ToastManager;
//? if >=1.21.5
import net.minecraft.client.renderer.RenderPipelines;
//? if <=1.21.4
//import net.minecraft.client.gui.components.toasts.ToastComponent;
//? if <=1.21.5
//import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;


public class UpdateToast implements Toast {

	private Visibility visibility;
	private final Component title;
	private final Component message;

	public UpdateToast(Component title, Component message) {
		this.title = title;
		this.message = message;
	}

	//? if >=1.21.2 {
	@Override
	public Visibility getWantedVisibility() {
		return visibility;
	}

	@Override
	public void update(ToastManager manager, long fullyVisibleForMs) {
		// this shouldn't get called at all if showToast is false but...
		if (!Config.showToast) visibility = Visibility.HIDE;
		if (fullyVisibleForMs >= Config.toastDisplayTime * manager.getNotificationDisplayTimeMultiplier()) {
			visibility = Visibility.HIDE;
		}
	}
	//?}

	//? if >=1.21.2 {
	@Override
	//~ if <26.1 'extractRenderState(GuiGraphicsExtractor graphics' -> 'render(GuiGraphics graphics'
	public void extractRenderState(GuiGraphicsExtractor graphics, Font font, long fullyVisibleForMs) {
		int w = width();
		int h = height();

		//background
		graphics.fill(0, 0, w, h, 0x5d5858);


		//border
		graphics.fill(0, 0, width(), height() - 1, 0xFFFFFFFF);
		graphics.fill(0, height() - 1, width(), height(), 0xFFFFFFFF);
		graphics.fill(0, 0, 1, height(), 0xFFFFFFFF);
		graphics.fill(width() - 1, 0, width(), height(), 0xFFFFFFFF);

		//? if >=1.21.11 {
		graphics.blitSprite(RenderPipelines.GUI_TEXTURED, Identifier.fromNamespaceAndPath(AIUTD.MOD_ID, "update"), 20, 90, 20, 20);
		//?} 1.21.5 && 1.21.2 {
		//graphics.blitSprite(RenderType::guiTextured, Identifier.fromNamespaceAndPath(AIUTD.MOD_ID, "update"), 20, 90, 20, 20);
		//?} else {
		//graphics.blitSprite(Identifier.fromNamespaceAndPath(AIUTD.MOD_ID, "update"), 20, 90, 20, 20);
		//?}

		int textX = 32;
		int titleY = h / 2 - font.lineHeight - 1;
		int messageY = h / 2 + 1;

		//~ if <26.1 '.text' -> '.drawString' {
		graphics.text(font, title, textX, titleY, 0xFFFFFFFF, false);
		graphics.text(font, message, textX, messageY, 0xcdc2c2, false);
		//~}

	}
	//?} else {
	/*// yeah so this could be very wrong but i see no other way so we're just going to have to try it
	@Override
	public Visibility render(GuiGraphics graphics, ToastComponent toastComponent, long l) {
		int w = width();
		int h = height();

		//background
		graphics.fill(0, 0, w, h, 0x5d5858);


		//border
		graphics.fill(0, 0, width(), height() - 1, 0xFFFFFFFF);
		graphics.fill(0, height() - 1, width(), height(), 0xFFFFFFFF);
		graphics.fill(0, 0, 1, height(), 0xFFFFFFFF);
		graphics.fill(width() - 1, 0, width(), height(), 0xFFFFFFFF);

		//? if 1.21.1
		//graphics.blitSprite(Identifier.fromNamespaceAndPath(AIUTD.MOD_ID, "update"), 20, 90, 20, 20);

		//I think my numbers are in the wrong place, needs testing
		//? if 1.20.1
		//graphics.blit(new Identifier(AIUTD.MOD_ID, "update"), 20, 90, 20, 20, 0, 0);

		int textX = 32;
		int titleY = h / 2 - Minecraft.getInstance().font.lineHeight - 1;
		int messageY = h / 2 + 1;

		graphics.drawString(Minecraft.getInstance().font, title, textX, titleY, 0xFFFFFFFF, false);
		graphics.drawString(Minecraft.getInstance().font, message, textX, messageY, 0xcdc2c2, false);

		// this shouldn't get called at all if showToast is false but...
		if (!Config.showToast) visibility = Visibility.HIDE;
		if (l >= Config.toastDisplayTime * toastComponent.getNotificationDisplayTimeMultiplier()) {
			visibility = Visibility.HIDE;
		}
		return visibility;
	}
	*///?}

	@Override
	public int width() {
		return 180;
	}

	@Override
	public int height() {
		return 45;
	}

	public static void show(){
		//? if >=1.21.2 {
		//~ if <26.2 '.gui.toastManager()' -> '.getToastManager()' {
		if (!Config.customToastMessage && !Config.customToastTitle) {
			Minecraft.getInstance().gui.toastManager().addToast(new UpdateToast(Component.translatable("aiutd.menuNotice"), Component.translatable("aiutd.defaultToastText")));
		} else if (!Config.customToastMessage){
			Minecraft.getInstance().gui.toastManager().addToast(new UpdateToast(Component.translatable("aiutd.menuNotice"), Component.literal(Config.toastMessage)));
		} else if (!Config.customToastTitle) {
			Minecraft.getInstance().gui.toastManager().addToast(new UpdateToast(Component.literal(Config.toastTitle), Component.translatable("aiutd.defaultToastText")));
		} else {
			Minecraft.getInstance().gui.toastManager().addToast(new UpdateToast(Component.literal(Config.toastTitle), Component.literal(Config.toastMessage)));
		}
		//~}
		//?} else {
		/*if (!Config.customToastMessage && !Config.customToastTitle) {
			Minecraft.getInstance().getToasts().addToast(new UpdateToast(Component.translatable("aiutd.menuNotice"), Component.translatable("aiutd.defaultToastText")));
		} else if (!Config.customToastMessage){
			Minecraft.getInstance().getToasts().addToast(new UpdateToast(Component.translatable("aiutd.menuNotice"), Component.literal(Config.toastMessage)));
		} else if (!Config.customToastTitle) {
			Minecraft.getInstance().getToasts().addToast(new UpdateToast(Component.literal(Config.toastTitle), Component.translatable("aiutd.defaultToastText")));
		} else {
			Minecraft.getInstance().getToasts().addToast(new UpdateToast(Component.literal(Config.toastTitle), Component.literal(Config.toastMessage)));
		}
		*///?}
	}
}
