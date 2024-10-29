package vesper.aiutd;

import eu.midnightdust.lib.config.MidnightConfig;

public class MyConfig extends MidnightConfig {


    @Entry public static boolean menuAlert = true;
    @Entry public static boolean chatAlert = true;
    @Entry public static String localVersion = "0.0.0";
    @Entry public static String versionAPI = "https://api.modrinth.com/v2/project/<id>/version";
    @Entry public static String changelogLink = "https://modrinth.com/modpack/<modpack-URL>/changelog";
}
