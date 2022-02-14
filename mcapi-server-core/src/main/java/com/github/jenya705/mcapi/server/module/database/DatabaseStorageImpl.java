package com.github.jenya705.mcapi.server.module.database;

import com.github.jenya705.mcapi.server.application.AbstractApplicationModule;
import com.github.jenya705.mcapi.server.application.ServerApplication;
import com.github.jenya705.mcapi.server.entity.BotEntity;
import com.github.jenya705.mcapi.server.entity.BotLinkEntity;
import com.github.jenya705.mcapi.server.entity.BotPermissionEntity;
import com.github.jenya705.mcapi.server.entity.PermissionEntity;
import com.github.jenya705.mcapi.server.module.database.cache.CacheStorage;
import com.github.jenya705.mcapi.server.module.database.cache.FakeCacheStorage;
import com.github.jenya705.mcapi.server.module.storage.StorageModule;
import com.github.jenya705.mcapi.server.util.FileUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
@Getter(AccessLevel.PROTECTED)
public class DatabaseStorageImpl extends AbstractApplicationModule implements DatabaseStorage {

    private final StorageModule storageModule;
    private final DatabaseModule databaseModule;
    private final String sqlType;

    // Scripts
    private final String setup;
    private final String findAllBots;
    private final String findBotById;
    private final String findBotByToken;
    private final String findBotsByOwner;
    private final String findBotsPageByOwner;
    private final String findPermissionsPageById;
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

    public DatabaseStorageImpl(ServerApplication application, DatabaseModule databaseModule,
                               StorageModule storageModule, String sqlType) throws IOException {
        super(application);
        this.storageModule = storageModule;
        this.databaseModule = databaseModule;
        this.sqlType = sqlType;
        setup = loadScript("setup");
        findAllBots = loadScript("find_all_bots");
        findBotById = loadScript("find_bot_by_id");
        findBotByToken = loadScript("find_bot_by_token");
        findBotsByOwner = loadScript("find_bots_by_owner");
        findBotsPageByOwner = loadScript("find_page_ordered_bots_by_owner");
        findPermissionsPageById = loadScript("find_page_ordered_permissions_by_id");
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
        for (String splitSetupElement : splitSetup) {
            databaseModule.update(splitSetupElement);
        }
    }

    @Override
    @SneakyThrows
    public BotEntity findBotById(int id) {
        BotEntity bot = isCacheFake() ? null : cache().getCachedBot(id);
        if (bot == null) {
            bot = parseSingleElement(BotEntity.mapResultSet(
                    databaseModule.query(findBotById, id)
            ));
            if (!isCacheFake()) {
                cache().cache(Objects.requireNonNullElseGet(bot, BotEntity::empty));
            }
        }
        return bot;
    }

