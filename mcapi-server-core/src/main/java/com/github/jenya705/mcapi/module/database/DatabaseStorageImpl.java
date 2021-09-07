package com.github.jenya705.mcapi.module.database;

import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.entity.BotEntity;
import com.github.jenya705.mcapi.entity.BotLinkEntity;
import com.github.jenya705.mcapi.entity.BotPermissionEntity;
import com.github.jenya705.mcapi.module.database.cache.CacheStorage;
import com.github.jenya705.mcapi.module.storage.StorageModule;
import com.github.jenya705.mcapi.util.FileUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.*;

/**
 * @author Jenya705
 */
@Getter(AccessLevel.PROTECTED)
public class DatabaseStorageImpl implements DatabaseStorage, BaseCommon {

    private final StorageModule storageModule = bean(StorageModule.class);
    private final DatabaseModule databaseModule;
    private final String sqlType;

    // Scripts
    private final String setup;
    private final String findAllBots;
    private final String findBotById;
    private final String findBotByToken;
    private final String findBotsByOwner;
    private final String findBotsPageByOwner;
    private final String findBotsByName;
    private final String findPermission;
    private final String findPermissionsById;
    private final String findPermissionsByIdAndTarget;
    private final String findAllLinks;
    private final String findLink;
    private final String findLinksByBot;
    private final String findLinksByTarget;
    private final String deleteLinksByBotId;
    private final String deletePermissionsByBotId;
    private final String deletePermissionsByBotIdAndTarget;
    private final String deleteBot;
    private final String deleteLink;
    private final String saveBot;
    private final String saveLink;
    private final String savePermission;
    private final String updateBot;
    private final String updatePermission;
    private final String upsertPermission;

    public DatabaseStorageImpl(DatabaseModule databaseModule, String sqlType) throws IOException {
        this.databaseModule = databaseModule;
        this.sqlType = sqlType;
        setup = loadScript("setup");
        findAllBots = loadScript("find_all_bots");
        findBotById = loadScript("find_bot_by_id");
        findBotByToken = loadScript("find_bot_by_token");
        findBotsByOwner = loadScript("find_bots_by_owner");
        findBotsPageByOwner = loadScript("find_page_ordered_bots_by_owner");
        findBotsByName = loadScript("find_bots_by_name");
        findPermission = loadScript("find_permission");
        findPermissionsById = loadScript("find_permissions_by_id");
        findPermissionsByIdAndTarget = loadScript("find_permissions_by_id_and_target");
        findAllLinks = loadScript("find_all_links");
        findLink = loadScript("find_link");
        findLinksByBot = loadScript("find_links_by_bot");
        findLinksByTarget = loadScript("find_links_by_target");
        deleteLinksByBotId = loadScript("delete_bot_links");
        deletePermissionsByBotId = loadScript("delete_bot_permissions");
        deletePermissionsByBotIdAndTarget = loadScript("delete_bot_target_permissions");
        deleteBot = loadScript("delete_bot");
        deleteLink = loadScript("delete_link");
        saveBot = loadScript("save_bot");
        saveLink = loadScript("save_link");
        savePermission = loadScript("save_permission");
        updateBot = loadScript("update_bot");
        updatePermission = loadScript("update_permission");
        upsertPermission = loadScript("upsert_permission");
    }

    @Override
    public void setup() {
        String[] splitSetup = setup.split("ANOTHEROPERATION");
        for (String splitSetupElement : splitSetup) databaseModule.update(splitSetupElement);
    }

    @Override
    @SneakyThrows
    public BotEntity findBotById(int id) {
        BotEntity bot = cache().getCachedBot(id);
        if (bot == null) {
            bot = parseSingleElement(BotEntity.mapResultSet(
                    databaseModule.query(findBotById, id)
            ));
            cache().cache(Objects.requireNonNullElseGet(bot, BotEntity::empty));
        }
        return bot;
    }

    @Override
    @SneakyThrows
    public BotEntity findBotByToken(String token) {
        BotEntity bot = cache().getCachedBot(token);
        if (bot == null) {
            bot = parseSingleElement(BotEntity.mapResultSet(
                    databaseModule.query(findBotByToken, token)
            ));
            cache().cache(Objects.requireNonNullElseGet(bot, BotEntity::empty));
        }
        return bot;
    }

    @Override
    @SneakyThrows
    public List<BotEntity> findBotsByOwner(UUID owner) {
        return BotEntity.mapResultSet(
                databaseModule.query(
                        findBotsByOwner,
                        owner.getMostSignificantBits(),
                        owner.getLeastSignificantBits()
                )
        );
    }

    @Override
    @SneakyThrows
    public List<BotEntity> findAllBots() {
        return BotEntity.mapResultSet(databaseModule.query(findAllBots));
    }

