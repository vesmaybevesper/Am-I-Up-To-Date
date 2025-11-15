package dev.vesper.AIUTD.config;

<<<<<<< Updated upstream
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.AutoGen;
import dev.isxander.yacl3.config.v2.api.autogen.Boolean;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.isxander.yacl3.platform.YACLPlatform;
import net.minecraft.resources.ResourceLocation;

public class EndUserConfig {
    public static ConfigClassHandler<EndUserConfig> USERCONFIG = ConfigClassHandler.createBuilder(EndUserConfig.class)
            .id(ResourceLocation.fromNamespaceAndPath("aiutd-enduser", "enduserconfig"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(YACLPlatform.getConfigDir().resolve("aiutd-enduser.json5"))
                    .setJson5(true)
                    .build())
            .build();

    @AutoGen(category = "main")
    @Boolean
    @SerialEntry
=======
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "aiutd-enduser")
public class EndUserConfig {
    @ConfigEntry.Gui.Tooltip
>>>>>>> Stashed changes
    public static boolean shouldIgnore = false;
}
