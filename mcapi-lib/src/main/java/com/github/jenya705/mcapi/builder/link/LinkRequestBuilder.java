package com.github.jenya705.mcapi.builder.link;

import com.github.jenya705.mcapi.LinkRequest;
import com.github.jenya705.mcapi.builder.Buildable;
import com.github.jenya705.mcapi.entity.api.EntityLinkRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Jenya705
 */
public class LinkRequestBuilder implements Buildable<LinkRequest> {

    public static LinkRequestBuilder create() {
        return new LinkRequestBuilder();
    }

    private List<String> requireRequestPermissions = new ArrayList<>();
    private List<String> optionalRequestPermissions = new ArrayList<>();
    private List<String> minecraftRequestCommands = new ArrayList<>();

    public LinkRequestBuilder requirePermission(String permission) {
        if (requireRequestPermissions == null) requireRequestPermissions = new ArrayList<>();
        requireRequestPermissions.add(permission);
        return this;
    }

    public LinkRequestBuilder requirePermissions(String... permissions) {
        requireRequestPermissions = new ArrayList<>(Arrays.asList(permissions));
        return this;
    }

    public LinkRequestBuilder requirePermissions(List<String> permissions) {
        requireRequestPermissions = new ArrayList<>(permissions);
        return this;
    }

    public LinkRequestBuilder optionalPermission(String permission) {
        if (optionalRequestPermissions == null) optionalRequestPermissions = new ArrayList<>();
        optionalRequestPermissions.add(permission);
        return this;
    }

    public LinkRequestBuilder optionalPermissions(String... permissions) {
        optionalRequestPermissions = new ArrayList<>(Arrays.asList(permissions));
        return this;
    }

    public LinkRequestBuilder optionalPermissions(List<String> permissions) {
        optionalRequestPermissions = new ArrayList<>(permissions);
        return this;
    }

    public LinkRequestBuilder command(String command) {
        if (minecraftRequestCommands == null) minecraftRequestCommands = new ArrayList<>();
        minecraftRequestCommands.add(command);
        return this;
    }

    public LinkRequestBuilder commands(String... commands) {
        minecraftRequestCommands = new ArrayList<>(Arrays.asList(commands));
        return this;
    }

    public LinkRequestBuilder commands(List<String> commands) {
        minecraftRequestCommands = new ArrayList<>(commands);
        return this;
    }

    @Override
    public LinkRequest build() {
        return new EntityLinkRequest(
                requireRequestPermissions.toArray(String[]::new),
                optionalRequestPermissions.toArray(String[]::new),
                minecraftRequestCommands.toArray(String[]::new)
        );
    }
}
