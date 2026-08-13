package dev.vesper.aiutd.mixin;

import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import dev.vesper.aiutd.AIUTD;
import dev.vesper.aiutd.common.ChangelogOpeners;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

import static dev.vesper.aiutd.common.UpdateChecker.needUpdate;
import static dev.vesper.aiutd.common.config.Config.menuAlert;

@Mixin(TitleScreen.class)
@MixinEnvironment(type = MixinEnvironment.Env.MAIN)
public class TitleScreenMixin extends Screen {

	@Unique
	private int buttonY;

	protected TitleScreenMixin(Component component) {
		super(component);
	}

	@Inject(method = "createNormalMenuOptions", at = @At("RETURN"))
	//? >=1.21.11{
	private void addUpdateNotice(int topPos, int spacing, CallbackInfoReturnable<Integer> cir) {
	//?} <1.21.11 && > 1.21.1{
	/*private void addUpdateNotice(int i, int j, CallbackInfoReturnable<Integer> cir) {
	*///?} <=1.21.1{
	/*private void addUpdateNotice(int i, int j, CallbackInfo ci) {
	*///?}
		if (!AIUTD.isModLoaded("fancymenu")) {
			if(AIUTD.isModLoaded("notebook")){
				//? >=1.21.11{
				buttonY = topPos - 24;
				//?} <1.21.11{
				/*buttonY = i - 24;
				*///?}
			} else {
				//? >=1.21.11{
				buttonY = topPos;
				//?} <1.21.11{
				/*buttonY = i;
				*///?}
			}
			if (needUpdate && menuAlert){
				this.addRenderableWidget(
						Button.builder(Component.translatable("aiutd.menuNotice"), button -> {
									ChangelogOpeners.browser();
								})
								.bounds(this.width / 2 - 100 + 205, buttonY, 90, 20)
								.build());
			}
		}
	}
}
