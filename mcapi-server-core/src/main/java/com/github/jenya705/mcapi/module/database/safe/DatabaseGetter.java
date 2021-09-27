package com.github.jenya705.mcapi.module.database.safe;

import com.github.jenya705.mcapi.entity.BotEntity;
import com.github.jenya705.mcapi.entity.BotLinkEntity;
import com.github.jenya705.mcapi.entity.BotPermissionEntity;

import java.util.Collection;
import java.util.UUID;

/**
 * @author Jenya705
 */
public interface DatabaseGetter {

    BotEntity getBot(String token);

    BotEntity getBot(int id);

    Collection<BotLinkEntity> getLinks(UUID target);

    Collection<BotLinkEntity> getLinks(int botId);

    Collection<BotPermissionEntity> getPermissions(int botId);

    BotPermissionEntity getPermission(int botId, String permission, UUID target);
}
