package com.github.jenya705.mcapi.link;

import com.github.jenya705.mcapi.ApiLinkRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class ApiLinkRequestEntity implements ApiLinkRequest {

    private String[] requireRequestPermissions;
    private String[] optionalRequestPermissions;
    private String[] minecraftRequestCommands;
}
