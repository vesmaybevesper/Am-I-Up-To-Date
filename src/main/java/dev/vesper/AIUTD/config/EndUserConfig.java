package dev.vesper.AIUTD.config;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.isxander.yacl3.platform.YACLPlatform;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class EndUserConfig {
    public static ConfigClassHandler<Config> HANDLER = ConfigClassHandler.createBuilder(Config.class)
            .id(ResourceLocation.fromNamespaceAndPath("aiutd-client", "config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(YACLPlatform.getConfigDir().resolve("aiutd-client.json5"))
                    .setJson5(true)
                    .build())
            .build();

    @SerialEntry
    public static boolean shouldIgnore = false;
}
