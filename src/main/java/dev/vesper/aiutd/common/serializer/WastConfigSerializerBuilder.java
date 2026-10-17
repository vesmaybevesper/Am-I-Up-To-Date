package dev.vesper.aiutd.common.serializer;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;

public interface WastConfigSerializerBuilder<T> {
	static <T> WastConfigSerializerBuilder<T> create(ConfigClassHandler<T> config){
		return new WastConfigSerializer.Builder<>(config);
	}
}
