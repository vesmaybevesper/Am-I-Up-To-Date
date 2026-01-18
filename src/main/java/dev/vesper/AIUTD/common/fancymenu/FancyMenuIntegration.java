/*
package dev.vesper.AIUTD.common.fancymenu;

import de.keksuccino.fancymenu.customization.element.ElementRegistry;
import dev.vesper.AIUTD.AIUTD;

public class FancyMenuIntegration {
    private static boolean initalized = false;

    public static void init() {
        if(initalized) return;

        if (AIUTD.isModLoaded("fancymenu")) {
            try {
                registerElements();
                AIUTD.LOG.info("FancyMenu integration initialized");
                initalized = true;
            } catch (Exception e) {
                AIUTD.LOG.error("Failed to initialize FancyMenu integration", e);
            }
        }
    }

    private static void registerElements() {
        UpdateNoticeElementBuilder builder = new UpdateNoticeElementBuilder();
        ElementRegistry.register(builder);
        AIUTD.LOG.info("Registered AIUTD Update Button element with FancyMenu");
    }


}
*/
