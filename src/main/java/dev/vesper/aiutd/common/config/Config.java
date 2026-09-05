package dev.vesper.aiutd.common.config;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.AutoGen;
import dev.isxander.yacl3.config.v2.api.autogen.Boolean;
import dev.isxander.yacl3.config.v2.api.autogen.EnumCycler;
import dev.isxander.yacl3.config.v2.api.autogen.LongField;
import dev.isxander.yacl3.config.v2.api.autogen.StringField;
import dev.isxander.yacl3.platform.YACLPlatform;
import dev.vesper.fastjson4yacl.Serializers.FastJsonJson.FastJsonConfigSerializerBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.Identifier;

public class Config {
	//? >=1.21.1{
	public static final ConfigClassHandler<Config> HANDLER = ConfigClassHandler.createBuilder(Config.class)
			.id(Identifier.fromNamespaceAndPath("aiutd", "config"))
			.serializer(config -> FastJsonConfigSerializerBuilder.create(config)
					.setPath(YACLPlatform.getConfigDir().resolve("aiutd.json"))
					.build())
			.build();
	//?} 1.20.1{
	/*public static final ConfigClassHandler<Config> HANDLER = ConfigClassHandler.createBuilder(Config.class)
			.id(new Identifier("aiutd", "config"))
			.serializer(config -> FastJsonConfigSerializerBuilder.create(config)
					.setPath(YACLPlatform.getConfigDir().resolve("aiutd.json"))
					.build())
			.build();
	*///?}

	public static boolean showLoaderOpts = false;

	public static Screen config(Screen parent){
		return HANDLER.generateGui().generateScreen(parent);
	}

	public enum LoaderEnum {FABRIC, QUILT, NEOFORGE}

	public enum ColorEnum {BLACK, DARK_BLUE, DARK_GREEN, DARK_AQUA, DARK_RED, DARK_PURPLE, GOLD, GRAY, DARK_GRAY, BLUE, GREEN, AQUA, RED, LIGHT_PURPLE, YELLOW, WHITE}

	public enum LinkMethod {BROWSER, RINKU}

	// Main Settings
	@AutoGen(category = "Main")
	@StringField
	@SerialEntry
	public static String modpackId = "";
	@AutoGen(category = "Main")
	@StringField
	@SerialEntry
	public static String localVersion = "1.0.0";
	//@AutoGen(category = "Main")
	@Boolean(formatter = Boolean.Formatter.ON_OFF, colored = true)
	@SerialEntry
	public static boolean showToast = true;
	@AutoGen(category = "Main")
	@Boolean(formatter = Boolean.Formatter.ON_OFF, colored = true)
	@SerialEntry
	public static boolean menuAlert = true;
	@AutoGen(category = "Main")
	@Boolean(formatter = Boolean.Formatter.ON_OFF, colored = true)
	@SerialEntry
	public static boolean chatAlert = true;
	//? if 1.20.1 || 1.21.1 || 1.21.11 || >= 26.1 && !26.3 {
	@AutoGen(category = "Main")
	@EnumCycler
	@SerialEntry
	//?}
	public static LinkMethod openingMethod = LinkMethod.BROWSER;

	// Customization Settings
	@AutoGen(category = "Optional")
	@Boolean(formatter =  Boolean.Formatter.ON_OFF, colored = true)
	@SerialEntry
	public static boolean bigButton = true;
	//@AutoGen(category = "Optional")
	@Boolean(formatter =  Boolean.Formatter.ON_OFF, colored = true)
	@SerialEntry
	public static boolean customToastMessage = false;
	//@AutoGen(category = "Optional")
	@StringField
	@SerialEntry
	public static String toastMessage = "";
	//@AutoGen(category = "Optional")
	@Boolean(formatter =  Boolean.Formatter.ON_OFF, colored = true)
	@SerialEntry
	public static boolean customToastTitle = false;
	//@AutoGen(category = "Optional")
	@StringField
	@SerialEntry
	public static String toastTitle = "";
	//@AutoGen(category = "Optional")
	@LongField
	@SerialEntry
	public static long toastDisplayTime = 5000L;
	@AutoGen(category = "Optional")
	@Boolean(formatter = Boolean.Formatter.ON_OFF, colored = true)
	@SerialEntry
	public static boolean multiVersion = false;
	@AutoGen(category = "Optional")
	@Boolean(formatter = Boolean.Formatter.ON_OFF, colored = true)
	@SerialEntry
	public static boolean multiLoaderBool = false;
	@AutoGen(category = "Optional")
	@Boolean(formatter = Boolean.Formatter.ON_OFF, colored = true)
	@SerialEntry
	public static boolean useModpackName = false;
	@AutoGen(category = "Optional")
	@StringField
	@SerialEntry
	public static String modpackName = "Default";
	@AutoGen(category = "Optional")
	@Boolean(formatter = Boolean.Formatter.ON_OFF, colored = true)
	@SerialEntry
	public static boolean useCustomMessage = false;
	@AutoGen(category = "Optional")
	@StringField
	@SerialEntry
	public static String customMessage = "This is a custom message!";
	@AutoGen(category = "Optional")
	@EnumCycler
	@SerialEntry
	public static ColorEnum messageColor = ColorEnum.WHITE;
	@AutoGen(category = "Optional")
	@Boolean(formatter = Boolean.Formatter.ON_OFF, colored = true)
	@SerialEntry
	public static boolean linkChangelog = false;
	@AutoGen(category = "Optional")
	@EnumCycler
	@SerialEntry
	public static ColorEnum changelogColor = ColorEnum.RED;
	@AutoGen(category = "Optional")
	@EnumCycler
	@SerialEntry
	public static ColorEnum ignoreColor = ColorEnum.GRAY;
}
