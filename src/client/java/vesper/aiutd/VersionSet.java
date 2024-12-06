package vesper.aiutd;

import java.util.Objects;

public class VersionSet {
    public static boolean needUpdate;

    public static void setVersion() {
        // version Via ModrinthAPI, grabbed in VersionChecker
        String modpackVersion = VersionGrabber.getLatestVersion();
        //Local version
        String localVersion = MyConfig.localVersion;

        // Compare local version to version listed via Modrinth API
        if (Objects.equals(localVersion, modpackVersion)) {
            needUpdate = Boolean.FALSE;
        } else {
            needUpdate = Boolean.TRUE;
        }
    }
}
