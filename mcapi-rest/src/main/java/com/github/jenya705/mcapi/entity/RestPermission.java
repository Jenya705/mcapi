package com.github.jenya705.mcapi.entity;

import com.github.jenya705.mcapi.ApiPermission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestPermission {

    private boolean toggled;
    private String name;
    private UUID target;

    public static RestPermission from(ApiPermission playerPermission) {
        return new RestPermission(
                playerPermission.isToggled(),
                playerPermission.getName(),
                playerPermission.getTarget()
        );
    }

}
