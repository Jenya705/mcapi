package com.github.jenya705.mcapi.entity.api;

import com.github.jenya705.mcapi.ApiError;
import com.github.jenya705.mcapi.entity.RestError;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityError implements ApiError {

    private int code;
    private String namespace;
    private String reason;

    public RestError rest() {
        return RestError.from(this);
    }

}
