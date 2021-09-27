package com.github.jenya705.mcapi.entity.api;

import com.github.jenya705.mcapi.ApiLocation;
import com.github.jenya705.mcapi.entity.RestLocation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityLocation implements ApiLocation {

    private double x;
    private double y;
    private double z;
    private String world;

    public RestLocation rest() {
        return RestLocation.from(this);
    }
}
