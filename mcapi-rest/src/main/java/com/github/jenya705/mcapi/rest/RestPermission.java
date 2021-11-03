package com.github.jenya705.mcapi.rest;

import com.github.jenya705.mcapi.Permission;
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

    public static RestPermission from(Permission playerPermission) {
        return new RestPermission(
                playerPermission.isToggled(),
                playerPermission.getName(),
                playerPermission.getTarget()
        );
    }

}
