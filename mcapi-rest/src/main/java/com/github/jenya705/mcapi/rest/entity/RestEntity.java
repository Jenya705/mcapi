package com.github.jenya705.mcapi.rest.entity;

import com.github.jenya705.mcapi.entity.Entity;
import com.github.jenya705.mcapi.rest.RestLocation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.kyori.adventure.text.Component;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestEntity {

    private String type;
    private RestLocation location;
    private Component customName;

    public static RestEntity from(Entity entity) {
        return new RestEntity(
                entity.getType(),
                RestLocation.from(entity.getLocation()),
                entity.customName()
        );
    }
}
