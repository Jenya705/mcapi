package com.github.jenya705.mcapi.command;

import com.github.jenya705.mcapi.ApiCommandSender;
import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.data.ConfigData;
import com.github.jenya705.mcapi.data.MapConfigData;
import com.github.jenya705.mcapi.stringful.StringfulIterator;
import com.github.jenya705.mcapi.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
public class ContainerCommandExecutor implements CommandExecutor, BaseCommon {

    private final Map<String, Object> nodes = new HashMap<>();
    private final String permission;

    private String notPermittedMessage = "&cYou are not permitted to do that";

    public ContainerCommandExecutor(String permission) {
        this.permission = permission;
    }

    @Override
    public void onCommand(ApiCommandSender sender, StringfulIterator args, String permission) {
        Pair<Object, String> pair = walkThrew(args);
        pair.setRight(permission + "." + pair.getRight());
        if (pair.getLeft() instanceof CommandExecutor) {
            if (!sender.hasPermission(pair.getRight())) {
                sender.sendMessage(CommandsUtil.placeholderMessage(notPermittedMessage));
                return;
            }
            ((CommandExecutor) pair.getLeft()).onCommand(sender, args, pair.getRight());
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> onTab(ApiCommandSender sender, StringfulIterator args, String permission) {
        Pair<Object, String> pair = walkThrew(args);
        pair.setRight(permission + "." + pair.getRight());
        if (pair.getLeft() instanceof CommandExecutor) {
            if (!sender.hasPermission(pair.getRight())) {
                return null;
            }
            return ((CommandExecutor) pair.getLeft()).onTab(sender, args, pair.getRight());
        }
        else {
            List<String> tabs = new ArrayList<>(((Map<String, Object>) pair.getLeft()).keySet());
            return tabs
                    .stream()
                    .filter(it -> sender.hasPermission(pair.getRight() + "." + it))
                    .collect(Collectors.toList());
        }
    }

    @SuppressWarnings("unchecked")
    protected Pair<Object, String> walkThrew(StringfulIterator args) {
        Map<String, Object> current = nodes;
        StringBuilder path = new StringBuilder();
        while (true) {
            if (!args.hasNext()) break;
            String next = args.next();
            if (!current.containsKey(next)) break;
            path.append(next);
            Object nextObject = current.get(next);
            if (nextObject instanceof CommandExecutor) return new Pair<>(nextObject, path.toString());
            if (nextObject instanceof Map) current = (Map<String, Object>) nextObject;
            else throw badItemInNodes();
        }
        return new Pair<>(current, path.toString());
    }

    @Override
    public void setConfig(ConfigData config) {
        notPermittedMessage = config.required("notPermittedMessage", notPermittedMessage);
        setConfig(config, nodes);
        recalculatePermissions(permission, nodes);
    }

    @SuppressWarnings("unchecked")
    protected void setConfig(ConfigData config, Map<String, Object> node) {
        for (Map.Entry<String, Object> entry: node.entrySet()) {
            if (entry.getValue() instanceof CommandExecutor) {
                ((CommandExecutor) entry.getValue())
                        .setConfig(config.required(entry.getKey(), new MapConfigData()));
            }
            else if (entry.getValue() instanceof Map) {
                setConfig(
                        config.required(entry.getKey(), new MapConfigData()),
                        (Map<String, Object>) entry.getValue()
                );
            }
            else {
                throw badItemInNodes();
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void recalculatePermissions(String path, Map<String, Object> node) {
        for (Map.Entry<String, Object> entry: node.entrySet()) {
            if (entry.getValue() instanceof CommandExecutor) {
                String commandPath = path + "." + entry.getKey();
                core().permission(commandPath, false);
                AdditionalPermissions additionalPermissionsAnnotation =
                        entry.getValue().getClass().getAnnotation(AdditionalPermissions.class);
                if (additionalPermissionsAnnotation != null) {
                    for (String permission: additionalPermissionsAnnotation.value()) {
                        core().permission(commandPath + "." + permission, false);
                    }
                }
            }
            else if (entry.getValue() instanceof Map) {
                recalculatePermissions(path + "." + entry.getKey(), (Map<String, Object>) entry.getValue());
            }
            else {
                throw badItemInNodes();
            }
        }
    }

    protected RuntimeException badItemInNodes() {
        return new IllegalStateException(
                "Found not CommandExecutor and not Map<String, Object> in node"
        );
    }

    protected CommandTree tree() {
        return new ContainerCommandTree(nodes);
    }

}
