package com.pstr.game.control.initializers;

import com.pstr.game.main.GameConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StarshipGameObjectsInitializer implements GameObjectsInitializer {
    private static final Logger LOG = LoggerFactory.getLogger(StarshipGameObjectsInitializer.class);
    private boolean initialized = false;

    public StarshipGameObjectsInitializer(GameConf gameConf) {

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
