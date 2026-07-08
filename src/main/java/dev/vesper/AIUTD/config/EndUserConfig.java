package dev.vesper.AIUTD.config;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.AutoGen;
import dev.isxander.yacl3.config.v2.api.autogen.Boolean;
import dev.vesper.FastJSONForYACL.common.serializer.FastJsonConfigSerializerBuilder;
import dev.isxander.yacl3.platform.YACLPlatform;
//? >=1.21.11 {
import net.minecraft.resources.Identifier;
//?}
//? <1.21.11 {
/*import net.minecraft.resources.ResourceLocation;
 *///?}

public class EndUserConfig {
    //? <1.21.11 && !1.20.1 {
    /*public static ConfigClassHandler<EndUserConfig> USERCONFIG = ConfigClassHandler.createBuilder(EndUserConfig.class)
            .id(ResourceLocation.fromNamespaceAndPath("aiutd-enduser", "enduserconfig"))
            .serializer(config -> FastJsonConfigSerializerBuilder.create(config)
                    .setPath(YACLPlatform.getConfigDir().resolve("aiutd-enduser.json"))
                    .build())
            .build();
    *///?}
    //? 1.20.1 {
    /*public static ConfigClassHandler<EndUserConfig> USERCONFIG = ConfigClassHandler.createBuilder(EndUserConfig.class)
            .id(ResourceLocation.tryBuild("aiutd-enduser", "enduserconfig"))
            .serializer(config -> FastJsonConfigSerializerBuilder.create(config)
                    .setPath(YACLPlatform.getConfigDir().resolve("aiutd-enduser.json"))
                    .build())
            .build();
    *///?}
//? >= 1.21.11 {
    public static ConfigClassHandler<EndUserConfig> USERCONFIG = ConfigClassHandler.createBuilder(EndUserConfig.class)
            .id(Identifier.fromNamespaceAndPath("aiutd-enduser", "enduserconfig"))
            .serializer(config -> FastJsonConfigSerializerBuilder.create(config)
                    .setPath(YACLPlatform.getConfigDir().resolve("aiutd-enduser.json"))
                    .build())
            .build();
    //?}

    @AutoGen(category = "main")
    @Boolean
    @SerialEntry
    public static boolean shouldIgnore = false;
    @SerialEntry
    public static String versionCache = "0.0.0";
}
