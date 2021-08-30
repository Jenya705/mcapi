package com.github.jenya705.mcapi.link;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class LinkRequestEntity implements LinkRequest {

    private String[] requireRequestPermissions;
    private String[] optionalRequestPermissions;
    private String reason;

}
