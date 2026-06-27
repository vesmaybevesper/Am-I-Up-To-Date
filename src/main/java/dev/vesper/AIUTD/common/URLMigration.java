package dev.vesper.AIUTD.common;

import dev.vesper.AIUTD.config.Config;

public class URLMigration {
    public static void removeExcess() {
        Config.HANDLER.load();
        if (Config.changelogLink.contains("https://api.modrinth.com/v2/project/")) {
            Config.changelogLink = Config.changelogLink.replace("https://api.modrinth.com/v2/project/", "");
            Config.changelogLink = Config.changelogLink.replace("/version?include_changelog=false", "");
            Config.changelogLink = Config.changelogLink.trim();
            Config.HANDLER.save();
        }
    }
}
