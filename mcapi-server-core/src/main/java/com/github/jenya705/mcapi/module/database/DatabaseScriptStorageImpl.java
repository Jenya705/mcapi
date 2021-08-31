package com.github.jenya705.mcapi.module.database;

import com.github.jenya705.mcapi.BaseCommon;
import com.github.jenya705.mcapi.entity.BotEntity;
import com.github.jenya705.mcapi.entity.BotLinkEntity;
import com.github.jenya705.mcapi.entity.BotPermissionEntity;
import com.github.jenya705.mcapi.util.FileUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.UUID;

/**
 * @author Jenya705
 */
@Getter(AccessLevel.PROTECTED)
public class DatabaseScriptStorageImpl implements DatabaseScriptStorage, BaseCommon {

    private final DatabaseModule databaseModule;
    private final String sqlType;

    // Scripts
    private final String setup;
    private final String findAllBots;
    private final String findBotById;
    private final String findBotByToken;
    private final String findBotsByOwner;
    private final String findBotsPageByOwner;
    private final String findPermission;
    private final String findPermissionsById;
    private final String findPermissionsByIdAndTarget;
    private final String findAllLinks;
    private final String findLink;
    private final String findLinksByBot;
    private final String findLinksByTarget;
    private final String deleteLinksByBotId;
    private final String deletePermissionsByBotId;
    private final String deleteBot;
    private final String deleteLink;
    private final String saveBot;
    private final String saveLink;
    private final String savePermission;
    private final String updateBot;
    private final String updatePermission;

    public DatabaseScriptStorageImpl(DatabaseModule databaseModule, String sqlType) throws IOException {
        this.databaseModule = databaseModule;
        this.sqlType = sqlType;
        setup = loadScript("setup");
        findAllBots = loadScript("find_all_bots");
        findBotById = loadScript("find_bot_by_id");
        findBotByToken = loadScript("find_bot_by_token");
        findBotsByOwner = loadScript("find_bots_by_owner");
        findBotsPageByOwner = loadScript("find_page_ordered_bots_by_owner");
        findPermission = loadScript("find_permission");
        findPermissionsById = loadScript("find_permissions_by_id");
        findPermissionsByIdAndTarget = loadScript("find_permissions_by_id_and_target");
        findAllLinks = loadScript("find_all_links");
        findLink = loadScript("find_link");
        findLinksByBot = loadScript("find_links_by_bot");
        findLinksByTarget = loadScript("find_links_by_target");
        deleteLinksByBotId = loadScript("delete_bot_links");
        deletePermissionsByBotId = loadScript("delete_bot_permissions");
        deleteBot = loadScript("delete_bot");
        deleteLink = loadScript("delete_link");
        saveBot = loadScript("save_bot");
        saveLink = loadScript("save_link");
        savePermission = loadScript("save_permission");
        updateBot = loadScript("update_bot");
        updatePermission = loadScript("update_permission");
    }

    @Override
    public void setup() {
        String[] splitSetup = setup.split("ANOTHEROPERATION");
        for (String splitSetupElement: splitSetup) databaseModule.update(splitSetupElement);
    }

    @Override
    @SneakyThrows
    public BotEntity findBotById(int id) {
        return parseSingleElement(BotEntity.mapResultSet(
                databaseModule.query(findBotById, id)
        ));
    }

    @Override
    @SneakyThrows
    public BotEntity findBotByToken(String token) {
        return parseSingleElement(BotEntity.mapResultSet(
                databaseModule.query(findBotByToken, token)
        ));
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
    public BotPermissionEntity findPermission(long botId, String permission, UUID target) {
        UUID realTarget = parseTarget(target);
        return parseSingleElement(
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
    }

    @Override
    @SneakyThrows
    public List<BotPermissionEntity> findPermissionsById(long botId) {
        return BotPermissionEntity.mapResultSet(
                databaseModule.query(findPermissionsById, botId)
        );
    }

    @Override
    @SneakyThrows
    public List<BotPermissionEntity> findPermissionsByIdAndTarget(long botId, UUID target) {
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
    public BotLinkEntity findLink(long botId, UUID target) {
        return parseSingleElement(BotLinkEntity.mapResultSet(
                databaseModule.query(
                        findLink,
                        botId,
                        target.getMostSignificantBits(),
                        target.getLeastSignificantBits()
                )
        ));
    }

    @Override
    @SneakyThrows
    public List<BotLinkEntity> findLinksById(long botId) {
        return BotLinkEntity.mapResultSet(
                databaseModule.query(findLinksByBot, botId)
        );
    }

    @Override
    @SneakyThrows
    public List<BotLinkEntity> findLinksByTarget(UUID target) {
        return BotLinkEntity.mapResultSet(
                databaseModule.query(
                        findLinksByTarget,
                        target.getMostSignificantBits(),
                        target.getLeastSignificantBits()
                )
        );
    }

    @Override
    public void delete(BotEntity botEntity) {
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
        databaseModule.update(
                deleteLink,
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
    }

    @Override
    public void save(BotLinkEntity linkEntity) {
        databaseModule.update(
                saveLink,
                linkEntity.getBotId(),
                linkEntity.getTarget().getMostSignificantBits(),
                linkEntity.getTarget().getLeastSignificantBits()
        );
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
}
