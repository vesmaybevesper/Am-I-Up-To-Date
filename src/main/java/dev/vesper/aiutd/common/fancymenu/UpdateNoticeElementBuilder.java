package dev.vesper.aiutd.common.fancymenu;

import de.keksuccino.fancymenu.customization.action.ActionInstance;
import de.keksuccino.fancymenu.customization.action.blocks.AbstractExecutableBlock;
import de.keksuccino.fancymenu.customization.action.blocks.ExecutableBlockDeserializer;
import de.keksuccino.fancymenu.customization.action.blocks.GenericExecutableBlock;
import de.keksuccino.fancymenu.customization.element.AbstractElement;
import de.keksuccino.fancymenu.customization.element.ElementBuilder;
import de.keksuccino.fancymenu.customization.element.SerializedElement;
import de.keksuccino.fancymenu.customization.layout.editor.LayoutEditorScreen;
import de.keksuccino.fancymenu.customization.overlay.CustomizationOverlay;
import de.keksuccino.fancymenu.customization.requirement.internal.RequirementContainer;
import de.keksuccino.fancymenu.util.rendering.ui.widget.button.ExtendedButton;
import de.keksuccino.fancymenu.util.threading.MainThreadTaskExecutor;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
//? <=26.2{
import org.lwjgl.glfw.GLFW;
//?}

public class UpdateNoticeElementBuilder extends ElementBuilder<UpdateNoticeElement, UpdateNoticeEditorElement<?,?>> {

	public UpdateNoticeElementBuilder() {
		super("aiutd_update_button");
	}

	@Override
	public @NotNull UpdateNoticeElement buildDefaultInstance() {
		UpdateNoticeElement element = new UpdateNoticeElement(this);
		element.baseWidth = 90;
		element.baseHeight = 20;
		element.label = String.valueOf(Component.translatable("aiutd.menuNotice"));
		element.setWidget(new ExtendedButton(0,0,0,0, Component.empty(), (press) -> {
			if((CustomizationOverlay.getCurrentMenuBarInstance() == null) || !CustomizationOverlay.getCurrentMenuBarInstance().isUserNavigatingInMenuBar()) {
				boolean isMousePressed = isAnyMouseButtonPressed();

				if(element.openChangelogOnClick) {
					element.openChangelog();
				}

				element.getExecutableBlock().execute();

				MainThreadTaskExecutor.executeInMainThread(() ->{
					if(isMousePressed) press.setFocused(false);
				}, MainThreadTaskExecutor.ExecuteTiming.POST_CLIENT_TICK);
			}
		}));
		return element;
	}

