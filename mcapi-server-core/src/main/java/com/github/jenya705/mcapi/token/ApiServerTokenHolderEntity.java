package com.github.jenya705.mcapi.token;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

/**
 * @since 1.0
 * @author Jenya705
 */
@Getter
@AllArgsConstructor
public class ApiServerTokenHolderEntity implements ApiServerTokenHolder {

    private final UUID player;
    private final String token;
    private final String name;

}
