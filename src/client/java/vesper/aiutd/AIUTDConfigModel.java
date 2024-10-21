package vesper.aiutd;

import io.wispforest.owo.config.annotation.Config;

@Config(name = "aiutd-mod-config", wrapperName = "AIUTDConfig")
public class AIUTDConfigModel {
    public static String checkLocalVersion ="1.1.4";
    public static String VersionAPI = "https://api.modrinth.com/v2/project/ihN5bmrg/version";
    public static String changelogLink = "https://modrinth.com/modpack/vespers-vanilla-enhanced/changelog";
}
