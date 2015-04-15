package com.pstr.game.control.initializers;

import com.pstr.game.main.configs.GameConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StarsLightGameAreaInitializer implements GameAreaInitializer {
    private static final Logger LOG = LoggerFactory.getLogger(StarsLightGameAreaInitializer.class);

    private boolean initialized = false;

    public StarsLightGameAreaInitializer(GameConf gameConf) {

    }

    @Override
    public void init() {
        LOG.info("Initialization start");
        if (!initialized) {

        }
        initialized = true;
        LOG.info("Initialization successfully completed");
    }
}
