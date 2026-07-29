package dev.vesper.aiutd;

import dev.vesper.aiutd.common.ChatMessages;
import dev.vesper.aiutd.common.UpdateChecker;
import dev.vesper.aiutd.common.Variables;
import dev.vesper.aiutd.common.config.Config;
import dev.vesper.aiutd.common.config.EndUserConfig;
import dev.vesper.aiutd.common.fancymenu.FancyMenuIntegration;
import dev.vesper.aiutd.platform.Platform;

import net.minecraft.resources.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

import static dev.vesper.aiutd.common.config.Config.localVersion;

//? fabric {
import dev.vesper.aiutd.platform.fabric.FabricPlatform;
import net.fabricmc.loader.api.FabricLoader;
//?} neoforge {
/*import dev.vesper.aiutd.platform.neoforge.NeoforgePlatform;
import net.neoforged.fml.ModList;
import net.neoforged.bus.api.SubscribeEvent;
 *///?} forge {
/*import dev.vesper.aiutd.platform.forge.ForgePlatform;
import net.minecraftforge.fml.ModList;
 *///?}

@SuppressWarnings("LoggingSimilarMessage")
public class AIUTD {

	public static final String MOD_ID = /*$ mod_id*/ "aiutd";
	public static final String MOD_VERSION = /*$ mod_version*/ "2.5.3";
	public static final String MOD_FRIENDLY_NAME = /*$ mod_name*/ "Am I Up To Date?";
	public static final Logger LOG = LoggerFactory.getLogger(MOD_ID);
	public static boolean hasNotified = false;
	public static String modrinthApiLink;
	public static String changelogLink;

	private static final Platform PLATFORM = createPlatformInstance();

	public static void onInitialize() {
		LOG.info("Initializing {} on {}", MOD_ID, AIUTD.xplat().loader());
		LOG.debug("{}: { version: {}; friendly_name: {} }", MOD_ID, MOD_VERSION, MOD_FRIENDLY_NAME);
	}

	public static void onInitializeClient() {
		LOG.info("Initializing {} Client on {}", MOD_ID, AIUTD.xplat().loader());
		LOG.debug("{}: { version: {}; friendly_name: {} }", MOD_ID, MOD_VERSION, MOD_FRIENDLY_NAME);
		Config.HANDLER.load();
		changelogLink = "https://modrinth.com/modpack/" + Config.modpackId + "/changelog".trim();
		modrinthApiLink = "https://api.modrinth.com/v2/project/" + Config.modpackId + "/version?include_changelog=false".trim();

		Config.HANDLER.load();
		EndUserConfig.USERCONFIG.load();
		try {
			if (!UpdateChecker.hasChecked){
				if (Config.modpackId.isEmpty()) {
					UpdateChecker.needUpdate = false;
					return;
				}
				UpdateChecker.needUpdate = !Objects.equals(localVersion, UpdateChecker.getVersionNumber());
				UpdateChecker.hasChecked = true;
			}
		} catch (URISyntaxException | IOException ignored) {}
		Variables.setAll();
		//? fabric{
		ChatMessages.sendChatMessage();
		//?}
		if (isModLoaded("fancymenu")) {
			FancyMenuIntegration.init();
		}
	}

	static Platform xplat() {
		return PLATFORM;
	}

	private static Platform createPlatformInstance() {
		//? fabric {
		return new FabricPlatform();
		//?} neoforge {
		/*return new NeoforgePlatform();
		 *///?} forge {
		/*return new ForgePlatform();
		 *///?}
	}

	private static Identifier id(String path) {
		//? 1.20.1{
		/*return Identifier.tryBuild(MOD_ID, path);
		*///?} > 1.20.1 {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
		 //?} <= 1.19.2 {
		/*return new Identifier(MOD_ID, path);
		*///?}
	}

	private static Identifier id(String namespace, String path) {
		//? 1.20.1 {
		/*return Identifier.tryBuild(namespace, path);
		*///?} > 1.20.1 {
		return Identifier.fromNamespaceAndPath(namespace, path);
		 //?} <= 1.19.2 {
		/*return new Identifier(namespace, path);
		*///?}
	}

	public static boolean isModLoaded(String modid) {
		//? fabric {
		 return FabricLoader.getInstance().isModLoaded(modid);
		//?} neoforge {
		/*return ModList.get().isLoaded(modid);
		*///?} forge {
		/*return ModList.get().isLoaded(modid);
		*///?}
	}
}
