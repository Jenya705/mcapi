package com.github.jenya705.mcapi.server.module.database.storage;

import com.github.jenya705.mcapi.server.entity.BotEntity;
import com.github.jenya705.mcapi.server.entity.BotLinkEntity;
import com.github.jenya705.mcapi.server.entity.BotPermissionEntity;
import com.github.jenya705.mcapi.server.event.EventLoop;
import com.github.jenya705.mcapi.server.event.database.DatabaseFoundEvent;
import com.github.jenya705.mcapi.server.event.database.DatabaseUpdateDoneEvent;
import com.github.jenya705.mcapi.server.event.database.DatabaseUpdateEvent;
import com.github.jenya705.mcapi.server.module.database.DatabaseModule;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * @author Jenya705
 */
@Singleton
public class EventDatabaseStorageImpl implements EventDatabaseStorage {

    private final DatabaseModule databaseModule;
    private final EventLoop eventLoop;

    @Inject
    public EventDatabaseStorageImpl(DatabaseModule databaseModule, EventLoop eventLoop) {
        this.databaseModule = databaseModule;
        this.eventLoop = eventLoop;
    }

    @Override
    public BotEntity findBotById(int id) {
        return found(databaseModule.storage().findBotById(id));
    }

    @Override
    public BotEntity findBotByToken(String token) {
        return found(databaseModule.storage().findBotByToken(token));
    }

    @Override
    public List<BotEntity> findBotsByOwner(UUID owner) {
        return found(databaseModule.storage().findBotsByOwner(owner));
    }

    @Override
    public List<BotEntity> findAllBots() {
        return found(databaseModule.storage().findAllBots());
    }

    @Override
    public List<BotEntity> findBotsPageByOwner(UUID owner, int page, int size) {
        return found(databaseModule.storage().findBotsPageByOwner(owner, page, size));
    }

    @Override
    public List<BotPermissionEntity> findPermissionsPageById(int id, int page, int size) {
        return found(databaseModule.storage().findPermissionsPageById(id, page, size));
    }

    @Override
    public List<BotEntity> findBotsByName(String name) {
        return found(databaseModule.storage().findBotsByName(name));
    }

    @Override
    public BotPermissionEntity findPermission(int botId, String permission, UUID target) {
        return found(databaseModule.storage().findPermission(botId, permission, target));
    }

    @Override
    public List<BotPermissionEntity> findPermissionsById(int botId) {
        return found(databaseModule.storage().findPermissionsById(botId));
    }

    @Override
    public List<BotPermissionEntity> findPermissionsByIdAndTarget(int botId, UUID target) {
        return found(databaseModule.storage().findPermissionsByIdAndTarget(botId, target));
    }

    @Override
    public List<BotLinkEntity> findAllLinks() {
        return found(databaseModule.storage().findAllLinks());
    }

    @Override
    public BotLinkEntity findLink(int botId, UUID target) {
        return found(databaseModule.storage().findLink(botId, target));
    }

    @Override
    public List<BotLinkEntity> findLinksById(int botId) {
        return found(databaseModule.storage().findLinksById(botId));
    }

    @Override
    public List<BotLinkEntity> findLinksByTarget(UUID target) {
        return found(databaseModule.storage().findLinksByTarget(target));
    }

    @Override
    public boolean delete(BotEntity botEntity) {
        return update(botEntity, DatabaseUpdateEvent.Result.DELETE, databaseModule.storage()::delete);
    }

    @Override
    public boolean delete(BotLinkEntity linkEntity) {
        return update(linkEntity, DatabaseUpdateEvent.Result.DELETE, databaseModule.storage()::delete);
    }

    @Override
    public boolean save(BotPermissionEntity permissionEntity) {
        return update(permissionEntity, DatabaseUpdateEvent.Result.CREATE, databaseModule.storage()::save);
    }

    @Override
    public boolean save(BotLinkEntity linkEntity) {
        return update(linkEntity, DatabaseUpdateEvent.Result.CREATE, databaseModule.storage()::save);
    }

    @Override
    public boolean save(BotEntity botEntity) {
        return update(botEntity, DatabaseUpdateEvent.Result.CREATE, databaseModule.storage()::save);
    }

    @Override
    public boolean update(BotPermissionEntity permissionEntity) {
        return update(permissionEntity, DatabaseUpdateEvent.Result.UPDATE, databaseModule.storage()::update);
    }

    @Override
    public boolean update(BotEntity botEntity) {
        return update(botEntity, DatabaseUpdateEvent.Result.UPDATE, databaseModule.storage()::update);
    }

    @Override
    public boolean upsert(BotPermissionEntity permissionEntity) {
        return update(permissionEntity, DatabaseUpdateEvent.Result.UPSERT, databaseModule.storage()::upsert);
    }

    private <T> T found(T entity) {
        DatabaseFoundEvent<T> event = DatabaseFoundEvent.single(entity);
        eventLoop.invoke(event);
        return event.getSingle();
    }

    private <T> List<T> found(List<? extends T> entities) {
        DatabaseFoundEvent<T> event = new DatabaseFoundEvent<>(entities);
        eventLoop.invoke(event);
        return event.getEntities();
    }

    private <T> boolean update(T entity, DatabaseUpdateEvent.Result result, Consumer<T> consumer) {
        DatabaseUpdateEvent event = new DatabaseUpdateEvent(entity, result);
        eventLoop.invoke(event);
        if (!event.isCancelled()) {
            consumer.accept(entity);
            DatabaseUpdateDoneEvent doneEvent = new DatabaseUpdateDoneEvent(entity, result);
            eventLoop.invoke(doneEvent);
        }
        return !event.isCancelled();
    }

}