    @Override
    @SneakyThrows
    public List<BotEntity> findBotsPageByOwner(UUID owner, int page, int size) {
        return BotEntity.mapResultSet(
                databaseModule.query(
                        findBotsPageByOwner,
                        owner.getMostSignificantBits(),
                        owner.getLeastSignificantBits(),
                        size,
                        page * size
                )
        );
    }

    @Override
    @SneakyThrows
    public List<BotEntity> findBotsByName(String name) {
        return BotEntity.mapResultSet(
                databaseModule.query(
                        findBotsByName,
                        name
                )
        );
    }

    @Override
    @SneakyThrows
    public BotPermissionEntity findPermission(int botId, String permission, UUID target) {
        UUID realTarget = parseTarget(target);
        BotPermissionEntity permissionEntity =
                cache().getPermission(botId, permission, target);
        if (permissionEntity == null) {
            permissionEntity = parseSingleElement(
                    BotPermissionEntity.mapResultSet(
                            databaseModule.query(
                                    findPermission,
                                    botId,
                                    permission,
                                    realTarget.getMostSignificantBits(),
                                    realTarget.getLeastSignificantBits()
                            )
                    )
            );
            cache().cache(Objects.requireNonNullElseGet(permissionEntity, () ->
                    new BotPermissionEntity(
                            botId,
                            permission,
                            target,
                            storageModule.getPermission(permission).isEnabled()
                    )
            ));
        }
        return permissionEntity;
    }

    @Override
    @SneakyThrows
    public List<BotPermissionEntity> findPermissionsById(int botId) {
        Collection<BotPermissionEntity> permissions = cache().getCachedPermissions(botId);
        if (permissions == null) {
            permissions = BotPermissionEntity.mapResultSet(
                    databaseModule.query(findPermissionsById, botId)
            );
            permissions.forEach(it -> cache().cache(it));
        }
        return parseCollection(permissions);
    }

    @Override
    @SneakyThrows
    public List<BotPermissionEntity> findPermissionsByIdAndTarget(int botId, UUID target) {
        return BotPermissionEntity.mapResultSet(
                databaseModule.query(
                        findPermissionsByIdAndTarget,
                        botId,
                        target.getMostSignificantBits(),
                        target.getLeastSignificantBits()
                )
        );
    }

    @Override
    @SneakyThrows
    public List<BotLinkEntity> findAllLinks() {
        return BotLinkEntity.mapResultSet(
                databaseModule.query(findAllLinks)
        );
    }

    @Override
    @SneakyThrows
    public BotLinkEntity findLink(int botId, UUID target) {
        BotLinkEntity link = cache()
                .getCachedLinks(botId)
                .stream()
                .filter(it -> it.getTarget().equals(target))
                .findAny()
                .orElseGet(() ->
                        cache()
                                .getCachedLinks(target)
                                .stream()
                                .filter(it -> it.getBotId() == botId)
                                .findAny()
                                .orElse(null)
                );
        if (link == null) {
            link = parseSingleElement(BotLinkEntity.mapResultSet(
                    databaseModule.query(
                            findLink,
                            botId,
                            target.getMostSignificantBits(),
                            target.getLeastSignificantBits()
                    )
            ));
            cache().cache(link);
        }
        return link;
    }

    @Override
    @SneakyThrows
    public List<BotLinkEntity> findLinksById(int botId) {
        Collection<BotLinkEntity> links = cache().getCachedLinks(botId);
        if (links == null) {
            links = BotLinkEntity.mapResultSet(
                    databaseModule.query(findLinksByBot, botId)
            );
            links.forEach(it -> cache().cache(it));
        }
        return parseCollection(links);
    }

    @Override
    @SneakyThrows
    public List<BotLinkEntity> findLinksByTarget(UUID target) {
        Collection<BotLinkEntity> links = cache().getCachedLinks(target);
        if (links == null) {
            links = BotLinkEntity.mapResultSet(
                    databaseModule.query(
                            findLinksByTarget,
                            target.getMostSignificantBits(),
                            target.getLeastSignificantBits()
                    )
            );
            links.forEach(it -> cache().cache(it));
        }
        return parseCollection(links);
    }

    @Override
    public void delete(BotEntity botEntity) {
        cache()
                .getCachedLinks(botEntity.getId())
                .forEach(it -> cache().unCache(it));
        cache()
                .getCachedPermissions(botEntity.getId())
                .forEach(it -> cache().unCache(it));
        cache()
                .unCache(botEntity);
        databaseModule.update(
                deleteBot,
                botEntity.getId()
        );
        databaseModule.update(
                deleteLinksByBotId,
                botEntity.getId()
        );
        databaseModule.update(
                deletePermissionsByBotId,
                botEntity.getId()
        );
    }

