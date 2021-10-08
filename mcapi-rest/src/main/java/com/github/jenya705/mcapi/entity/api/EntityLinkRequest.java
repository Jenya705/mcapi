package com.github.jenya705.mcapi.entity.api;

import com.github.jenya705.mcapi.LinkRequest;
import com.github.jenya705.mcapi.entity.RestLinkRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityLinkRequest implements LinkRequest {

    private String[] requireRequestPermissions;
    private String[] optionalRequestPermissions;
    private String[] minecraftRequestCommands;

    public RestLinkRequest rest() {
        return RestLinkRequest.from(this);
    }
}
