package dev.vesper.AIUTD.common;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONReader;
import dev.isxander.yacl3.platform.YACLPlatform;
import dev.vesper.AIUTD.AIUTD;
import dev.vesper.AIUTD.config.Config;
import dev.vesper.AIUTD.config.EndUserConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigMigration {
    public static void change(){
        try {
            if (Files.exists(Path.of(YACLPlatform.getConfigDir() + "/aiutd.json5"))){
                AIUTD.LOG.info("Pre-2.5.0 config found. Migrating...!");

                String JSONContent = Files.readString(Path.of(YACLPlatform.getConfigDir() + "/aiutd.json5"));
                JSONObject oldConfig = JSON.parseObject(JSONContent, JSONReader.Feature.AllowUnQuotedFieldNames);

                Config.HANDLER.load();
                Config.menuAlert = Boolean.parseBoolean(oldConfig.getString("menuAlert"));
                Config.chatAlert = Boolean.parseBoolean(oldConfig.getString("chatAlert"));
                Config.multiLoaderBool = Boolean.parseBoolean(oldConfig.getString("multiLoaderBool"));
                Config.multiLoader = Config.LoaderEnum.valueOf(oldConfig.getString("multiLoader"));
                Config.localVersion = oldConfig.getString("localVersion");
                Config.modpackId = oldConfig.getString("versionAPI");
                Config.multiVersion = Boolean.parseBoolean(oldConfig.getString("multiVersion"));
                Config.useModpackName = Boolean.parseBoolean(oldConfig.getString("useModpackName"));
                Config.modpackName = oldConfig.getString("modpackName");
                Config.useCustomMessage = Boolean.parseBoolean(oldConfig.getString("useCustomMessage"));
                Config.customMessage = oldConfig.getString("customMessage");
                Config.linkChangelog = Boolean.parseBoolean(oldConfig.getString("linkChangelog"));
                Config.ignoreColor = Config.colorEnum.valueOf(oldConfig.getString("ignoreColor"));
                Config.messageColor = Config.colorEnum.valueOf(oldConfig.getString("messageColor"));
                Config.changelogColor = Config.colorEnum.valueOf(oldConfig.getString("changelogColor"));
                Config.HANDLER.save();
                AIUTD.LOG.info("Config migrated, please double check it for accuracy!");
                Files.deleteIfExists(Path.of(YACLPlatform.getConfigDir() + "/aiutd.json5"));
                AIUTD.LOG.info("Old config has been deleted");
            }
        } catch (IOException e) {
            AIUTD.LOG.error("Failed to migrate config! Please do so manually!", e);
        }

        try {
            if (Files.exists(Path.of(YACLPlatform.getConfigDir() + "/aiutd-enduser.json5"))){
                AIUTD.LOG.info("Pre-2.5.0 end user config found. Migrating...!");

                String JSONContent = Files.readString(Path.of(YACLPlatform.getConfigDir() + "/aiutd-enduser.json5"));
                JSONObject oldUserConfig = JSON.parseObject(JSONContent, JSONReader.Feature.AllowUnQuotedFieldNames);

                EndUserConfig.USERCONFIG.load();
                EndUserConfig.shouldIgnore = Boolean.parseBoolean(oldUserConfig.getString("shouldIgnore"));

                AIUTD.LOG.info("End user config migrated, please double check it for accuracy!");
                Files.deleteIfExists(Path.of(YACLPlatform.getConfigDir() + "/aiutd.json5"));
                AIUTD.LOG.info("Old end user config has been deleted");
            }
        } catch (IOException e) {
            AIUTD.LOG.error("Failed to migrate end user config! Please do so manually!", e);
        }
    }
}
