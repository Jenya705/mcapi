package com.github.jenya705.mcapi.command;

import com.github.jenya705.mcapi.ApiCommandSender;
import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.data.ConfigData;
import com.github.jenya705.mcapi.stringful.StringfulIterator;
import com.github.jenya705.mcapi.util.Pair;
import lombok.Getter;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
public class ContainerCommandExecutor implements CommandExecutor, BaseCommon {

    @Getter
    private final Map<String, Object> nodes;

    private final String permission;
    private final String command;

    private ContainerCommandConfig config;

    public ContainerCommandExecutor(String permission, String command) {
        this(new HashMap<>(), permission, command);
    }

    public ContainerCommandExecutor(Map<String, Object> nodes, String permission, String command) {
        this.nodes = nodes;
        this.permission = permission;
        this.command = command;
    }

    public void withConfig(ContainerCommandConfig config) {
        this.config = config;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onCommand(ApiCommandSender sender, StringfulIterator args, String permission) {
        Pair<Object, String> pair = walkThrew(args);
        String fullPermission = permission + pair.getRight();
        if (pair.getLeft() instanceof CommandExecutor) {
            if (!sender.hasPermission(fullPermission)) {
                sender.sendMessage(CommandsUtils.placeholderMessage(config.getNotPermittedMessage()));
                return;
            }
            ((CommandExecutor) pair.getLeft()).onCommand(sender, args, fullPermission);
        }
        else {
            Map<String, Object> node = (Map<String, Object>) pair.getLeft();
            String commandStart = "/" + command + pair.getRight().replaceAll("\\.", " ") + " ";
            sender.sendMessage(CommandsUtils
                    .listMessage(
                            config.getHelpLayout(),
                            config.getHelpElement(),
                            config.getHelpListDelimiter(),
                            new ArrayList<>(node.keySet()),
                            it -> new String[]{
                                    "%name%", it,
                                    "%command%", commandStart + it
                            }
                    )
            );
        }
    }

    @Override
    public List<CommandTab> onTab(ApiCommandSender sender, StringfulIterator args, String permission) {
        return tab(sender, args, permission, executor -> executor.onTab(sender, args, permission));
    }

    @Override
    public List<CommandTab> asyncTab(ApiCommandSender sender, StringfulIterator args, String permission) {
        return tab(sender, args, permission, executor -> executor.asyncTab(sender, args, permission));
    }

    @SuppressWarnings("unchecked")
    private List<CommandTab> tab(ApiCommandSender sender, StringfulIterator args, String permission, Function<CommandExecutor, List<CommandTab>> tab) {
        Pair<Object, String> pair = walkThrew(args);
        pair.setRight(permission + pair.getRight());
        if (pair.getLeft() instanceof CommandExecutor) {
            if (!sender.hasPermission(pair.getRight())) {
                return null;
            }
            return tab.apply((CommandExecutor) pair.getLeft());
        }
        else {
            if (!args.hasNext() || isGhost(pair.getLeft())) return null;
            Map<String, Object> node = (Map<String, Object>) pair.getLeft();
            List<String> tabs = new ArrayList<>(node.keySet());
            return tabs
                    .stream()
                    .map(CommandTab::of)
                    .filter(it -> sender.hasPermission(pair.getRight() + "." + it.getName()))
                    .filter(it -> !isGhost(node.get(it.getName())))
                    .collect(Collectors.toList());
        }
    }

    @SuppressWarnings("unchecked")
    protected Pair<Object, String> walkThrew(StringfulIterator args) {
        Map<String, Object> current = nodes;
        StringBuilder path = new StringBuilder();
        while (true) {
            if (!args.hasNext()) break;
            String next = args.next().toLowerCase(Locale.ROOT);
            if (!current.containsKey(next)) {
                args.back();
                break;
            }
            path.append(".").append(next);
            Object nextObject = current.get(next);
            if (nextObject instanceof CommandExecutor) return new Pair<>(nextObject, path.toString());
            if (nextObject instanceof Map) current = (Map<String, Object>) nextObject;
            else throw badItemInNodes();
        }
        return new Pair<>(current, path.toString());
    }

    @Override
    public void setConfig(ConfigData config) {
        this.config = new ContainerCommandConfig(config);
        setConfig(config, nodes);
        recalculatePermissions(permission, nodes);
    }

    @SuppressWarnings("unchecked")
    protected void setConfig(ConfigData config, Map<String, Object> node) {
        for (Map.Entry<String, Object> entry : node.entrySet()) {
            if (entry.getValue() instanceof CommandExecutor) {
                if (entry.getValue().getClass().getAnnotation(NoConfig.class) != null) continue;
                ((CommandExecutor) entry.getValue())
                        .setConfig(config.required(entry.getKey()));
            }
            else if (entry.getValue() instanceof Map) {
                setConfig(
                        config.required(entry.getKey()),
                        (Map<String, Object>) entry.getValue()
                );
            }
            else {
                throw badItemInNodes();
            }
        }
    }

    public void recalculatePermissions() {
        recalculatePermissions(permission, nodes);
    }

    @SuppressWarnings("unchecked")
    private void recalculatePermissions(String path, Map<String, Object> node) {
        for (Map.Entry<String, Object> entry : node.entrySet()) {
            if (entry.getValue() instanceof CommandExecutor) {
                String commandPath = path + "." + entry.getKey();
                core().permission(commandPath, false);
                AdditionalPermissions additionalPermissionsAnnotation =
                        entry.getValue().getClass().getAnnotation(AdditionalPermissions.class);
                if (additionalPermissionsAnnotation != null) {
                    for (String permission : additionalPermissionsAnnotation.value()) {
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

    public CommandTree tree() {
        return new ContainerCommandTree(nodes);
    }

    private boolean isGhost(Object obj) {
        return obj instanceof GhostBranch && ((GhostBranch) obj).isGhost();
    }
}
