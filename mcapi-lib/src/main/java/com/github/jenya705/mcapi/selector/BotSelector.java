package com.github.jenya705.mcapi.selector;

import java.util.UUID;

/**
 * @author Jenya705
 */
public class BotSelector extends ContainerSelector {

    public static final BotSelector me = new BotSelector("@me", ".@me", null);

    public static BotSelector of(String token) {
        return new BotSelector(token, "", null);
    }

    protected BotSelector(String stringValue, String permission, UUID target) {
        super(stringValue, permission, target);
    }
}
