package dev.vesper.AIUTD.config;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.AutoGen;
import dev.isxander.yacl3.config.v2.api.autogen.Boolean;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.isxander.yacl3.platform.YACLPlatform;
import net.minecraft.resources.Identifier;

public class EndUserConfig {
    public static ConfigClassHandler<EndUserConfig> USERCONFIG = ConfigClassHandler.createBuilder(EndUserConfig.class)
            .id(Identifier.fromNamespaceAndPath("aiutd-enduser", "enduserconfig"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(YACLPlatform.getConfigDir().resolve("aiutd-enduser.json5"))
                    .setJson5(true)
                    .build())
            .build();

    @AutoGen(category = "main")
    @Boolean
    @SerialEntry
    public static boolean shouldIgnore = false;
    @SerialEntry
    public static String versionCache = "0.0.0";
}
