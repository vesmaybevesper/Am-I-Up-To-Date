package vesper.aiutd;

import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;

public class MyConfig {
    public static ConfigClassHandler<MyConfig> HANDLER = ConfigClassHandler.createBuilder(MyConfig.class)
            .id(Identifier.of("aiutd:aiutd-mod-config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                .setPath(FabricLoader.getInstance().getConfigDir().resolve("aiutd-mod-config.json5"))
                .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
                .setJson5(true)
                .build()).build();

    @SerialEntry
    public String localVersion = "0.0.0";

    @SerialEntry
    public String verionAPI = "https://api.modrinth.com/v2/project/<id>/version";

    @SerialEntry
    public String changelogLink = "https://modrinth.com/modpack/<modpack URL>/changelog";
}