	private static boolean isAnyMouseButtonPressed() {
		long window = Minecraft.getInstance().getWindow().handle();
		//? <26.3{
        return GLFW.glfwGetMouseButton(window, GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS
                || GLFW.glfwGetMouseButton(window, GLFW.GLFW_MOUSE_BUTTON_RIGHT) == GLFW.GLFW_PRESS;
        //?} >=26.3{
		/*// temp to be able to build :)
		return false;
		*///?}

	}

	@Override
	public UpdateNoticeElement deserializeElement(@NotNull SerializedElement serializedElement) {
		UpdateNoticeElement element = buildDefaultInstance();

		element.label = serializedElement.getValue("label");

		String buttonExecuteBlockId = serializedElement.getValue("button_element_executable_block_identifier");
		if(buttonExecuteBlockId != null) {
			AbstractExecutableBlock buttonExecuteBlock = ExecutableBlockDeserializer.deserializeWithIdentifier(serializedElement, buttonExecuteBlockId);
			if(buttonExecuteBlock instanceof GenericExecutableBlock genericExecutableBlock) {
				element.actionExecutor = genericExecutableBlock;
			}
		} else {
			GenericExecutableBlock genericExecutableBlock = new GenericExecutableBlock();
			genericExecutableBlock.getExecutables().addAll(ActionInstance.deserializeAll(serializedElement));
			element.actionExecutor = genericExecutableBlock;
		}

		element.hoverSound = deserializeAudioResourceSupplier(serializedElement.getValue("hoversound"));
		element.hoverLabel = serializedElement.getValue("hoverlabel");
		element.tooltip = serializedElement.getValue("description");
		element.clickSound = deserializeAudioResourceSupplier(serializedElement.getValue("clicksound"));
		element.backgroundTextureNormal = deserializeImageResourceSupplier(serializedElement.getValue("backgroundnormal"));
		element.backgroundTextureHover = deserializeImageResourceSupplier(serializedElement.getValue("backgroundhovered"));
		element.backgroundTextureInactive = deserializeImageResourceSupplier(serializedElement.getValue("background_texture_inactive"));

		String loopBackAnimation = serializedElement.getValue("loopbackgroundanimations");
		if((loopBackAnimation != null) && !loopBackAnimation.equalsIgnoreCase("false")) {
			element.loopBackgroundAnimations = false;
		}

		String restartAnimation = serializedElement.getValue("restartbackgroundanimations");
		if((restartAnimation != null) && !restartAnimation.equalsIgnoreCase("false")) {
			element.loopBackgroundAnimations = false;
		}

		element.nineSliceCustomBackground = deserializeBoolean(element.nineSliceCustomBackground, serializedElement.getValue("nine_slice_custom_background"));
		element.nineSliceBorderX = deserializeNumber(Integer.class, element.nineSliceBorderX, serializedElement.getValue("nine_slice_border_x"));
		element.nineSliceBorderY = deserializeNumber(Integer.class, element.nineSliceBorderY, serializedElement.getValue("nine_slice_border_y"));

		element.backgroundAnimationNormal = serializedElement.getValue("backgroundanimationnormal");
		element.backgroundAnimationHover = serializedElement.getValue("backgroundanimationhovered");
		element.backgroundAnimationInactive = serializedElement.getValue("background_animation_inactive");

		element.navigatable = deserializeBoolean(element.navigatable, serializedElement.getValue("navigatable"));

		element.hideWhenNoUpdate = deserializeBoolean(element.hideWhenNoUpdate, serializedElement.getValue("hide_when_no_update"));
		element.openChangelogOnClick = deserializeBoolean(element.openChangelogOnClick, serializedElement.getValue("open_changelog_on_click"));

		String activeStateRequirementIdentifier = serializedElement.getValue("widget_active_state_requirement_container_identifier");
		if(activeStateRequirementIdentifier != null) {
			RequirementContainer c = RequirementContainer.deserializeWithIdentifier(activeStateRequirementIdentifier, serializedElement);
			if(c != null) {
				element.activeStateSupplier = c;
			}
		}
		return element;
	}

	@Override
	protected SerializedElement serializeElement(@NotNull UpdateNoticeElement updateNoticeElement, @NotNull SerializedElement serializedElement) {
		serializedElement.putProperty("button_element_executable_block_identifier", updateNoticeElement.actionExecutor.identifier);
		updateNoticeElement.actionExecutor.serializeToExistingPropertyContainer(serializedElement);

		if (updateNoticeElement.backgroundTextureNormal != null) {
			serializedElement.putProperty("backgroundnormal", updateNoticeElement.backgroundTextureNormal.getSourceWithPrefix());
		}
		if (updateNoticeElement.backgroundAnimationNormal != null) {
			serializedElement.putProperty("backgroundanimationnormal", updateNoticeElement.backgroundAnimationNormal);
		}
		if (updateNoticeElement.backgroundTextureHover != null) {
			serializedElement.putProperty("backgroundhovered", updateNoticeElement.backgroundTextureHover.getSourceWithPrefix());
		}
		if (updateNoticeElement.backgroundAnimationHover != null) {
			serializedElement.putProperty("backgroundanimationhovered", updateNoticeElement.backgroundAnimationHover);
		}
		if (updateNoticeElement.backgroundTextureInactive != null) {
			serializedElement.putProperty("background_texture_inactive", updateNoticeElement.backgroundTextureInactive.getSourceWithPrefix());
		}
		if (updateNoticeElement.backgroundAnimationInactive != null) {
			serializedElement.putProperty("background_animation_inactive", updateNoticeElement.backgroundAnimationInactive);
		}

		serializedElement.putProperty("restartbackgroundanimations", "" + updateNoticeElement.restartBackgroundAnimationsOnHover);
		serializedElement.putProperty("loopbackgroundanimations", "" + updateNoticeElement.loopBackgroundAnimations);
		serializedElement.putProperty("nine_slice_custom_background", "" + updateNoticeElement.nineSliceCustomBackground);
		serializedElement.putProperty("nine_slice_border_x", "" + updateNoticeElement.nineSliceBorderX);
		serializedElement.putProperty("nine_slice_border_y", "" + updateNoticeElement.nineSliceBorderY);

		if (updateNoticeElement.hoverSound != null) {
			serializedElement.putProperty("hoversound", updateNoticeElement.hoverSound.getSourceWithPrefix());
		}
		if (updateNoticeElement.hoverLabel != null) {
			serializedElement.putProperty("hoverlabel", updateNoticeElement.hoverLabel);
		}
		if (updateNoticeElement.clickSound != null) {
			serializedElement.putProperty("clicksound", updateNoticeElement.clickSound.getSourceWithPrefix());
		}
		if (updateNoticeElement.tooltip != null) {
			serializedElement.putProperty("description", updateNoticeElement.tooltip);
		}
		if (updateNoticeElement.label != null) {
			serializedElement.putProperty("label", updateNoticeElement.label);
		}

		serializedElement.putProperty("navigatable", "" + updateNoticeElement.navigatable);

		serializedElement.putProperty("hide_when_no_updates", "" + updateNoticeElement.hideWhenNoUpdate);
		serializedElement.putProperty("open_changelog_on_click", "" + updateNoticeElement.openChangelogOnClick);

		serializedElement.putProperty("widget_active_state_requirement_container_identifier", updateNoticeElement.activeStateSupplier.identifier);

		updateNoticeElement.activeStateSupplier.serializeToExistingPropertyContainer(serializedElement);

		return serializedElement;
	}

	@Override
	public @NotNull UpdateNoticeEditorElement<?,?> wrapIntoEditorElement(@NotNull UpdateNoticeElement updateNoticeElement, @NotNull LayoutEditorScreen layoutEditorScreen) {
		return new UpdateNoticeEditorElement<>(updateNoticeElement, layoutEditorScreen);
	}

	@Override
	public @NotNull Component getDisplayName(@Nullable AbstractElement abstractElement) {
		if((abstractElement instanceof UpdateNoticeElement n) && (n.getWidget() != null) && !n.getWidget().getMessage().getString().replace(" ", " ").isEmpty()) {
			return n.getWidget().getMessage();
		}
		return Component.translatable("aiutd.fancymenu.update_button");
	}

	@Override
	public @Nullable Component[] getDescription(@Nullable AbstractElement abstractElement) {
		return new Component[]{
				Component.translatable("aiutd.fancymenu.update_button.desc.line1"),
				Component.translatable("aiutd.fancymenu.update_button.desc.line2")
		};
	}
}
