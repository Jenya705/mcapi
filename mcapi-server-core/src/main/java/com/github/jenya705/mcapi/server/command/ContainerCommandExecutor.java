package com.github.jenya705.mcapi.server.command;

import com.github.jenya705.mcapi.CommandSender;
import com.github.jenya705.mcapi.server.application.AbstractApplicationModule;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.data.ConfigData;
import com.github.jenya705.mcapi.server.module.config.message.MessageContainer;
import com.github.jenya705.mcapi.server.stringful.StringfulIterator;
import com.github.jenya705.mcapi.server.util.MutablePair;
import com.github.jenya705.mcapi.server.util.Pair;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
public class ContainerCommandExecutor extends AbstractApplicationModule implements CommandExecutor {

    private final Map<String, Object> nodes;

    private final String permission;
    private final String command;
    private final MessageContainer messageContainer;

    public ContainerCommandExecutor(ServerApplication application, String permission, String command) {
        this(application, new ConcurrentHashMap<>(), permission, command);
    }

    public ContainerCommandExecutor(ServerApplication application, Map<String, Object> nodes, String permission, String command) {
        super(application);
        messageContainer = bean(MessageContainer.class);
        this.nodes = nodes;
        this.permission = permission;
        this.command = command;
    }

    public Map<String, Object> getNodes() {
        return Collections.unmodifiableMap(nodes);
    }

    public void putUnsafe(String name, Object obj) {
        if (!(obj instanceof Map) && !(obj instanceof CommandExecutor)) {
            throw badItemInNodes();
        }
        nodes.put(name.toLowerCase(Locale.ROOT), obj);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onCommand(CommandSender sender, StringfulIterator args, String permission) {
        Pair<Object, String> pair = walkThrew(args);
        String fullPermission = permission + pair.getRight();
        if (pair.getLeft() instanceof CommandExecutor) {
            if (!sender.hasPermission(fullPermission)) {
                sender.sendMessage(messageContainer.render(messageContainer.notPermitted(), sender));
                return;
            }
            ((CommandExecutor) pair.getLeft()).onCommand(sender, args, fullPermission);
        }
        else {
            Map<String, Object> node = (Map<String, Object>) pair.getLeft();
            String commandStart = "/" + command + pair.getRight().replace('.', ' ') + " ";
            sender.sendMessage(messageContainer.render(
                    messageContainer.commandHelp(
                            node
                                    .entrySet()
                                    .stream()
                                    .filter(it -> sender.hasPermission(fullPermission + "." + it.getKey()))
                                    .filter(it -> !isGhost(it.getValue()))
                                    .map(Map.Entry::getKey)
                                    .collect(Collectors.toList()),
                            commandStart
                    ),
                    sender
            ));
        }
    }

    @Override
    public List<CommandTab> onTab(CommandSender sender, StringfulIterator args, String permission) {
        return tab(sender, args, permission, executor -> executor.onTab(sender, args, permission));
    }

    @Override
    public List<CommandTab> asyncTab(CommandSender sender, StringfulIterator args, String permission) {
        return tab(sender, args, permission, executor -> executor.asyncTab(sender, args, permission));
    }

    @SuppressWarnings("unchecked")
    private List<CommandTab> tab(CommandSender sender, StringfulIterator args, String permission, Function<CommandExecutor, List<CommandTab>> tab) {
        MutablePair<Object, String> pair = walkThrew(args).mutable();
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
    private Pair<Object, String> walkThrew(StringfulIterator args) {
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
        setConfig(config, nodes);
        recalculatePermissions();
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
        return obj instanceof GhostableBranch && ((GhostableBranch) obj).isGhost();
    }
}
