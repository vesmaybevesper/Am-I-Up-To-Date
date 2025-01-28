package vesper.aiutd;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.minecraft.client.MinecraftClient;

import java.util.Objects;

import static vesper.aiutd.MyConfig.*;
import static vesper.aiutd.MyConfig.LoaderEnum.*;


public class MultiVersionSupport {
    public static boolean needUpdate;
    public static int loaderLocation = 0;
    public static String localLoader = String.valueOf(multiLoader);
    public static String  modpackVersion = "";
    public static String versionLoader = "";
    public static String APIMcVersion = "";
    public static String clientVersion;
    public static void setVersion() {
        StringBuilder result = new StringBuilder();
        JsonArray jsonArray = new JsonArray();
        clientVersion = MinecraftClient.getInstance().getGameVersion();

    if (multiVersion && multiLoaderBool) {
        // set which loader to look for
        if (multiLoader == FABRIC) {
            localLoader = "fabric";
        } else if (multiLoader == QUILT) {
            localLoader = "quilt";
        } else if (multiLoader == NEOFORGE) {
            localLoader = "neoforge";
        }
        // Parse for first version with Identical loader and mc version
        while (!Objects.equals(localLoader, versionLoader) && !Objects.equals(clientVersion, APIMcVersion)) {
            JsonElement getVersionElement = jsonArray.get(loaderLocation);
            APIMcVersion = getVersionElement.getAsJsonObject().get("game_versions").getAsString();
            versionLoader = getVersionElement.getAsJsonObject().get("loaders").getAsString();
            loaderLocation = loaderLocation + 1;
            System.out.println(loaderLocation);
        }
        // grab its version
        modpackVersion = VersionGrabber.getLatestVersion(loaderLocation);
    }
    else if (multiLoaderBool){
        // set which loader to look for
        if (multiLoader == FABRIC) {
            localLoader = "fabric";
        } else if (multiLoader == QUILT) {
            localLoader = "quilt";
        } else if (multiLoader == NEOFORGE) {
            localLoader = "neoforge";
        }
            // Parse for First version with identical loader name
            while (!Objects.equals(versionLoader, localLoader)){
                JsonElement getVersionElement = jsonArray.get(loaderLocation);
                APIMcVersion = getVersionElement.getAsJsonObject().get("game_versions").getAsString();
                loaderLocation = loaderLocation + 1;
                System.out.println(loaderLocation);
            }
            // get its version number
        modpackVersion = VersionGrabber.getLatestVersion(loaderLocation);
        }
    else if (multiVersion) {
        // Parse for first MP version with identical Minecraft version
        while (!Objects.equals(clientVersion, APIMcVersion)) {
            JsonElement getVersionElement = jsonArray.get(loaderLocation);
            APIMcVersion = getVersionElement.getAsJsonObject().get("game_versions").getAsString();
            loaderLocation = loaderLocation + 1;
            System.out.println(loaderLocation);
        }
        // Get its version number
        modpackVersion = VersionGrabber.getLatestVersion(loaderLocation);

    }
    else {
        // version Via ModrinthAPI, grabbed in VersionChecker
        modpackVersion = VersionGrabber.getLatestVersion(0);
        }
        // Compare local version to version listed via Modrinth API
        if (Objects.equals(localVersion, modpackVersion)) {
            needUpdate = Boolean.FALSE;
        } else {
            needUpdate = Boolean.TRUE;
        }
    }
}
