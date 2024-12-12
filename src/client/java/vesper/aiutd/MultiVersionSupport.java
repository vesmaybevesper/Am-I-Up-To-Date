package vesper.aiutd;

import java.util.Objects;

import static vesper.aiutd.MyConfig.multiLoaderBool;
import static vesper.aiutd.MyConfig.multiVersion;


public class MultiVersionSupport {
    public static boolean needUpdate;

    public static void setVersion() {

    if (multiVersion && multiLoaderBool) {
        // Parse for first version with Identical loader and mc version

        // grab its version
    }
    else if (multiLoaderBool){
            // set loader name

            // Parse for First version with identical loader name

            // get its version number
        }
    else if (multiVersion) {
        // Parse for first MP version with identical Minecraft version

        // Get its version number
    }
    else {
        // version Via ModrinthAPI, grabbed in VersionChecker
        String modpackVersion = VersionGrabber.getLatestVersion();

        // Compare local version to version listed via Modrinth API
        if (Objects.equals(MyConfig.localVersion, modpackVersion)) {
            needUpdate = Boolean.FALSE;
        } else {
            needUpdate = Boolean.TRUE;
            }
        }
    }
}
