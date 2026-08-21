package dev.vesper.aiutd.common.fancymenu;

//? 1.20.1 && !forge || 1.21.1 || >= 1.21.11{
import de.keksuccino.fancymenu.customization.action.ui.ActionScriptEditorWindowBody;
import de.keksuccino.fancymenu.customization.element.editor.AbstractEditorElement;
import de.keksuccino.fancymenu.customization.layout.editor.LayoutEditorScreen;
import de.keksuccino.fancymenu.customization.requirement.ui.ManageRequirementsWindowBody;
import de.keksuccino.fancymenu.util.LocalizationUtils;
import de.keksuccino.fancymenu.util.input.TextValidators;
import de.keksuccino.fancymenu.util.rendering.ui.contextmenu.v2.ContextMenu;
import de.keksuccino.fancymenu.util.rendering.ui.tooltip.UITooltip;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
//? forge{
/*import net.minecraft.client.gui.GuiGraphics;
*///?}

public class UpdateNoticeEditorElement<E extends UpdateNoticeEditorElement<?, ?>, N extends UpdateNoticeElement> extends AbstractEditorElement<E, N> {
	public UpdateNoticeEditorElement(@NotNull N element, @NotNull LayoutEditorScreen editor) {
		super(element, editor);
	}

	@Override
	public void init() {
		super.init();

		this.rightClickMenu.addClickableEntry("manage_actions", Component.translatable("fancymenu.editor.action.screens.manage_screen.manage"), ((contextMenu, clickableContextMenuEntry) -> {
			ActionScriptEditorWindowBody s = new ActionScriptEditorWindowBody(this.getElement().getExecutableBlock(), (call) ->{
				if (call != null){
					this.editor.history.saveSnapshot();
					this.getElement().actionExecutor = call;
				}
				//? <26.2{
				/*Minecraft.getInstance().setScreen(this.editor);
				 *///?} >=26.2{
				Minecraft.getInstance().setScreenAndShow(this.editor);
				//?}
			});
			//? <26.2{
			/*Minecraft.getInstance().setScreen(s);
			 *///?} >=26.2{
			Minecraft.getInstance().setScreenAndShow(s);
			//?}
		})).setTooltipSupplier(((contextMenu, contextMenuEntry) -> UITooltip.of(LocalizationUtils.splitLocalizedLines("fancymenu.editor.elements.button.manage_actions.desc")))).setIcon(ContextMenu.IconFactory.getIcon("script")).setStackable(false);

		this.rightClickMenu.addClickableEntry("widget_active_state_controller", Component.translatable("fancymenu.elements.button.active_state_controller"),  ((contextMenu, contextMenuEntry) -> {
			ManageRequirementsWindowBody s = new ManageRequirementsWindowBody(this.getElement().activeStateSupplier.copy(false), (call) ->{
				if (call != null){
					this.editor.history.saveSnapshot();
					this.getElement().activeStateSupplier = call;
				}
				//? <26.2{
				/*Minecraft.getInstance().setScreen(this.editor);
				 *///?} >=26.2{
				Minecraft.getInstance().setScreenAndShow(this.editor);
				//?}
			});
			//? <26.2{
			/*Minecraft.getInstance().setScreen(s);
			 *///?} >=26.2{
			Minecraft.getInstance().setScreenAndShow(s);
			//?}
		})).setTooltipSupplier(((contextMenu, contextMenuEntry) -> UITooltip.of(LocalizationUtils.splitLocalizedLines("fancymenu.elements.button.active_state_controller.desc")))).setStackable(false);

		this.rightClickMenu.addSeparatorEntry("update_button_separator_1");

		this.addToggleContextMenuEntryTo(this.rightClickMenu, "hide_when_no_update",
						this.selfClass(),
						consumes -> consumes.getElement().hideWhenNoUpdate,
						(editorElement, aBoolean) -> editorElement.getElement().hideWhenNoUpdate = aBoolean,
						"aiutd.fancymenu.hide_when_no_update")
				.setTooltipSupplier((menu, entry) -> UITooltip.of(Component.translatable("aiutd.fancymenu.hide_when_no_update.desc")))
				.setStackable(true);

		this.addToggleContextMenuEntryTo(this.rightClickMenu, "open_changelog_on_click",
						this.selfClass(),
						consumes -> consumes.getElement().openChangelogOnClick,
						(editorElement, aBoolean) -> editorElement.getElement().openChangelogOnClick = aBoolean,
						"aiutd.fancymenu.open_changelog_on_click")
				.setTooltipSupplier((menu, entry) -> UITooltip.of(Component.translatable("aiutd.fancymenu.open_changelog_on_click.desc")))
				.setStackable(true);

		this.rightClickMenu.addSeparatorEntry("update_button_separator_2");

		ContextMenu buttonBackgroundMenu = new ContextMenu();
		this.rightClickMenu.addSubMenuEntry("button_background",
						Component.translatable("fancymenu.helper.editor.items.buttons.buttonbackground"), buttonBackgroundMenu)
				.setIcon(ContextMenu.IconFactory.getIcon("image"))
				.setStackable(true);

		ContextMenu setBackMenu = new ContextMenu();
		buttonBackgroundMenu.addSubMenuEntry("set_background",
						Component.translatable("fancymenu.helper.editor.items.buttons.buttonbackground.set"), setBackMenu)
				.setStackable(true);

		this.addImageResourceChooserContextMenuEntryTo(setBackMenu, "normal_background_texture",
				this.selfClass(),
				null,
				consumes -> consumes.getElement().backgroundTextureNormal,
				(editorElement, supplier) -> {
					editorElement.getElement().backgroundTextureNormal = supplier;
					editorElement.getElement().backgroundAnimationNormal = null;
				}, Component.translatable("fancymenu.helper.editor.items.buttons.buttonbackground.normal"),
				true, null, true, true, true);

		this.addImageResourceChooserContextMenuEntryTo(setBackMenu, "hover_background_texture",
				this.selfClass(),
				null,
				consumes -> consumes.getElement().backgroundTextureHover,
				(editorElement, supplier) -> {
					editorElement.getElement().backgroundTextureHover = supplier;
					editorElement.getElement().backgroundAnimationHover = null;
				}, Component.translatable("fancymenu.helper.editor.items.buttons.buttonbackground.hover"),
				true, null, true, true, true);

		this.addImageResourceChooserContextMenuEntryTo(setBackMenu, "inactive_background_texture",
				this.selfClass(),
				null,
				consumes -> consumes.getElement().backgroundTextureInactive,
				(editorElement, supplier) -> {
					editorElement.getElement().backgroundTextureInactive = supplier;
					editorElement.getElement().backgroundAnimationInactive = null;
				}, Component.translatable("fancymenu.helper.editor.items.buttons.buttonbackground.inactive"),
				true, null, true, true, true);

		buttonBackgroundMenu.addSeparatorEntry("separator_after_set_background").setStackable(true);

		this.addToggleContextMenuEntryTo(buttonBackgroundMenu, "restart_animated_on_hover",
						this.selfClass(),
						consumes -> consumes.getElement().restartBackgroundAnimationsOnHover,
						(editorElement, aBoolean) -> editorElement.getElement().restartBackgroundAnimationsOnHover = aBoolean,
						"fancymenu.helper.editor.items.buttons.textures.restart_animated_on_hover")
				.setStackable(true);

		buttonBackgroundMenu.addSeparatorEntry("separator_after_restart_animation_on_hover");

		this.addToggleContextMenuEntryTo(buttonBackgroundMenu, "nine_slice_background",
				this.selfClass(),
				consumes -> consumes.getElement().nineSliceCustomBackground,
				(editorElement, aBoolean) -> editorElement.getElement().nineSliceCustomBackground = aBoolean,
				"fancymenu.helper.editor.items.buttons.textures.nine_slice");

		this.addIntegerInputContextMenuEntryTo(buttonBackgroundMenu, "nine_slice_border_x",
				this.selfClass(),
				consumes -> consumes.getElement().nineSliceBorderX,
				(editorElement, integer) -> editorElement.getElement().nineSliceBorderX = integer,
				Component.translatable("fancymenu.helper.editor.items.buttons.textures.nine_slice.border_x"),
				true, 5, null, null);

		this.addIntegerInputContextMenuEntryTo(buttonBackgroundMenu, "nine_slice_border_y",
				this.selfClass(),
				consumes -> consumes.getElement().nineSliceBorderY,
				(editorElement, integer) -> editorElement.getElement().nineSliceBorderY = integer,
				Component.translatable("fancymenu.helper.editor.items.buttons.textures.nine_slice.border_y"),
				true, 5, null, null);

		this.rightClickMenu.addSeparatorEntry("update_button_separator_3").setStackable(true);

		this.addStringInputContextMenuEntryTo(this.rightClickMenu, "edit_label",
						this.selfClass(),
						consumes -> consumes.element.label,
						(element1, s) -> element1.element.label = s,
						null, false, true, Component.translatable("fancymenu.editor.items.button.editlabel"),
						true, null, null, null)
				.setStackable(true)
				.setIcon(ContextMenu.IconFactory.getIcon("text"));

		this.addStringInputContextMenuEntryTo(this.rightClickMenu, "edit_hover_label",
						this.selfClass(),
						consumes -> consumes.element.hoverLabel,
						(element1, s) -> element1.element.hoverLabel = s,
						null, false, true, Component.translatable("fancymenu.editor.items.button.hoverlabel"),
						true, null, null, null)
				.setStackable(true)
				.setIcon(ContextMenu.IconFactory.getIcon("text"));

		this.rightClickMenu.addSeparatorEntry("update_button_separator_4").setStackable(true);

		this.addAudioResourceChooserContextMenuEntryTo(this.rightClickMenu, "hover_sound",
						this.selfClass(),
						null,
						consumes -> consumes.getElement().hoverSound,
						(editorElement, supplier) -> editorElement.getElement().hoverSound = supplier,
						Component.translatable("fancymenu.editor.items.button.hoversound"),
						true, null, true, true, true)
				.setIcon(ContextMenu.IconFactory.getIcon("sound"));

		this.addAudioResourceChooserContextMenuEntryTo(this.rightClickMenu, "click_sound",
						this.selfClass(),
						null,
						consumes -> consumes.getElement().clickSound,
						(editorElement, supplier) -> editorElement.getElement().clickSound = supplier,
						Component.translatable("fancymenu.editor.items.button.clicksound"),
						true, null, true, true, true)
				.setIcon(ContextMenu.IconFactory.getIcon("sound"));

		this.rightClickMenu.addSeparatorEntry("update_button_separator_5").setStackable(true);

		this.addGenericStringInputContextMenuEntryTo(this.rightClickMenu, "edit_tooltip",
						consumes -> (consumes instanceof UpdateNoticeEditorElement),
						consumes -> {
							String t = consumes.element.tooltip;
							if (t != null) t = t.replace("%n%", "\n");
							return t;
						},
						(element1, s) -> {
							if (s != null) {
								s = s.replace("\n", "%n%");
							}
							element1.element.tooltip = s;
						},
						null, true, true, Component.translatable("fancymenu.editor.items.button.btndescription"),
						true, null, TextValidators.NO_EMPTY_STRING_TEXT_VALIDATOR, null)
				.setStackable(true)
				.setTooltipSupplier((menu, entry) -> UITooltip.of(LocalizationUtils.splitLocalizedLines("fancymenu.editor.items.button.btndescription.desc")))
				.setIcon(ContextMenu.IconFactory.getIcon("talk"));

		this.rightClickMenu.addSeparatorEntry("separator_before_navigatable");

		this.addToggleContextMenuEntryTo(this.rightClickMenu, "toggle_navigatable",
						this.selfClass(),
						consumes -> consumes.getElement().navigatable,
						(editorElement, aBoolean) -> editorElement.getElement().navigatable = aBoolean,
						"fancymenu.elements.widgets.generic.navigatable")
				.setTooltipSupplier((menu, entry) -> UITooltip.of(LocalizationUtils.splitLocalizedLines("fancymenu.elements.widgets.generic.navigatable.desc")));
	}

	protected UpdateNoticeElement getElement() {
		return this.element;
	}
}
//?}
