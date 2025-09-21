package dev.vesper.AIUTD.config;

import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.isxander.yacl3.impl.controller.EnumDropdownControllerBuilderImpl;
import dev.isxander.yacl3.impl.controller.StringControllerBuilderImpl;
import dev.isxander.yacl3.impl.controller.TickBoxControllerBuilderImpl;
import dev.isxander.yacl3.platform.YACLPlatform;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class Config {
    public static ConfigClassHandler<Config> HANDLER = ConfigClassHandler.createBuilder(Config.class)
            .id(ResourceLocation.fromNamespaceAndPath("aiutd", "config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(YACLPlatform.getConfigDir().resolve("aiutd.json5"))
                    .setJson5(true)
                    .build())
            .build();

    public enum LoaderEnum {FABRIC, QUILT, NEOFORGE}

    // Main Settings
    @SerialEntry
    public static boolean menuAlert = true;
    @SerialEntry
    public static boolean chatAlert = true;
    @SerialEntry
    public static String localVersion = "1.0.0";
    @SerialEntry
    public static String versionAPI = "https://api.modrinth.com/v2/project/<id>/version";
    @SerialEntry
    public static String changelogLink = "https://modrinth.com/modpack/<modpack-URL>/changelog";
    // i can delete this if i do my logic correctly this time
    @SerialEntry
    public static int maxChecks = 5;

    // Optional Settings
    @SerialEntry
    public static boolean multiVersion = false;
    @SerialEntry
    public static boolean multiLoaderBool = false;
    @SerialEntry
    public static LoaderEnum multiLoader = LoaderEnum.FABRIC;
    @SerialEntry
    public static boolean useModpackName = false;
    @SerialEntry
    public static String modpackName = "Default";
    @SerialEntry
    public static boolean useCustomMessage = false;
    @SerialEntry
    public static String customMessage = "This is a custom message!";
    @SerialEntry
    public static boolean linkChangelog = false;
    
    // Cache to prevent crashing. May relocate
    public static String versionCache = "0.0.0";

    public static Screen configGui(Screen parent){
        return YetAnotherConfigLib.createBuilder()
                .title(Component.translatable("aiutd.title"))
                .category(ConfigCategory.createBuilder()
                        .name(Component.translatable("aiutd.main"))

                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("aiutd.menuAlert"))
                                .description(OptionDescription.of(Component.translatable("aiutd.menuAlertDesc")))
                                .binding(Config.menuAlert, () -> Config.menuAlert, newVal -> Config.menuAlert = newVal)
                                .controller(TickBoxControllerBuilderImpl::new)
                                .build())

                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("aiutd.chatAlert"))
                                .description(OptionDescription.of(Component.translatable("aiutd.chatAlertDesc")))
                                .binding(Config.chatAlert, () -> Config.chatAlert, newVal -> Config.chatAlert = newVal)
                                .controller(TickBoxControllerBuilderImpl::new)
                                .build())

                        .option(Option.<String>createBuilder()
                                .name(Component.translatable("aiutd.localVersion"))
                                .description(OptionDescription.of(Component.translatable("aiutd.localVersionDesc")))
                                .binding(Config.localVersion, () -> Config.localVersion, newVal -> Config.localVersion = newVal)
                                .controller(StringControllerBuilderImpl::new)
                                .build())

                        .option(Option.<String>createBuilder()
                                .name(Component.translatable("aiutd.APIversion"))
                                .description(OptionDescription.of(Component.translatable("aiutd.APIVersionDesc")))
                                .binding(Config.versionAPI, () -> Config.versionAPI, newVal -> Config.versionAPI = newVal)
                                .controller(StringControllerBuilderImpl::new)
                                .build())

                        .option(Option.<String>createBuilder()
                                .name(Component.translatable("aiutd.changelogLink"))
                                .description(OptionDescription.of(Component.translatable("aiutd.changelogLink")))
                                .binding(Config.changelogLink, () -> Config.changelogLink, newVal -> Config.changelogLink = newVal)
                                .controller(StringControllerBuilderImpl::new)
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Component.translatable("auitd.optional"))

                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("aiutd.multiVersion"))
                                .description(OptionDescription.of(Component.translatable("aiutd.multiVersionDesc")))
                                .binding(Config.multiVersion, () -> Config.multiVersion, newVal -> Config.multiVersion = newVal)
                                .controller(TickBoxControllerBuilderImpl::new)
                                .build())

                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("aiutd.multiloaderBool"))
                                .description(OptionDescription.of(Component.translatable("aiutd.multiloaderBool")))
                                .binding(Config.multiLoaderBool, () -> Config.multiLoaderBool, newVal -> Config.multiLoaderBool = newVal)
                                .controller(TickBoxControllerBuilderImpl::new)
                                .build())

                        .option(Option.<LoaderEnum>createBuilder()
                                .name(Component.translatable("aiutd.multiloaderEnum"))
                                .description(OptionDescription.of(Component.translatable("aiutd.multiloaderEnum")))
                                .binding(Config.multiLoader, () -> Config.multiLoader, newVal -> Config.multiLoader = newVal)
                                .controller(EnumDropdownControllerBuilderImpl::new)
                                .build())

                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("aiutd.usePackName"))
                                .description(OptionDescription.of(Component.translatable("aiutd.usePackName")))
                                .binding(Config.useModpackName, () -> Config.useModpackName, newVal -> Config.useModpackName = newVal)
                                .controller(TickBoxControllerBuilderImpl::new)
                                .build())

                        .option(Option.<String>createBuilder()
                                .name(Component.translatable("aiutd.modpackName"))
                                .description(OptionDescription.of(Component.translatable("aiutd.modpackName")))
                                .binding(Config.modpackName, () -> Config.modpackName, newVal -> Config.modpackName = newVal)
                                .controller(StringControllerBuilderImpl::new)
                                .build())

                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("aiutd.useCustomMsg"))
                                .description(OptionDescription.of(Component.translatable("aiutd.useCustomMsg")))
                                .binding(Config.useCustomMessage, () -> Config.useCustomMessage, newVal -> Config.useCustomMessage = newVal)
                                .controller(TickBoxControllerBuilderImpl::new)
                                .build())

                        .option(Option.<String>createBuilder()
                                .name(Component.translatable("aiutd.customMsg"))
                                .description(OptionDescription.of(Component.translatable("aiutd.customMsg")))
                                .binding(Config.customMessage, () -> Config.customMessage, newVal -> Config.customMessage = newVal)
                                .controller(StringControllerBuilderImpl::new)
                                .build())

                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("aiutd.linkChangelog"))
                                .description(OptionDescription.of(Component.translatable("aiutd.linkChangelog")))
                                .binding(Config.linkChangelog, () -> Config.linkChangelog, newVal -> Config.linkChangelog = newVal)
                                .controller(TickBoxControllerBuilderImpl::new)
                                .build())
                        .build())
                .build()
                .generateScreen(parent);
    }
}
