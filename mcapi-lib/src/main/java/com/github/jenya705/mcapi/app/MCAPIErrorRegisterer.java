package com.github.jenya705.mcapi.app;

import com.github.jenya705.mcapi.ApiError;
import com.github.jenya705.mcapi.util.Triple;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.function.Function;

/**
 * @author Jenya705
 */
@UtilityClass
public class MCAPIErrorRegisterer {

    public Triple<String, Integer, Function<ApiError, RuntimeException>> createBuilder(Class<? extends ApiError> errorClass) throws Exception {
        if (!RuntimeException.class.isAssignableFrom(errorClass)) {
            throw new IllegalArgumentException("Class is not extends runtime exception");
        }
        Field codeField = errorClass.getDeclaredField("code");
        codeField.setAccessible(true);
        int code = codeField.getInt(null);
        Constructor<?> exceptionConstructor = errorClass.getConstructor(String.class);
        return new Triple<>(
                ApiError.defaultNamespace,
                code,
                error -> {
                    try {
                        return (RuntimeException) exceptionConstructor.newInstance(error.getReason());
                    } catch (Exception e) {
                        throw new Error(e);
                    }
                }
        );
    }

    @SneakyThrows
    public void registerError(LibraryApplication application, Class<? extends ApiError> errorClass) {
        Triple<String, Integer, Function<ApiError, RuntimeException>> errorInfo = createBuilder(errorClass);
        application.addExceptionBuilder(errorInfo.getFirst(), errorInfo.getSecond(), errorInfo.getThird());
    }

    @SafeVarargs
    @SneakyThrows
    public void registerAllErrors(LibraryApplication application, Class<? extends ApiError>... errorClasses) {
        for (Class<? extends ApiError> errorClass: errorClasses) {
            registerError(application, errorClass);
        }
    }

}
