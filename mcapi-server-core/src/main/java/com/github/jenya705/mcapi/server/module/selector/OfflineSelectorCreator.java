package com.github.jenya705.mcapi.server.module.selector;

import com.github.jenya705.mcapi.OfflinePlayer;
import com.github.jenya705.mcapi.error.PlayerNotFoundException;
import com.github.jenya705.mcapi.server.BaseCommon;
import com.github.jenya705.mcapi.server.ServerApplication;

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
                                .getOptionalOfflinePlayerId(id)
                                .orElseThrow(() -> PlayerNotFoundException.create(id))
                )
                .defaultSelector("l", data ->
                        SelectorCreatorUtils
                                .botLinked(
                                        data.getBot(),
                                        linkEntity -> core()
                                                .getOfflinePlayer(linkEntity.getTarget())
                                )
                );
    }
}
