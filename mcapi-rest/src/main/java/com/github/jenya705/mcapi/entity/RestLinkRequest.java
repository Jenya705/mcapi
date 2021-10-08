package com.github.jenya705.mcapi.entity;

import com.github.jenya705.mcapi.LinkRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestLinkRequest {

    private String[] requireRequestPermissions;
    private String[] optionalRequestPermissions;
    private String[] minecraftRequestCommands;

    public static RestLinkRequest from(LinkRequest request) {
        return new RestLinkRequest(
                request.getRequireRequestPermissions(),
                request.getOptionalRequestPermissions(),
                request.getMinecraftRequestCommands()
        );
    }
}
