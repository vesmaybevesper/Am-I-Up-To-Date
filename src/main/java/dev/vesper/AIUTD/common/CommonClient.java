package dev.vesper.AIUTD.common;

import dev.vesper.AIUTD.config.EndUserConfig;

import java.io.IOException;
import java.net.URISyntaxException;

public class CommonClient {
    public void init(){
        try {
            if (!UpdateChecker.hasChecked){
                versionCheck.setVersion();
                versionCheck.hasChecked = true;
            }
        } catch (URISyntaxException | IOException ignored) {}
        chatMessage();
    }
}
