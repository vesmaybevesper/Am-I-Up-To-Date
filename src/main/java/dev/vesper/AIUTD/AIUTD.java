package dev.vesper.AIUTD;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AIUTD {

    public static final String MOD_ID = "aiutd";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_ID);

    public static void init() {
        LOG.info("Initializing {} on {}", MOD_ID, Platform.INSTANCE.loader());
    }

}
