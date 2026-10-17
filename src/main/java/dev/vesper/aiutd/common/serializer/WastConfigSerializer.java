package dev.vesper.aiutd.common.serializer;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.ConfigSerializer;

public class WastConfigSerializer<T> extends ConfigSerializer<T> {

	public WastConfigSerializer(ConfigClassHandler<T> config) {
		super(config);
	}

	@Override
	public void save() {

	}
}
