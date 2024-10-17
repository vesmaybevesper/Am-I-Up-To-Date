package vesper.aiutd.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameModeSelectionScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vesper.aiutd.AIUTDAmIUpToDateClient;

import static vesper.aiutd.AIUTDAmIUpToDateClient.needUpdate;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin  extends Screen {

    protected TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("RETURN"), method = "initWidgetsNormal")
    private void addUpdateNotice(int y, int spacingY, CallbackInfo ci){
        //message should only display if there is an update
       if(needUpdate == Boolean.TRUE) {
           this.addDrawableChild(
                   ButtonWidget.builder(Text.translatable("Test"), button -> this.client.setScreen(new SelectWorldScreen(this)))
                           .dimensions(this.width / 2 - 100 + 205, y, 50, 20)
                           .build()
           );
       }
    }
}
