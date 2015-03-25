package com.pstr.game.main;

import com.pstr.game.utils.Files;
import org.yaml.snakeyaml.Yaml;

public class GameConf {

    public static GameConf load(String path) {
        Yaml yaml = new Yaml();
        return yaml.loadAs(Files.asString(path), GameConf.class);
    }

    public Window window = null;
    public Game game = null;
}
