package com.github.jenya705.mcapi.server.module.database.storage;

import com.github.jenya705.mcapi.server.entity.BotEntity;
import com.github.jenya705.mcapi.server.entity.BotLinkEntity;
import com.github.jenya705.mcapi.server.entity.BotPermissionEntity;
import com.google.inject.ImplementedBy;

import java.util.List;
import java.util.UUID;

/**
 *
 * Database storage with event invoking <br><br>
 *
 * All update events can be cancelled, so functions return false if cancelled otherwise true <br>
 * All query events can be modified, so functions return <strong>modified</strong> object
 *
 * @author Jenya705
 */
@ImplementedBy(EventDatabaseStorageImpl.class)
public interface EventDatabaseStorage {

    BotEntity findBotById(int id);

    BotEntity findBotByToken(String token);

    List<BotEntity> findBotsByOwner(UUID owner);

    List<BotEntity> findAllBots();

    List<BotEntity> findBotsPageByOwner(UUID owner, int page, int size);

    List<BotPermissionEntity> findPermissionsPageById(int id, int page, int size);

    List<BotEntity> findBotsByName(String name);

    BotPermissionEntity findPermission(int botId, String permission, UUID target);

    List<BotPermissionEntity> findPermissionsById(int botId);

    List<BotPermissionEntity> findPermissionsByIdAndTarget(int botId, UUID target);

    List<BotLinkEntity> findAllLinks();

    BotLinkEntity findLink(int botId, UUID target);

    List<BotLinkEntity> findLinksById(int botId);

    List<BotLinkEntity> findLinksByTarget(UUID target);

    boolean delete(BotEntity botEntity);

    boolean delete(BotLinkEntity linkEntity);

    boolean save(BotPermissionEntity permissionEntity);

    boolean save(BotLinkEntity linkEntity);

    boolean save(BotEntity botEntity);

    boolean update(BotPermissionEntity permissionEntity);

    boolean update(BotEntity botEntity);

    boolean upsert(BotPermissionEntity permissionEntity);

}
