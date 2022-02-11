package com.github.jenya705.mcapi.server.module.link;

import com.github.jenya705.mcapi.LinkRequest;
import com.github.jenya705.mcapi.server.command.CommandDescription;
import com.github.jenya705.mcapi.server.entity.AbstractBot;
import lombok.Data;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jenya705
 */
@Data
public class LinkObject {

    private final int id;
    private final LinkRequest request;
    private final List<CommandDescription> commandDescriptions;
    private final Map<String, Boolean> optionalPermissions;
    private final AbstractBot bot;

    public LinkObject(LinkRequest request, AbstractBot bot, List<CommandDescription> commandDescriptions, int id) {
        this.request = request;
        this.id = id;
        this.bot = bot;
        this.commandDescriptions = commandDescriptions;
        if (this.request.getOptionalRequestPermissions().length == 0) {
            optionalPermissions = Collections.emptyMap();
        }
        else {
            optionalPermissions = new HashMap<>();
            for (String optionalPermission : this.request.getOptionalRequestPermissions()) {
                optionalPermissions.put(optionalPermission, true);
            }
        }
    }

    public boolean isOptionalPermissionToggled(String name) {
        return optionalPermissions.getOrDefault(name, false);
    }

    public void toggleOptionalPermission(String name) {
        if (!optionalPermissions.containsKey(name)) return;
        optionalPermissions.put(name, !isOptionalPermissionToggled(name));
    }
}
