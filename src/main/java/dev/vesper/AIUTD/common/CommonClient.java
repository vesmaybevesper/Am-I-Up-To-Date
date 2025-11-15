package dev.vesper.AIUTD.common;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

import static dev.vesper.AIUTD.config.Config.localVersion;

public class CommonClient {
    public static void init(){
        try {
            if (!UpdateChecker.hasChecked){
                UpdateChecker.needUpdate = !Objects.equals(localVersion, UpdateChecker.getVersionNumber());
                UpdateChecker.hasChecked = true;
            }
        } catch (URISyntaxException | IOException ignored) {}
    }
}