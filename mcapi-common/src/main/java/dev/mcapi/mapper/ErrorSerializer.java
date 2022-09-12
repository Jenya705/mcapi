package dev.mcapi.mapper;

import dev.mcapi.data.ErrorData;

public interface ErrorSerializer<T extends Throwable> {

    ErrorData serialize(T value);

}
