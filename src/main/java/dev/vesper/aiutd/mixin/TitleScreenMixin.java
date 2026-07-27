package dev.vesper.aiutd.mixin;

import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
@MixinEnvironment(type = MixinEnvironment.Env.MAIN)
public class TitleScreenMixin {

	@Inject(method = "createNormalMenuOptions", at = @At("RETURN"))
	private void addUpdateNotice(CallbackInfo ci) {

	}

}
