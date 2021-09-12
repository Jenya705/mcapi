package com.github.jenya705.mcapi.module.link;

import com.github.jenya705.mcapi.entity.AbstractBot;
import com.github.jenya705.mcapi.ApiLinkRequest;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jenya705
 */
@Data
public class LinkObject {

    private final int id;
    private final ApiLinkRequest request;
    private final Map<String, Boolean> optionalPermissions;
    private final AbstractBot bot;

    public LinkObject(ApiLinkRequest request, AbstractBot bot, int id) {
        this.request = request;
        this.id = id;
        this.bot = bot;
        optionalPermissions = new HashMap<>();
        for (String optionalPermission : this.request.getOptionalRequestPermissions()) {
            optionalPermissions.put(optionalPermission, true);
        }
    }

    public boolean isOptionalPermissionToggled(String name) {
        if (!optionalPermissions.containsKey(name)) return false;
        return optionalPermissions.get(name);
    }

    public void toggleOptionalPermission(String name) {
        if (!optionalPermissions.containsKey(name)) return;
        optionalPermissions.put(name, !isOptionalPermissionToggled(name));
    }
}
