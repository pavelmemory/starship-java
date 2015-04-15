package com.pstr.game.main.configs;

import com.pstr.game.utils.Files;
import org.yaml.snakeyaml.Yaml;

public class GameConf {

    public static GameConf load(String path) {
        Yaml yaml = new Yaml();
        return yaml.loadAs(Files.asString(path), GameConf.class);
    }

    private WindowConf window = null;
    private GameInitialConf gameInitialConf = null;

    public void setWindow(WindowConf window) {
        this.window = window;
    }

    public void setGameInitialConf(GameInitialConf gameInitialConf) {
        this.gameInitialConf = gameInitialConf;
    }

    public WindowConf getWindow() {
        return window;
    }

    public GameInitialConf getGameInitialConf() {
        return gameInitialConf;
    }
}
