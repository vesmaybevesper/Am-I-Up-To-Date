/*
package dev.vesper.AIUTD.common.fancymenu;

import de.keksuccino.fancymenu.customization.element.AbstractElement;
import de.keksuccino.fancymenu.customization.element.ElementBuilder;
import de.keksuccino.fancymenu.customization.element.ElementStacker;
import de.keksuccino.fancymenu.customization.element.SerializedElement;
import de.keksuccino.fancymenu.customization.element.anchor.ElementAnchorPoints;
import de.keksuccino.fancymenu.customization.element.editor.AbstractEditorElement;
import de.keksuccino.fancymenu.customization.element.elements.button.vanillawidget.VanillaWidgetElement;
import de.keksuccino.fancymenu.customization.layout.editor.LayoutEditorScreen;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MainMenuWidgetBuilder extends ElementBuilder implements ElementStacker<VanillaWidgetElement> {
    public MainMenuWidgetBuilder(@NotNull String uniqueElementIdentifier) {
        super(uniqueElementIdentifier);
    }

    @Override
    public @NotNull AbstractElement buildDefaultInstance() {
       VanillaWidgetElement element = new VanillaWidgetElement(this);
       element.anchorPoint = ElementAnchorPoints.VANILLA;
       return element;
    }

    @Override
    public AbstractElement deserializeElement(@NotNull SerializedElement serializedElement) {
        VanillaWidgetElement element = (VanillaWidgetElement) super.deserializeElement(serializedElement);
        String hidden = serializedElement.getValue("is_hidden");
        if ((hidden != null) && hidden.equalsIgnoreCase("true")){
            element.vanillaButtonHidden = true;
        }

        String
    }

    @Override
    protected SerializedElement serializeElement(@NotNull AbstractElement abstractElement, @NotNull SerializedElement serializedElement) {
        try {

        } catch (){

        }
    }

    @Override
    public @NotNull AbstractEditorElement wrapIntoEditorElement(@NotNull AbstractElement abstractElement, @NotNull LayoutEditorScreen layoutEditorScreen) {
        return null;
    }

    @Override
    public net.minecraft.@NotNull class_2561 getDisplayName(@Nullable AbstractElement abstractElement) {
        return null;
    }

    @Override
    public @Nullable net.minecraft.class_2561[] getDescription(@Nullable AbstractElement abstractElement) {
        return null;
    }

    @Override
    public void stackElements(@NotNull VanillaWidgetElement vanillaWidgetElement, @NotNull VanillaWidgetElement e1) {

    }
}
*/
