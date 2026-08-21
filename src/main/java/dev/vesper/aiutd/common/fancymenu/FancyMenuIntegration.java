package dev.vesper.aiutd.common.fancymenu;
//? 1.20.1 && !forge || 1.21.1 || >= 1.21.11{
import de.keksuccino.fancymenu.customization.element.ElementRegistry;
import dev.vesper.aiutd.AIUTD;

public class FancyMenuIntegration {
	private static boolean initialized = false;

	public static void init() {
		if(initialized) return;

		if (AIUTD.isModLoaded("fancymenu")) {
			try {
				registerElements();
				initialized = true;
			} catch (Exception ignored) {}
		}
	}

	private static void registerElements() {
		UpdateNoticeElementBuilder builder = new UpdateNoticeElementBuilder();
		ElementRegistry.register(builder);
		AIUTD.LOG.info("Registered AIUTD Update Button element with FancyMenu");
	}
}
//?}
