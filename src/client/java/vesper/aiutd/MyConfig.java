package vesper.aiutd;

import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;

import java.nio.file.Files;
import java.nio.file.Path;

public class MyConfig {
    public static ConfigClassHandler<MyConfig> HANDLER;
// create strings
    @SerialEntry
    public String localVersion;

    @SerialEntry
    public String versionAPI;

    @SerialEntry
    public String changelogLink;

    static {
        //crate config
        Path configPath = FabricLoader.getInstance().getConfigDir().resolve("aiutd-mod-config.json5");

        HANDLER = ConfigClassHandler.createBuilder(MyConfig.class)
                .id(Identifier.of("aiutd:aiutd-mod-config"))
                .serializer(config -> GsonConfigSerializerBuilder.create(config)
                        .setPath(configPath)
                        .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
                        .setJson5(true)
                        .build()).build();
    }


}
