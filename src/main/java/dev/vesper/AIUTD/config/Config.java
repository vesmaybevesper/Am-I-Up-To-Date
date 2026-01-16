package dev.vesper.AIUTD.config;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.AutoGen;
import dev.isxander.yacl3.config.v2.api.autogen.EnumCycler;
import dev.isxander.yacl3.config.v2.api.autogen.FormatTranslation;
import dev.isxander.yacl3.config.v2.api.autogen.StringField;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.isxander.yacl3.platform.YACLPlatform;
import net.minecraft.client.gui.screens.Screen;
//? >=1.21.11 {
/*import net.minecraft.resources.Identifier;
*///?}
//? <1.21.11 {
import net.minecraft.resources.ResourceLocation;
//?}

public class Config {
    //? <1.21.11 && !1.20.1 {
    public static ConfigClassHandler<Config> HANDLER = ConfigClassHandler.createBuilder(Config.class)
            .id(ResourceLocation.fromNamespaceAndPath("aiutd", "config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(YACLPlatform.getConfigDir().resolve("aiutd.json5"))
                    .setJson5(true)
                    .build())
            .build();
    //?}
    //? 1.20.1 {
    /*public static ConfigClassHandler<Config> HANDLER = ConfigClassHandler.createBuilder(Config.class)
            .id(ResourceLocation.tryBuild("aiutd", "config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(YACLPlatform.getConfigDir().resolve("aiutd.json5"))
                    .setJson5(true)
                    .build())
            .build();
    *///?}

//? >= 1.21.11 {
    /*public static ConfigClassHandler<Config> HANDLER = ConfigClassHandler.createBuilder(Config.class)
            .id(Identifier.fromNamespaceAndPath("aiutd", "config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(YACLPlatform.getConfigDir().resolve("aiutd.json5"))
                    .setJson5(true)
                    .build())
            .build();
*///?}
    public static Screen config(Screen parent){
        return HANDLER.generateGui().generateScreen(parent);
    }

    public enum LoaderEnum {FABRIC, QUILT, NEOFORGE}

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
    public static String versionAPI = "https://api.modrinth.com/v2/project/<id>/version?include_changelog=false";
    @AutoGen(category = "Main")
    @StringField
    @SerialEntry
    public static String changelogLink = "https://modrinth.com/modpack/<modpack-URL>/changelog";
/*
    @AutoGen(category = "Main")
    @FormatTranslation("Times")
    @SerialEntry
    public static int maxChecks = 5;
*/

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
    @dev.isxander.yacl3.config.v2.api.autogen.Boolean(formatter = dev.isxander.yacl3.config.v2.api.autogen.Boolean.Formatter.ON_OFF, colored = true)
    @SerialEntry
    public static boolean linkChangelog = false;
    
    // Cache to prevent crashing. May relocate
    @SerialEntry
    public static String versionCache = "0.0.0";
    @SerialEntry
    public static boolean migrated = false;
}