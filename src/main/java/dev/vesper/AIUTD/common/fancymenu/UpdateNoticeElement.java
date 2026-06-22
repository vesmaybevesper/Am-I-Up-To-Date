//? !1.21.10{
package dev.vesper.AIUTD.common.fancymenu;

import de.keksuccino.fancymenu.customization.action.blocks.GenericExecutableBlock;
import de.keksuccino.fancymenu.customization.element.AbstractElement;
import de.keksuccino.fancymenu.customization.element.ElementBuilder;
import de.keksuccino.fancymenu.customization.element.ExecutableElement;
import de.keksuccino.fancymenu.customization.placeholder.PlaceholderParser;
import de.keksuccino.fancymenu.customization.requirement.internal.RequirementContainer;
import de.keksuccino.fancymenu.mixin.mixins.common.client.IMixinAbstractWidget;
import de.keksuccino.fancymenu.util.rendering.ui.tooltip.TooltipHandler;
import de.keksuccino.fancymenu.util.rendering.ui.tooltip.UITooltip;
import de.keksuccino.fancymenu.util.rendering.ui.widget.CustomizableWidget;
import de.keksuccino.fancymenu.util.rendering.ui.widget.NavigatableWidget;
import de.keksuccino.fancymenu.util.resource.RenderableResource;
import de.keksuccino.fancymenu.util.resource.ResourceSupplier;
import de.keksuccino.fancymenu.util.resource.resources.audio.IAudio;
import de.keksuccino.fancymenu.util.resource.resources.texture.ITexture;
import de.keksuccino.fancymenu.util.threading.MainThreadTaskExecutor;
import dev.vesper.AIUTD.AIUTD;
import dev.vesper.AIUTD.config.Config;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static dev.vesper.AIUTD.common.UpdateChecker.needUpdate;

public class UpdateNoticeElement extends AbstractElement implements ExecutableElement {


    @Nullable
    private AbstractWidget widget;
    public ResourceSupplier<IAudio> clickSound;
    public ResourceSupplier<IAudio> hoverSound;
    @Nullable
    public String label;
    @Nullable
    public String hoverLabel;
    public String tooltip;
    public ResourceSupplier<ITexture> backgroundTextureNormal;
    public ResourceSupplier<ITexture> backgroundTextureHover;
    public ResourceSupplier<ITexture> backgroundTextureInactive;
    public String backgroundAnimationNormal;
    public String backgroundAnimationHover;
    public String backgroundAnimationInactive;
    public boolean loopBackgroundAnimations = true;
    public boolean restartBackgroundAnimationsOnHover = true;
    public boolean nineSliceCustomBackground = false;
    public int nineSliceBorderX = 5;
    public int nineSliceBorderY = 5;
    public boolean navigatable = true;
    @NotNull
    public GenericExecutableBlock actionExecutor = new GenericExecutableBlock();
    @NotNull
    public RequirementContainer activeStateSupplier = new RequirementContainer();

    public boolean hideWhenNoUpdate = true;
    public boolean openChangelogOnClick = true;

    public UpdateNoticeElement(@NotNull ElementBuilder<?, ?> builder) {
        super(builder);
    }

    @Override
    public void tick() {
        if (this.getWidget() ==  null) return;
        this.updateWidget();
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int i, int i1, float v) {
        if (this.getWidget() ==  null) return;

        this.updateWidget();

        if (hideWhenNoUpdate && !needUpdate) return;

        if (!this.shouldRender()) return;

        if (isEditor()){
            //?<1.21.10{
            /*net.minecraft.client.gui.components.Tooltip cachedVanillaTooltip = this.widget.getTooltip();
            *///?}
            //? >=1.21.10{
            net.minecraft.client.gui.components.Tooltip cachedVanillaTooltip = ((IMixinAbstractWidget) this.getWidget()).getTooltipHolderFancyMenu().get();
            //?}
            boolean cachedVisible = this.getWidget().visible;
            boolean cachedActive = this.getWidget().active;
            this.getWidget().visible = true;
            this.getWidget().active = true;
            this.getWidget().setTooltip(null);
            MainThreadTaskExecutor.executeInMainThread(() -> {
                this.getWidget().visible = cachedVisible;
                this.getWidget().active = cachedActive;
                assert this.getWidget() != null;
                this.getWidget().setTooltip(cachedVanillaTooltip);
            }, MainThreadTaskExecutor.ExecuteTiming.POST_CLIENT_TICK);
        }

        this.renderElementWidget(guiGraphics, i, i1, v);

    }

    @Override
    public void tickVisibleInvisible() {
        super.tickVisibleInvisible();
        if (this.getWidget() !=  null) this.updateWidget();
    }

    @Override
    public @Nullable List<GuiEventListener> getWidgetsToRegister() {
        if (this.getWidget() == null) return null;
        return List.of(this.getWidget());
    }

    @Override
    public @NotNull GenericExecutableBlock getExecutableBlock() {
        return this.actionExecutor;
    }

    public @Nullable AbstractWidget getWidget() {
        return this.widget;
    }

    protected void renderElementWidget(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partial) {
        if (this.getWidget() != null) {
            if (this.getWidget().getHeight() <= 0) return;
            if (this.getWidget().getWidth() <= 0) return;
            this.getWidget().render(graphics, mouseX, mouseY, partial);
        }
    }

    public void updateWidget() {
        this.updateWidgetActiveState();
        this.updateWidgetVisibility();
        this.updateWidgetAlpha();
        this.updateWidgetTooltip();
        this.updateWidgetLabels();
        this.updateWidgetHoverSound();
        this.updateWidgetClickSound();
        this.updateWidgetTexture();
        this.updateWidgetSize();
        this.updateWidgetPosition();
        this.updateWidgetNavigatable();
    }