    @Override
    @SneakyThrows
    public BotEntity findBotByToken(String token) {
        BotEntity bot = isCacheFake() ? null : cache().getCachedBot(token);
        if (bot == null) {
            bot = parseSingleElement(BotEntity.mapResultSet(
                    databaseModule.query(findBotByToken, token)
            ));
            if (!isCacheFake()) {
                cache().cache(Objects.requireNonNullElseGet(bot, BotEntity::empty));
            }
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
    public List<BotPermissionEntity> findPermissionsPageById(int id, int page, int size) {
        return BotPermissionEntity.mapResultSet(
                databaseModule.query(
                        findPermissionsPageById,
                        id, size, page * size
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
                isCacheFake() ? null : cache().getPermission(botId, permission, target);
        if (permissionEntity == null) {
            List<BotPermissionEntity> permissionEntities =
                    BotPermissionEntity.mapResultSet(
                            databaseModule.query(
                                    findPermission,
                                    botId,
                                    permission,
                                    realTarget.getMostSignificantBits(),
                                    realTarget.getLeastSignificantBits()
                            )
                    );
            boolean targetEquals = false;
            if (!permissionEntities.isEmpty()) {
                for (BotPermissionEntity entity : permissionEntities) {
                    if (entity.isRegex()) {
                        permissionEntity = entity;
                        break;
                    }
                    if (permissionEntity == null || permissionEntity.getPermission().length() < entity.getPermission().length()) {
                        permissionEntity = entity;
                        targetEquals = Objects.equals(entity.getTarget(), target);
                        continue;
                    }
                    if (!targetEquals && Objects.equals(entity.getTarget(), target)) {
                        permissionEntity = entity;
                        targetEquals = true;
                    }
                }
            }
            if (!isCacheFake()) {
                PermissionEntity storagePermission = storageModule.getPermission(permission);
                if (permissionEntity == null && storagePermission == null) return null;
                cache().cache(Objects.requireNonNullElseGet(permissionEntity, () ->
                        new BotPermissionEntity(
                                botId,
                                permission,
                                target,
                                false,
                                storagePermission.isEnabled()
                        )
                ));
            }
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
            if (!isCacheFake()) {
                permissions.forEach(cache()::cache);
            }
        }
        return castCollection(permissions);
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
        BotLinkEntity link = isCacheFake() ? null : cache()
                .getCachedLinksWithNullSafety(botId)
                .stream()
                .filter(it -> it.getTarget().equals(target))
                .findAny()
                .orElseGet(() ->
                        cache()
                                .getCachedLinksWithNullSafety(target)
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
            if (!isCacheFake()) {
                cache().cache(link);
            }
        }
        return link;
    }

    @Override
    @SneakyThrows
    public List<BotLinkEntity> findLinksById(int botId) {
        Collection<BotLinkEntity> links = isCacheFake() ? null : cache().getCachedLinks(botId);
        if (links == null) {
            links = BotLinkEntity.mapResultSet(
                    databaseModule.query(findLinksByBot, botId)
            );
            if (!isCacheFake()) {
                links.forEach(cache()::cache);
            }
        }
        return castCollection(links);
    }

    @Override
    @SneakyThrows
    public List<BotLinkEntity> findLinksByTarget(UUID target) {
        Collection<BotLinkEntity> links = isCacheFake() ? null : cache().getCachedLinks(target);
        if (links == null) {
            links = BotLinkEntity.mapResultSet(
                    databaseModule.query(
                            findLinksByTarget,
                            target.getMostSignificantBits(),
                            target.getLeastSignificantBits()
                    )
            );
            if (!isCacheFake()) {
                links.forEach(cache()::cache);
            }
        }
        return castCollection(links);
    }

    @Override
    public void delete(BotEntity botEntity) {
        if (!isCacheFake()) {
            cache()
                    .getCachedLinksWithNullSafety(botEntity.getId())
                    .forEach(cache()::unCache);
            cache()
                    .getCachedPermissionsWithNullSafety(botEntity.getId())
                    .forEach(cache()::unCache);
            cache()
                    .unCache(botEntity);
        }
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
        if (!isCacheFake()) {
            for (BotPermissionEntity permissionEntity : cache()
                    .getCachedPermissionsWithNullSafety(linkEntity.getBotId())
                    .stream()
                    .filter(it -> Objects.equals(it.getTarget(), linkEntity.getTarget()))
                    .collect(Collectors.toList())
            ) {
                cache().unCache(permissionEntity);
            }
            cache()
                    .unCache(linkEntity);
        }
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
                permissionEntity.isToggled(),
                permissionEntity.isRegex()
        );
        if (!isCacheFake()) {
            cache().cache(permissionEntity);
        }
    }

    @Override
    public void save(BotLinkEntity linkEntity) {
        databaseModule.update(
                saveLink,
                linkEntity.getBotId(),
                linkEntity.getTarget().getMostSignificantBits(),
                linkEntity.getTarget().getLeastSignificantBits()
        );
        if (!isCacheFake()) {
            cache().cache(linkEntity);
        }
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
        if (!isCacheFake()) {
            cache().cache(botEntity);
        }
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
        if (!isCacheFake()) {
            cache().cache(permissionEntity);
        }
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
        if (!isCacheFake()) {
            cache().cache(botEntity);
        }
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
                permissionEntity.isRegex(),
                permissionEntity.isToggled()
        );
        if (!isCacheFake()) {
            cache().cache(permissionEntity);
        }
    }

    protected String loadScript(String fileName) throws IOException {
        String realFileName = fileName + ".sql";
        core().mkdirs(String.format("sql/%s", sqlType)); // making dir to give access for changing sql scripts.
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
        if (core().isExistsFile(path)) {
            return new String(core().loadSpecific(path));
        }
        InputStream fixedInputStream = getClass()
                .getClassLoader()
                .getResourceAsStream(path);
        if (fixedInputStream != null) {
            byte[] bytes = FileUtils.readInputStream(fixedInputStream);
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

    protected <T> List<T> castCollection(Collection<T> collection) {
        return collection instanceof List ?
                (List<T>) collection :
                new ArrayList<>(collection);
    }

    protected boolean isCacheFake() {
        return cache() instanceof FakeCacheStorage;
    }

}
