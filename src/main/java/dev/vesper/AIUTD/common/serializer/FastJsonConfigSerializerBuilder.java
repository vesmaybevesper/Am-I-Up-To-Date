/*
package dev.vesper.AIUTD.common.serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;

import java.nio.file.Path;
import java.util.function.UnaryOperator;

public interface FastJsonConfigSerializerBuilder<T> {
    static <T> FastJsonConfigSerializerBuilder<T> create(ConfigClassHandler<T> config){
        return new FastJsonConfigSerializer.Builder(config);
    }

    FastJsonConfigSerializerBuilder<T> setPath(Path var1);

    FastJsonConfigSerializerBuilder<T> overrideGsonBuilder(GsonBuilder var1);

    FastJsonConfigSerializerBuilder<T> overrideGsonBuilder(Gson var1);

    FastJsonConfigSerializerBuilder<T> appendGsonBuilder(UnaryOperator<GsonBuilder> var1);

    FastJsonConfigSerializerBuilder<T> setJson5(boolean var1);

    FastJsonConfigSerializerBuilder<T> build();
}
*/
