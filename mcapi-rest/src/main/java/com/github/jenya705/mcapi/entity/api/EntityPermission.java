package com.github.jenya705.mcapi.entity.api;

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
public class EntityPermission implements ApiPermission {

    private boolean toggled;
    private String name;
    private UUID target;

}
