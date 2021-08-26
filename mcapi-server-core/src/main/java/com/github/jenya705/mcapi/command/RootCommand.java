package com.github.jenya705.mcapi.command;

import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.command.token.CreateBotCommand;
import com.github.jenya705.mcapi.data.ConfigData;
import com.github.jenya705.mcapi.data.MapConfigData;
import lombok.SneakyThrows;

import java.util.function.Supplier;

/**
 * @author Jenya705
 */
public class RootCommand implements Supplier<ContainerCommandExecutor>, BaseCommon {

    @SneakyThrows
    @Override
    public ContainerCommandExecutor get() {
        ContainerCommandExecutor container = new ContainerCommandExecutor();
        container
                .tree()
                .branch("bot", branch -> branch
                        .leaf("create", new CreateBotCommand())
                );
        ConfigData configData =
                new MapConfigData(core().loadConfig("commands"));
        container.setConfig(configData);
        core().saveConfig("commands", configData.primitiveRepresent());
        return container;
    }
}
