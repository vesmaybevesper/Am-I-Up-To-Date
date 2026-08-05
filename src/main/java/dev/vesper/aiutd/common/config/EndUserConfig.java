package dev.vesper.aiutd.common.config;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.AutoGen;
import dev.isxander.yacl3.config.v2.api.autogen.Boolean;
import dev.isxander.yacl3.platform.YACLPlatform;
import dev.vesper.fastjson4yacl.Serializers.FastJsonJson.FastJsonConfigSerializerBuilder;
import net.minecraft.resources.Identifier;

public class EndUserConfig {
	//? >=1.21.1{
	public static final ConfigClassHandler<EndUserConfig> USERCONFIG = ConfigClassHandler.createBuilder(EndUserConfig.class)
			.id(Identifier.fromNamespaceAndPath("aiutd-enduser", "enduserconfig"))
			.serializer(config -> FastJsonConfigSerializerBuilder.create(config)
					.setPath(YACLPlatform.getConfigDir().resolve("aiutd-enduser.json"))
					.build())
			.build();
	//?} 1.20.1{
	/*public static final ConfigClassHandler<EndUserConfig> USERCONFIG = ConfigClassHandler.createBuilder(EndUserConfig.class)
			.id(Identifier.tryBuild("aiutd-enduser", "enduserconfig"))
			.serializer(config -> FastJsonConfigSerializerBuilder.create(config)
					.setPath(YACLPlatform.getConfigDir().resolve("aiutd-enduser.json"))
					.build())
			.build();
	*///?}

	@AutoGen(category = "main")
	@Boolean
	@SerialEntry
	public static boolean shouldIgnore = false;
	@SerialEntry
	public static String versionCache = "0.0.0";
}
