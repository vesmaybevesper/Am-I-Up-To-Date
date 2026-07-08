package dev.vesper.AIUTD.config;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.AutoGen;
import dev.isxander.yacl3.config.v2.api.autogen.EnumCycler;
import dev.isxander.yacl3.config.v2.api.autogen.StringField;
import dev.isxander.yacl3.platform.YACLPlatform;
import dev.vesper.FastJSONForYACL.common.serializer.FastJsonConfigSerializerBuilder;
import net.minecraft.client.gui.screens.Screen;
//? >=1.21.11 {
import net.minecraft.resources.Identifier;
//?}
//? <1.21.11 {
/*import net.minecraft.resources.ResourceLocation;
*///?}

public class Config {
    //? <1.21.11 && !1.20.1 {
    /*public static ConfigClassHandler<Config> HANDLER = ConfigClassHandler.createBuilder(Config.class)
            .id(ResourceLocation.fromNamespaceAndPath("aiutd", "config"))
            .serializer(config -> FastJsonConfigSerializerBuilder.create(config)
                    .setPath(YACLPlatform.getConfigDir().resolve("aiutd.json"))
                    .build())
            .build();
    *///?}
    //? 1.20.1 {
    /*public static ConfigClassHandler<Config> HANDLER = ConfigClassHandler.createBuilder(Config.class)
            .id(ResourceLocation.tryBuild("aiutd", "config"))
            .serializer(config -> FastJsonConfigSerializerBuilder.create(config)
                    .setPath(YACLPlatform.getConfigDir().resolve("aiutd.json"))
                    .build())
            .build();
    *///?}
//? >= 1.21.11 {
    public static ConfigClassHandler<Config> HANDLER = ConfigClassHandler.createBuilder(Config.class)
            .id(Identifier.fromNamespaceAndPath("aiutd", "config"))
            .serializer(config -> FastJsonConfigSerializerBuilder.create(config)
                    .setPath(YACLPlatform.getConfigDir().resolve("aiutd.json"))
                    .build())
            .build();
//?}
    public static Screen config(Screen parent){
        return HANDLER.generateGui().generateScreen(parent);
    }

    public enum LoaderEnum {FABRIC, QUILT, NEOFORGE}

    public enum ColorEnum {BLACK, DARK_BLUE, DARK_GREEN, DARK_AQUA, DARK_RED, DARK_PURPLE, GOLD, GRAY, DARK_GRAY, BLUE, GREEN, AQUA, RED, LIGHT_PURPLE, YELLOW, WHITE}

    // Main Settings
    @AutoGen(category = "Main")
    @dev.isxander.yacl3.config.v2.api.autogen.Boolean(formatter = dev.isxander.yacl3.config.v2.api.autogen.Boolean.Formatter.ON_OFF, colored = true)
    @SerialEntry
    public static boolean menuAlert = true;
    @AutoGen(category = "Main")
    @dev.isxander.yacl3.config.v2.api.autogen.Boolean(formatter = dev.isxander.yacl3.config.v2.api.autogen.Boolean.Formatter.ON_OFF, colored = true)
    @SerialEntry
    public static boolean chatAlert = true;
    @AutoGen(category = "Main")
    @StringField
    @SerialEntry
    public static String localVersion = "1.0.0";
    @AutoGen(category = "Main")
    @StringField
    @SerialEntry
    public static String modpackId = "";

    // Optional Settings
    @AutoGen(category = "Optional")
    @dev.isxander.yacl3.config.v2.api.autogen.Boolean(formatter = dev.isxander.yacl3.config.v2.api.autogen.Boolean.Formatter.ON_OFF, colored = true)
    @SerialEntry
    public static boolean multiVersion = false;
    @AutoGen(category = "Optional")
    @dev.isxander.yacl3.config.v2.api.autogen.Boolean(formatter = dev.isxander.yacl3.config.v2.api.autogen.Boolean.Formatter.ON_OFF, colored = true)
    @SerialEntry
    public static boolean multiLoaderBool = false;
    @AutoGen(category = "Optional")
    @EnumCycler
    @SerialEntry
    public static LoaderEnum multiLoader = LoaderEnum.FABRIC;
    @AutoGen(category = "Optional")
    @dev.isxander.yacl3.config.v2.api.autogen.Boolean(formatter = dev.isxander.yacl3.config.v2.api.autogen.Boolean.Formatter.ON_OFF, colored = true)
    @SerialEntry
    public static boolean useModpackName = false;
    @AutoGen(category = "Optional")
    @StringField
    @SerialEntry
    public static String modpackName = "Default";
    @AutoGen(category = "Optional")
    @dev.isxander.yacl3.config.v2.api.autogen.Boolean(formatter = dev.isxander.yacl3.config.v2.api.autogen.Boolean.Formatter.ON_OFF, colored = true)
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
    @dev.isxander.yacl3.config.v2.api.autogen.Boolean(formatter = dev.isxander.yacl3.config.v2.api.autogen.Boolean.Formatter.ON_OFF, colored = true)
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