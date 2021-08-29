package com.github.jenya705.mcapi.module.database;

import com.github.jenya705.mcapi.entity.BotEntity;
import com.github.jenya705.mcapi.entity.BotPermissionEntity;

import java.util.List;
import java.util.UUID;

/**
 * @author Jenya705
 */
public interface DatabaseScriptStorage {

    void setup();

    BotEntity findBotById(long id);

    BotEntity findBotByToken(String token);

    List<BotEntity> findBotsByOwner(UUID owner);

    List<BotEntity> findAllBots();

    List<BotEntity> findBotsPageByOwner(UUID owner, int page, int size);

    BotPermissionEntity findPermission(long botId, String permission, UUID target);

    List<BotPermissionEntity> findPermissionsById(long botId);

    void save(BotPermissionEntity permissionEntity);

    void save(BotEntity botEntity);

    void update(BotPermissionEntity permissionEntity);

    void update(BotEntity botEntity);

}