    @Override
    public void delete(BotLinkEntity linkEntity) {
        cache()
                .getCachedPermissions(linkEntity.getBotId())
                .stream()
                .filter(it -> it.getTarget().equals(linkEntity.getTarget()))
                .forEach(it -> cache().unCache(it));
        cache()
                .unCache(linkEntity);
        databaseModule.update(
                deleteLink,
                linkEntity.getBotId(),
                linkEntity.getTarget().getMostSignificantBits(),
                linkEntity.getTarget().getLeastSignificantBits()
        );
        databaseModule.update(
                deletePermissionsByBotIdAndTarget,
                linkEntity.getBotId(),
                linkEntity.getTarget().getMostSignificantBits(),
                linkEntity.getTarget().getLeastSignificantBits()
        );
    }

    @Override
    public void save(BotPermissionEntity permissionEntity) {
        UUID realTarget = parseTarget(permissionEntity.getTarget());
        databaseModule.update(
                savePermission,
                permissionEntity.getBotId(),
                permissionEntity.getPermission(),
                realTarget.getMostSignificantBits(),
                realTarget.getLeastSignificantBits(),
                permissionEntity.isToggled()
        );
        cache().cache(permissionEntity);
    }

    @Override
    public void save(BotLinkEntity linkEntity) {
        databaseModule.update(
                saveLink,
                linkEntity.getBotId(),
                linkEntity.getTarget().getMostSignificantBits(),
                linkEntity.getTarget().getLeastSignificantBits()
        );
        cache().cache(linkEntity);
    }

    @Override
    public void save(BotEntity botEntity) {
        databaseModule.update(
                saveBot,
                botEntity.getToken(),
                botEntity.getName(),
                botEntity.getOwner().getMostSignificantBits(),
                botEntity.getOwner().getLeastSignificantBits()
        );
        cache().cache(botEntity);
    }

    @Override
    public void update(BotPermissionEntity permissionEntity) {
        UUID realTarget = parseTarget(permissionEntity.getTarget());
        databaseModule.update(
                updatePermission,
                permissionEntity.isToggled(),
                permissionEntity.getBotId(),
                permissionEntity.getPermission(),
                realTarget.getMostSignificantBits(),
                realTarget.getLeastSignificantBits()
        );
        cache().cache(permissionEntity);
    }

    @Override
    public void update(BotEntity botEntity) {
        databaseModule.update(
                updateBot,
                botEntity.getToken(),
                botEntity.getName(),
                botEntity.getOwner().getMostSignificantBits(),
                botEntity.getOwner().getLeastSignificantBits(),
                botEntity.getId()
        );
        cache().cache(botEntity);
    }

    @Override
    public void upsert(BotPermissionEntity permissionEntity) {
        UUID realTarget = parseTarget(permissionEntity.getTarget());
        databaseModule.update(
                upsertPermission,
                permissionEntity.getBotId(),
                permissionEntity.getPermission(),
                realTarget.getMostSignificantBits(),
                realTarget.getLeastSignificantBits(),
                permissionEntity.isToggled(),
                permissionEntity.isToggled()
        );
        cache().cache(permissionEntity);
    }

    protected String loadScript(String fileName) throws IOException {
        String realFileName = fileName + ".sql";
        core().getPluginFile(String.format("sql/%s", sqlType)).mkdirs();
        String fixedContextScriptContent = loadContextScript(String.format("sql/%s/%s", sqlType, realFileName));
        if (fixedContextScriptContent != null) return fixedContextScriptContent;
        String defaultContextScriptContent = loadContextScript(String.format("sql/%s", realFileName));
        if (defaultContextScriptContent != null) return defaultContextScriptContent;
        throw new IllegalArgumentException(
                String.format(
                        "Script %s is not exist for %s sql type",
                        fileName, sqlType
                )
        );
    }

    protected String loadContextScript(String path) throws IOException {
        File fixedScriptFile = core()
                .getPluginFile(path);
        if (fixedScriptFile.exists()) {
            return Files.readString(fixedScriptFile.toPath());
        }
        InputStream fixedInputStream = getClass()
                .getClassLoader()
                .getResourceAsStream(path);
        if (fixedInputStream != null) {
            byte[] bytes = FileUtils.readInputStream(fixedInputStream);
            Files.write(
                    fixedScriptFile.toPath(),
                    bytes,
                    StandardOpenOption.CREATE
            );
            fixedInputStream.close();
            return new String(bytes);
        }
        return null;
    }

    protected <T> T parseSingleElement(List<T> array) {
        return array.isEmpty() ? null : array.get(0);
    }

    protected UUID parseTarget(UUID target) {
        return target == null ? BotPermissionEntity.identityTarget : target;
    }

    protected CacheStorage cache() {
        return databaseModule.cache();
    }

    protected <T> List<T> parseCollection(Collection<T> collection) {
        return collection instanceof List ?
                (List<T>) collection :
                new ArrayList<>(collection);
    }
}
