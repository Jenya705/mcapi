package com.github.jenya705.mcapi.entity;

import com.github.jenya705.mcapi.ApiError;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestError {

    private int code;
    private String namespace;
    private String reason;

    public static RestError from(ApiError error) {
        return new RestError(
                error.getCode(),
                error.getNamespace(),
                error.getReason()
        );
    }

    public static RestError from(Throwable ex) {
        if (ex instanceof ApiError) {
            return RestError.from((ApiError) ex);
        }
        return new RestError(
                0,
                null,
                ex.getMessage()
        );
    }

}
