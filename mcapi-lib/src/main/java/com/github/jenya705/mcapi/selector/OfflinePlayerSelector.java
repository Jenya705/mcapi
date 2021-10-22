package com.github.jenya705.mcapi.selector;

import java.util.UUID;

/**
 * @author Jenya705
 */
public class OfflinePlayerSelector extends ContainerSelector {

    public static final OfflinePlayerSelector linked = new OfflinePlayerSelector("@l", ".@l", null);

    public static OfflinePlayerSelector of(UUID uuid) {
        return new OfflinePlayerSelector(uuid.toString(), "", uuid);
    }

    public static OfflinePlayerSelector of(String name) {
        return new OfflinePlayerSelector(name, "", null);
    }
    
    protected OfflinePlayerSelector(String stringValue, String permission, UUID target) {
        super(stringValue, permission, target);
    }
}
