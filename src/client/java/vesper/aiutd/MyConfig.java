package vesper.aiutd;

import eu.midnightdust.lib.config.MidnightConfig;



public class MyConfig extends MidnightConfig {
        public static final String MAIN = "a_Main";
        public static final String OPTIONAL = "b_Optional";
        public enum LoaderEnum {FABRIC, QUILT, NEOFORGE}

    @Entry(category = MAIN) public static boolean menuAlert = true;
    @Entry(category = MAIN) public static boolean chatAlert = true;
    @Entry(category = MAIN) public static String localVersion = "0.0.0";
    @Entry(category = MAIN) public static String versionAPI = "https://api.modrinth.com/v2/project/<id>/version";
    @Entry(category = MAIN) public static String changelogLink = "https://modrinth.com/modpack/<modpack-URL>/changelog";

    @Entry(category = OPTIONAL) public static boolean multiVersion = false;
    @Entry(category = OPTIONAL) public static boolean multiLoaderBool = false;
    @Entry(category = OPTIONAL) public static LoaderEnum multiLoader = LoaderEnum.FABRIC;
    @Entry(category = OPTIONAL) public static boolean useModpackName = false;
    @Entry(category = OPTIONAL) public static String modpackName = "Default";
    @Entry(category = OPTIONAL) public static boolean useCustomMessage = false;
    @Entry(category = OPTIONAL) public static String customMessage = "This is a custom message!";
    @Entry(category = OPTIONAL) public static boolean linkChangelog = false;

    public static String versionCache = "0.0.0";

    public static boolean shouldIgnore = false;
}
