package dev.mcapi.data;

import org.immutables.value.Value;

@Value.Immutable
public interface ErrorData {

    static ImmutableErrorData.Builder builder() {
        return ImmutableErrorData.builder();
    }

    /**
     * Returns namespace of this error. It could be: mcapi, internal (or java error) and so on
     *
     * @return Namespace
     */
    String namespace();

    /**
     * Returns name of this error
     *
     * @return Name
     */
    String name();

    /**
     * Returns message of this error
     *
     * @return Message
     */
    String message();

}