    public void updateWidgetActiveState() {
        if (this.getWidget() == null) return;
        this.getWidget().active = this.activeStateSupplier.requirementsMet() && needUpdate;
    }

    public void updateWidgetNavigatable() {
        if (this.getWidget() instanceof NavigatableWidget w) {
            w.setNavigatable(this.navigatable);
        }
    }

    public void updateWidgetVisibility() {
        if (this.getWidget() instanceof CustomizableWidget w) {
            boolean shouldHide = !this.shouldRender() || (hideWhenNoUpdate && !needUpdate);
            w.setHiddenFancyMenu(shouldHide);
        }
    }

    public void updateWidgetAlpha() {
        if (this.getWidget() == null) return;
        this.getWidget().setAlpha(this.opacity);
    }

    public void updateWidgetPosition() {
        if (this.getWidget() == null) return;
        this.getWidget().setX(this.getAbsoluteX());
        this.getWidget().setY(this.getAbsoluteY());
    }

    public void updateWidgetSize() {
        if (this.getWidget() == null) return;
        this.getWidget().setWidth(this.getAbsoluteWidth());
        ((IMixinAbstractWidget) this.getWidget()).setHeightFancyMenu(this.getAbsoluteHeight());
    }

    public void updateWidgetTooltip() {
        if ((this.tooltip != null) && (this.getWidget() != null) && this.getWidget().isHovered()
                && this.getWidget().visible && this.shouldRender() && !isEditor()) {
            String tooltip = this.tooltip.replace("%n%", "\n");
            String parsedTooltip = PlaceholderParser.replacePlaceholders(tooltip);
            List<Component> tooltipLines = splitTooltipLines(parsedTooltip);
            TooltipHandler.INSTANCE.addRenderTickWidgetTooltip(this.getWidget(), UITooltip.of((Component) tooltipLines));
        }
    }

    private List<Component> splitTooltipLines(String parsedTooltip) {
        List<Component> line = new ArrayList<>();
        if (parsedTooltip == null || parsedTooltip.isEmpty()) {
            return line;
        }

        String[] lines = parsedTooltip.split("\n");
        for (String s : lines) {
            if(!s.isEmpty()){
                line.add(Component.literal(s));
            }
        }
        return line;
    }

    public void updateWidgetLabels() {
        if (this.getWidget() == null) return;
        if (this.label != null) {
            this.getWidget().setMessage(buildComponent(this.label));
        } else {
            this.getWidget().setMessage(Component.empty());
        }
        if ((this.hoverLabel != null) && this.getWidget().isHoveredOrFocused() && this.getWidget().active) {
            this.getWidget().setMessage(buildComponent(this.hoverLabel));
        }
    }

    public void updateWidgetHoverSound() {
        if (this.getWidget() instanceof CustomizableWidget w) {
            w.setHoverSoundFancyMenu((this.hoverSound != null) ? this.hoverSound.get() : null);
        }
    }

    public void updateWidgetClickSound() {
        if (this.getWidget() instanceof CustomizableWidget w) {
            w.setCustomClickSoundFancyMenu((this.clickSound != null) ? this.clickSound.get() : null);
        }
    }

    public void updateWidgetTexture() {
        RenderableResource backNormal = null;
        RenderableResource backHover = null;
        RenderableResource backInactive = null;

        if (this.backgroundTextureNormal != null) {
            backNormal = this.backgroundTextureNormal.get();
        }
        if (this.backgroundTextureHover != null) {
            backHover = this.backgroundTextureHover.get();
        }
        if (this.backgroundTextureInactive != null) {
            backInactive = this.backgroundTextureInactive.get();
        }

        if (this.getWidget() instanceof CustomizableWidget w) {
            w.setNineSliceCustomBackground_FancyMenu(this.nineSliceCustomBackground);
            w.setNineSliceBorderX_FancyMenu(this.nineSliceBorderX);
            w.setNineSliceBorderY_FancyMenu(this.nineSliceBorderY);
            w.setCustomBackgroundNormalFancyMenu(backNormal);
            w.setCustomBackgroundHoverFancyMenu(backHover);
            w.setCustomBackgroundInactiveFancyMenu(backInactive);
            w.setCustomBackgroundResetBehaviorFancyMenu(this.restartBackgroundAnimationsOnHover
                    ? CustomizableWidget.CustomBackgroundResetBehavior.RESET_ON_HOVER
                    : CustomizableWidget.CustomBackgroundResetBehavior.RESET_NEVER);
        }
    }

    public void setWidget(@Nullable AbstractWidget widget) {
        this.widget = widget;
    }

    public void openChangelog() {
        try {
            URI url = new URI(AIUTD.changelogLink);
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(url);
            } else {
                String os = System.getProperty("os.name").toLowerCase();
                try {
                    if (os.contains("win")) {
                        Runtime.getRuntime().exec(new String[]{"rundll32", "url.dll,FileProtocolHandler", AIUTD.changelogLink});
                    } else if (os.contains("mac")) {
                        Runtime.getRuntime().exec(new String[]{"open", AIUTD.changelogLink});
                    } else if (os.contains("nix") || os.contains("nux")) {
                        Runtime.getRuntime().exec(new String[]{"xdg-open", AIUTD.changelogLink});
                    } else {
                        AIUTD.LOG.warn("Unsupported OS for opening browser");
                    }
                } catch (IOException e) {
                    AIUTD.LOG.error("Failed to open changelog", e);
                }
            }
        } catch (Exception e) {
            AIUTD.LOG.error("Failed to open changelog URL", e);
        }
    }
}
//?}