package com.github.jenya705.mcapi.module.selector;

import com.github.jenya705.mcapi.ApiOfflinePlayer;
import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.ServerApplication;
import com.github.jenya705.mcapi.error.PlayerNotFoundException;

/**
 * @author Jenya705
 */
public class OfflineSelectorCreator extends MapSelectorCreator<ApiOfflinePlayer, DefaultSelectorCreatorData> implements BaseCommon {

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
                                .orElseThrow(() -> new PlayerNotFoundException(id))
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
