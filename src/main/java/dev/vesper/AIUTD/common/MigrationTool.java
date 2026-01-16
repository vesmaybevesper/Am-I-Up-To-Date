package dev.vesper.AIUTD.common;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import dev.isxander.yacl3.platform.YACLPlatform;
import dev.vesper.AIUTD.AIUTD;
import dev.vesper.AIUTD.config.Config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;

import static dev.vesper.AIUTD.config.Config.migrated;

public class MigrationTool {
    public static void copy() {
        try {
            if (Files.exists(Path.of(YACLPlatform.getConfigDir() + "/aiutd.json")) && !migrated) {

                // This try block *should* prevent configs that have been used from being overwritten with old info
                try {
                    BasicFileAttributes attributes = Files.readAttributes(Path.of(YACLPlatform.getConfigDir() + "/aiutd.json5"), BasicFileAttributes.class);
                    Instant lastModified = attributes.lastModifiedTime().toInstant();
                    Instant now = Instant.now();

                    long secSinceMod = now.getEpochSecond() - lastModified.getEpochSecond();
                    long minSinceMod = secSinceMod / 60;

                    if (minSinceMod > 5) {
                        AIUTD.LOG.info("Activity detected on YACL Config, assuming active, not migrating old config");
                        Files.deleteIfExists(Path.of(YACLPlatform.getConfigDir() + "/aiutd.json"));
                        AIUTD.LOG.info("Old config has been deleted");
                        migrated = true;
                        return;
                    }
                } catch (IOException e) {
                    AIUTD.LOG.info("Failed to check new config file attributes, skipping migration to be safe!");
                    return;
                }

                AIUTD.LOG.info("Pre-2.0.0 config found. Migrating it to new YACL Config. This should only take a moment...");

                String jsonContent = Files.readString(Path.of(YACLPlatform.getConfigDir() + "/aiutd.json"));
                JSONObject oldConfig = JSON.parseObject(jsonContent);

                Config.HANDLER.load();
                Config.menuAlert = Boolean.parseBoolean(oldConfig.getString("menuAlert"));
                Config.chatAlert = Boolean.parseBoolean(oldConfig.getString("chatAlert"));
                Config.multiLoaderBool = Boolean.parseBoolean(oldConfig.getString("multiLoaderBool"));
                Config.multiLoader = Config.LoaderEnum.valueOf(oldConfig.getString("multiLoader"));
                Config.localVersion = oldConfig.getString("localVersion");
                Config.versionAPI = oldConfig.getString("versionAPI");
                Config.changelogLink = oldConfig.getString("changelogLink");
                Config.multiVersion = Boolean.parseBoolean(oldConfig.getString("multiVersion"));
                Config.useModpackName = Boolean.parseBoolean(oldConfig.getString("useModpackName"));
                Config.modpackName = oldConfig.getString("modpackName");
                Config.useCustomMessage = Boolean.parseBoolean(oldConfig.getString("useCustomMessage"));
                Config.customMessage = oldConfig.getString("customMessage");
                Config.linkChangelog = Boolean.parseBoolean(oldConfig.getString("linkChangelog"));
                migrated = true;
                Config.HANDLER.save();
                AIUTD.LOG.info("Config migrated, please double check it for accuracy");

                Files.deleteIfExists(Path.of(YACLPlatform.getConfigDir() + "/aiutd.json"));
                AIUTD.LOG.info("Old config has been deleted");
            }
        } catch (Exception e) {
            AIUTD.LOG.warn("Failed to migrate config! Please do so manually!");
        }
    }
}