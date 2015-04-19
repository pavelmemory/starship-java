package com.pstr.game;

import com.google.common.base.Charsets;
import com.google.common.collect.Sets;
import com.google.common.io.Resources;
import com.pstr.game.control.ii.LevelInterpreter;
import com.pstr.game.main.configs.GameConf;
import com.pstr.game.main.configs.LevelConf;
import com.pstr.game.main.configs.YamlObjectConfProvider;
import com.pstr.game.object.Starship;
import org.junit.Assert;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class TestLevelInterpreter extends Assert {

    @Test
    public void test() throws IOException, InterruptedException {
        LevelConf level1 = new Yaml().loadAs(
                Resources.toString(Resources.getResource("levels/level_1.yaml"), Charsets.UTF_8), LevelConf.class);
        GameConf gameConf = new Yaml().loadAs(Resources.toString(Resources.getResource("game.yaml"), Charsets.UTF_8), GameConf.class);

        LevelInterpreter interpreter = new LevelInterpreter(level1, new YamlObjectConfProvider(gameConf), gameConf);
        interpreter.start();
        TimeUnit.SECONDS.sleep(1);
        assertEquals("Amount of frames not correct", 3, level1.getFrames().size());

        // first frame
        Set<Starship> enemies = interpreter.getEnemiesAndToEmpty();
        assertEquals("Incorrect number of enemies", 3, enemies.size());
        Map<Integer, Set<Integer>> moveCommands = interpreter.getMoveCommandsAndToEmpty();
        assertEquals("Incorrect number of move commands", 3, moveCommands.size());
        assertTrue("Incorrect indexes of move commands", moveCommands.keySet().equals(Sets.newHashSet(0, 1, 2)));
        Set<Integer> moveSet = moveCommands.get(0);
        assertEquals("Move set is incorrect", Sets.newHashSet(39, 40), moveSet);
        moveSet = moveCommands.get(2);
        assertEquals("Move set is incorrect", Sets.newHashSet(40), moveSet);
        TimeUnit.SECONDS.sleep(level1.getFrames().get(0).getSleep() + 1);

        // second frame
        enemies = interpreter.getEnemiesAndToEmpty();
        assertEquals("Incorrect number of enemies", 0, enemies.size());
        moveCommands = interpreter.getMoveCommandsAndToEmpty();
        assertEquals("Incorrect number of move commands", 3, moveCommands.size());
        assertTrue("Incorrect indexes of move commands", moveCommands.keySet().equals(Sets.newHashSet(0, 1, 2)));
        moveSet = moveCommands.get(0);
        assertEquals("Move set is incorrect", Sets.newHashSet(37, 40), moveSet);
        moveSet = moveCommands.get(2);
        assertEquals("Move set is incorrect", Sets.newHashSet(40), moveSet);
        TimeUnit.SECONDS.sleep(level1.getFrames().get(0).getSleep() + 1);

        TimeUnit.SECONDS.sleep(1);
        interpreter.interrupt();
        interpreter.join();
        Assert.assertTrue(true);
    }
}
