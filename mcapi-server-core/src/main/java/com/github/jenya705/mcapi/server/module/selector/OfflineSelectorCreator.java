package com.github.jenya705.mcapi.server.module.selector;

import com.github.jenya705.mcapi.error.PlayerNotFoundException;
import com.github.jenya705.mcapi.player.OfflinePlayer;
import com.github.jenya705.mcapi.server.application.BaseCommon;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @author Jenya705
 */
public class OfflineSelectorCreator extends MapSelectorCreator<OfflinePlayer, DefaultSelectorCreatorData> implements BaseCommon {

    private final ServerApplication application;

    @Override
    public ServerApplication app() {
        return application;
    }

    public OfflineSelectorCreator(ServerApplication application) {
        this.application = application;
        this
                .uuidDirect((data, id) ->
                        core()
                                .getOfflinePlayer(id)
                                .switchIfEmpty(Mono.error(() -> PlayerNotFoundException.create(id)))
                )
                .defaultSelector("l", data ->
                        SelectorCreatorUtils.botLinked(data.getBot(), it -> core().getOfflinePlayer(it.getTarget()))
                );
    }
}
