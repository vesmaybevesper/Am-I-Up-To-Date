package dev.vesper.aiutd.common;

import dev.vesper.aiutd.AIUTD;
//? if 1.20.1
//import dev.vesper.aiutd.common.rinku.RinkuWindow201;
//? if 1.21.1 && fabric
//import dev.vesper.aiutd.common.rinku.RinkuWindow211;
//? if 1.21.11 && fabric
//import dev.vesper.aiutd.common.rinku.RinkuWindow11;
//? if >=26.1 && ! 26.3
import dev.vesper.aiutd.common.rinku.RinkuWindow26;
import dev.vesper.aiutd.common.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class ChangelogOpeners {

	public static void rinku(){
		//? 1.20.1 {
			/*Minecraft.getInstance().setScreen(new RinkuWindow201(Component.empty()));
		*///?} 1.21.1 && fabric{
			/*Minecraft.getInstance().setScreen(new RinkuWindow211(Component.empty()));
		*///?} 1.21.11 && fabric{
			/*Minecraft.getInstance().setScreenAndShow(new RinkuWindow11(Component.empty()));
		*///?} >=26.1 && !26.3{
			Minecraft.getInstance().setScreenAndShow(new RinkuWindow26(Component.empty()));
		//?} else {
			/*AIUTD.LOG.error("Rinku window opening should never be called on this version!");
		*///?}
	}

	public static void browser(){
		try {
			URI url = new URI(AIUTD.getChangelogLink());
			// Check if the browser can be opened
			if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
				Desktop.getDesktop().browse(url);
			} else {
				// fallback link opening logic to try again
				String os = System.getProperty("os.name").toLowerCase();
				String osKey = os.contains("win") ? "win"
						: os.contains("mac") ? "mac"
						: (os.contains("nux") || os.contains("nix")) ? "nix"
						: "unsupported";
				try {
					switch (osKey){
						case "win" -> Runtime.getRuntime().exec(new String[]{"rundll32", "url.dll,FileProtocolHandler", AIUTD.getChangelogLink()});
						case "mac" -> Runtime.getRuntime().exec(new String[]{"open", AIUTD.getChangelogLink()});
						// I have a feeling this fallback isn't robust enough the handle all linux distros, but from my experience it works fine with the main open command
						case "nix" -> Runtime.getRuntime().exec(new String[]{"xdg-open", AIUTD.getChangelogLink()});
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

    public static void pick() {
		if (Config.openingMethod == Config.LinkMethod.BROWSER) {
			ChangelogOpeners.browser();
		} else if (Config.openingMethod == Config.LinkMethod.RINKU){
			if (!AIUTD.isModLoaded("rinku")){
				AIUTD.LOG.error("Rinku opening was attempted without Rinku installed, falling back to browser!");
				ChangelogOpeners.browser();
			}
			ChangelogOpeners.rinku();
		}
    }
}
