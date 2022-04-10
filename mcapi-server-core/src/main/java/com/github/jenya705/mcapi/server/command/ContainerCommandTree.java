package com.github.jenya705.mcapi.server.command;

import lombok.Getter;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author Jenya705
 */
@Getter
public class ContainerCommandTree implements CommandTree {

    private final Map<String, Object> node;

    public ContainerCommandTree(Map<String, Object> node) {
        this.node = node;
    }

    public ContainerCommandTree() {
        this(new HashMap<>());
    }

    @Override
    public CommandTree branch(String name, Consumer<CommandTree> treeConsumer) {
        ContainerCommandTree tree = new ContainerCommandTree();
        treeConsumer.accept(tree);
        node.put(name.toLowerCase(Locale.ROOT), tree.getNode());
        return this;
    }

    @Override
    public CommandTree ghostBranch(String name, Consumer<CommandTree> treeConsumer) {
        ContainerCommandTree tree = new ContainerCommandTree();
        treeConsumer.accept(tree);
        node.put(name.toLowerCase(Locale.ROOT), new ContainerGhostableBranch(true, tree.getNode()));
        return this;
    }

    @Override
    public CommandTree leaf(String name, CommandExecutor executor) {
        node.put(name.toLowerCase(Locale.ROOT), executor);
        return this;
    }
}
