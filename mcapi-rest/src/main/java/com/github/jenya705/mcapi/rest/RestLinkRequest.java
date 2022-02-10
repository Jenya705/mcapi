package com.github.jenya705.mcapi.rest;

import com.github.jenya705.mcapi.LinkRequest;
import com.github.jenya705.mcapi.jackson.DefaultNull;
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

    @DefaultNull
    private String[] requireRequestPermissions;
    @DefaultNull
    private String[] optionalRequestPermissions;
    @DefaultNull
    private String[] minecraftRequestCommands;

    public static RestLinkRequest from(LinkRequest request) {
        return new RestLinkRequest(
                request.getRequireRequestPermissions(),
                request.getOptionalRequestPermissions(),
                request.getMinecraftRequestCommands()
        );
    }
}
