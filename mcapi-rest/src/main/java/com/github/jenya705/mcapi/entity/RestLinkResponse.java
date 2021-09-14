package com.github.jenya705.mcapi.entity;

import com.github.jenya705.mcapi.ApiLinkResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestLinkResponse {

    private boolean failed;
    private String[] declinePermissions;

    public static RestLinkResponse from(ApiLinkResponse response) {
        return new RestLinkResponse(
                response.isFailed(),
                response.getDeclinePermissions()
        );
    }

}
