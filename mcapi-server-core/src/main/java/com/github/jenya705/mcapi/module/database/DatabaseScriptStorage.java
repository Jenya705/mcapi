package com.github.jenya705.mcapi.module.database;

import com.github.jenya705.mcapi.entity.BotEntity;
import com.github.jenya705.mcapi.entity.BotLinkEntity;
import com.github.jenya705.mcapi.entity.BotPermissionEntity;
import com.github.jenya705.mcapi.module.config.GlobalConfig;

import java.util.List;
import java.util.UUID;

/**
 * @author Jenya705
 */
public interface DatabaseScriptStorage {

    void setup();

    BotEntity findBotById(int id);

    BotEntity findBotByToken(String token);

    List<BotEntity> findBotsByOwner(UUID owner);

    List<BotEntity> findAllBots();

    List<BotEntity> findBotsPageByOwner(UUID owner, int page, int size);

    List<BotEntity> findBotsByName(String name);

    BotPermissionEntity findPermission(long botId, String permission, UUID target);

    List<BotPermissionEntity> findPermissionsById(long botId);

    List<BotPermissionEntity> findPermissionsByIdAndTarget(long botId, UUID target);

    List<BotLinkEntity> findAllLinks();

    BotLinkEntity findLink(long botId, UUID target);

    List<BotLinkEntity> findLinksById(long botId);

    List<BotLinkEntity> findLinksByTarget(UUID target);

    void delete(BotEntity botEntity);

    void delete(BotLinkEntity linkEntity);

    void save(BotPermissionEntity permissionEntity);

    void save(BotLinkEntity linkEntity);

    void save(BotEntity botEntity);

    void update(BotPermissionEntity permissionEntity);

    void update(BotEntity botEntity);

    default boolean isExistBotWithName(String name) {
        return !findBotsByName(name).isEmpty();
    }

    default boolean canCreateBot(String name, GlobalConfig globalConfig) {
        return globalConfig.isBotNameUnique() && !isExistBotWithName(name);
    }

}
