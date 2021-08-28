package com.github.jenya705.mcapi.command;

import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.command.bot.create.CreateBotCommand;
import com.github.jenya705.mcapi.command.bot.list.ListBotCommand;
import com.github.jenya705.mcapi.data.ConfigData;
import com.github.jenya705.mcapi.data.GlobalConfigData;
import lombok.SneakyThrows;

import java.util.function.Supplier;

/**
 * @author Jenya705
 */
public class RootCommand implements Supplier<ContainerCommandExecutor>, BaseCommon {

    @SneakyThrows
    @Override
    public ContainerCommandExecutor get() {
        ContainerCommandExecutor container = new ContainerCommandExecutor("mcapi.command", "mcapi");
        container
                .tree()
                .branch("bot", branch -> branch
                        .leaf("create", new CreateBotCommand())
                        .leaf("list", new ListBotCommand())
                );
        ConfigData configData =
                new GlobalConfigData(core().loadConfig("commands"));
        container.setConfig(configData);
        core().saveConfig("commands", configData.primitiveRepresent());
        return container;
    }
}
