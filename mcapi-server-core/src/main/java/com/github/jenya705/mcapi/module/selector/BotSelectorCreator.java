package com.github.jenya705.mcapi.module.selector;

import com.github.jenya705.mcapi.ServerApplication;
import com.github.jenya705.mcapi.entity.AbstractBot;
import com.github.jenya705.mcapi.entity.BotObject;
import com.github.jenya705.mcapi.module.database.DatabaseModule;
import com.github.jenya705.mcapi.module.storage.StorageModule;
import com.github.jenya705.mcapi.util.SelectorContainer;
import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.Optional;

/**
 * @author Jenya705
 */
public class BotSelectorCreator extends MapSelectorCreator<AbstractBot, BotSelectorCreator.Data> {

    @lombok.Data
    @AllArgsConstructor
    public static class Data {
        private AbstractBot bot;
    }

    private final DatabaseModule databaseModule;
    private final StorageModule storageModule;

    public BotSelectorCreator(ServerApplication application) {
        databaseModule = application.getBean(DatabaseModule.class);
        storageModule = application.getBean(StorageModule.class);
        this
                .direct(
                        (data, token) ->
                                new SelectorContainer<>(
                                        Optional.ofNullable(
                                                databaseModule
                                                        .storage()
                                                        .findBotByToken(token)
                                        )
                                                .map(entity -> new BotObject(
                                                        entity,
                                                        databaseModule.storage(),
                                                        storageModule
                                                ))
                                                .orElse(null),
                                        "", null
                                )
                )
                .defaultSelector(
                        "me",
                        data -> Collections.singletonList(data.getBot())
                );
    }
}
