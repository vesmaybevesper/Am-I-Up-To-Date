package dev.vesper.aiutd.common;

import dev.vesper.aiutd.AIUTD;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class ChangelogOpeners {

	/*public static void rinku(){
		//? <26.2{
		Minecraft.getInstance().setScreen(new RinkuWindow(Component.literal("Modpack Changelog")));
		//?} >=26.2{
		*//*Minecraft.getInstance().gui.setScreen(new RinkuWindow(Component.literal("Modpack Changelog")));
		*//*//?}
	}*/

	public static void browser(){
		try {
			URI url = new URI(AIUTD.changelogLink);
			// Check if the browser can be opened
			if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
				Desktop.getDesktop().browse(url);
			} else {
				// fallback link opening logic to try again
				String os = System.getProperty("os.name").toLowerCase();
				try {
					switch (os){
						case "win" -> Runtime.getRuntime().exec(new String[]{"rundll32", "url.dll,FileProtocolHandler", AIUTD.changelogLink});
						case "mac" -> Runtime.getRuntime().exec(new String[]{"open", AIUTD.changelogLink});
						case "nix", "nux" -> Runtime.getRuntime().exec(new String[]{"xdg-open", AIUTD.changelogLink});
						default -> AIUTD.LOG.error("Your OS is currently unsupported for link opening, please open an issue on the AIUTD GitHub with information on it");
					}
				} catch (IOException e) {
					AIUTD.LOG.info(String.valueOf(e));
				}
			}
		} catch (Exception e) {
			AIUTD.LOG.error(String.valueOf(e));
		}
	}

	public static void popUp(){

	}

}
