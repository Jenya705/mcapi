package dev.mcapi.data;

import org.immutables.value.Value;

@Value.Immutable
public interface ErrorData {

    static ImmutableErrorData.Builder builder() {
        return ImmutableErrorData.builder();
    }

    String getNamespace();

    String getName();

    String getReason();

}
