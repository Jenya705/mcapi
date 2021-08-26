package com.github.jenya705.mcapi.command;

import lombok.Getter;

import java.util.HashMap;
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
        node.put(name, tree.getNode());
        return this;
    }

    @Override
    public CommandTree leaf(String name, CommandExecutor executor) {
        node.put(name, executor);
        return this;
    }
}
