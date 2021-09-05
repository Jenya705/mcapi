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
public class LinkResponseEntity implements LinkResponse {

    private boolean failed;
    private String[] declinePermissions;
}
