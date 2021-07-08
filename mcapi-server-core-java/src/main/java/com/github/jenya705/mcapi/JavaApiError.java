package com.github.jenya705.mcapi;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Jenya705
 */
@Getter
@Setter(AccessLevel.PROTECTED)
@AllArgsConstructor
public class JavaApiError implements ApiError {

    private String description;

}
