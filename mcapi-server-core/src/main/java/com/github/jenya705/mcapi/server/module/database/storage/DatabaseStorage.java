package com.github.jenya705.mcapi.server.module.database.storage;

import com.github.jenya705.mcapi.server.entity.BotEntity;
import com.github.jenya705.mcapi.server.entity.BotLinkEntity;
import com.github.jenya705.mcapi.server.entity.BotPermissionEntity;
import com.github.jenya705.mcapi.server.module.config.GlobalConfig;

import java.util.List;
import java.util.UUID;

/**
 * @author Jenya705
 */
public interface DatabaseStorage {

    void setup();

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

    void delete(BotEntity botEntity);

    void delete(BotLinkEntity linkEntity);

    void save(BotPermissionEntity permissionEntity);

    void save(BotLinkEntity linkEntity);

    void save(BotEntity botEntity);

    void update(BotPermissionEntity permissionEntity);

    void update(BotEntity botEntity);

    void upsert(BotPermissionEntity permissionEntity);

    default boolean isExistBotWithName(String name) {
        return !findBotsByName(name).isEmpty();
    }

    default boolean canCreateBot(String name, GlobalConfig globalConfig) {
        return globalConfig.isBotNameUnique() && !isExistBotWithName(name);
    }

    default boolean isExistPermission(int botId, String permissionName, UUID target) {
        return findPermission(
                botId, permissionName, target
        ) != null;
    }
}
