package com.pstr.game;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.pstr.game.main.GameConf;
import org.junit.Assert;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;

/**
 * Created by pstr on 3/21/2015.
 */
public class TestGameConf extends Assert{

    @Test
    public void gameConfigFromYamlDowloadRight() throws IOException {
        Yaml yaml = new Yaml();
        GameConf gameConf = yaml.loadAs(Resources.toString(Resources.getResource("game.yaml"), Charsets.UTF_8), GameConf.class);
        assertEquals(600, gameConf.window.width);
        assertEquals(600, gameConf.window.height);
        assertEquals(false, gameConf.window.resizable);
        assertEquals(50, gameConf.window.updateSpeed);
    }


}
