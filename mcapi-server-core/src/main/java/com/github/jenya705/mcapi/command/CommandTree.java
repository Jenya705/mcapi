package com.github.jenya705.mcapi.command;

import java.util.function.Consumer;

/**
 * @author Jenya705
 */
public interface CommandTree {

    CommandTree branch(String name, Consumer<CommandTree> treeConsumer);

    CommandTree ghostBranch(String name, Consumer<CommandTree> treeConsumer);

    CommandTree leaf(String name, CommandExecutor executor);

}
