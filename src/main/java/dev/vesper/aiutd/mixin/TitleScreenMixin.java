package dev.vesper.aiutd.mixin;

import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import dev.vesper.aiutd.AIUTD;
import dev.vesper.aiutd.common.gui.Buttons;
import dev.vesper.aiutd.common.config.Config;
import dev.vesper.aiutd.common.gui.UpdateToast;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static dev.vesper.aiutd.common.UpdateChecker.needUpdate;
import static dev.vesper.aiutd.common.config.Config.menuAlert;
import static dev.vesper.aiutd.common.config.Config.showToast;

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
	//private void addUpdateNotice(int i, int j, CallbackInfoReturnable<Integer> cir) {
	//?} <=1.21.1{
	//private void addUpdateNotice(int i, int j, CallbackInfo ci) {
	//?}
		if (!AIUTD.isModLoaded("fancymenu")) {
			if(AIUTD.isModLoaded("notebook")){
				//? >=1.21.11{
				buttonY = topPos - 24;
				//?} <1.21.11{
				//buttonY = i - 24;
				//?}
			} else {
				//? >=1.21.11{
				buttonY = topPos;
				//?} <1.21.11{
				//buttonY = i;
				//?}
			}

			if (needUpdate && menuAlert){
				if (Config.bigButton) {
					this.addRenderableWidget(Buttons.largeButton(this.width / 2 - 100 + 205, buttonY));
				} else {
					this.addRenderableWidget(Buttons.smallButton(this.width / 2 - 100 + 205, buttonY));
				}
			} /*else if (needUpdate && showToast) {
				UpdateToast.show();
			}*/
		}
	}
}
