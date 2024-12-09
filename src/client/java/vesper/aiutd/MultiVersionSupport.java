package vesper.aiutd;

import java.util.Objects;
import vesper.aiutd.MyConfig;

public class MultiVersionSupport {
    public static boolean needUpdate;

    // if multiLoader
        // set loader name

        // Parse for First version with identical loader name

        // get its version number

    // if multiVersion
        // Parse for first MP version with identical Minecraft version

        // Get its version number

    // if both
        // Parse for first version with Identical loader and mc version

        // grab its version

    //else
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
