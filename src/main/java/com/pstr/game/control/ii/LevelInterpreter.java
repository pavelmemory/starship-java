package com.pstr.game.control.ii;

import com.google.common.collect.Sets;
import com.pstr.game.main.configs.*;
import com.pstr.game.object.GameObject;
import com.pstr.game.object.Starship;
import com.pstr.game.object.StarshipBuilder;
import com.pstr.game.object.attack.WeaponType;
import com.pstr.game.object.attack.strategy.FireStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class LevelInterpreter extends Thread {

    private static final Logger LOG = LoggerFactory.getLogger(LevelInterpreter.class);

    private final ObjectConfProvider provider;
    private final GameConf gameConf;
    private LevelConf currentLevel;
    private LevelFrame frame;

    private volatile Map<Integer, Set<Integer>> moveCommands = Collections.emptyMap();
    private volatile Set<Starship> enemies = Collections.emptySet();
    private volatile Set<GameObject> objects = Collections.emptySet();

    public LevelInterpreter(LevelConf currentLevel, ObjectConfProvider provider, GameConf gameConf) {
        this.provider = provider;
        this.gameConf = gameConf;
        this.currentLevel = currentLevel;
        setDaemon(true);
    }

    public void changeLevel(LevelConf levelConf) {
        synchronized (currentLevel) {
            currentLevel.notifyAll();
            currentLevel = levelConf;
        }
    }

    protected void interpreterMoveCommands() {
//        int iter = 0;
//        Map<Integer, Set<Integer>> mc = Maps.newHashMap();
//        for (EnemyConf enemyConf : frame.getEnemiesConf()) {
//            mc.put(iter, Sets.newHashSet(enemyConf.getDirection().iterator()));
//            ++iter;
//        }
//        moveCommands = mc;
//        LOG.info("Move commands");
    }

    protected void interpreterCreateCommands() {
        Set<Starship> starships = Sets.newHashSetWithExpectedSize(frame.getEnemiesConf().size());
        for (EnemyConf enemyConf : frame.getEnemiesConf()) {
            if (enemyConf.getType() == null) break;

            StarshipBuilder starshipBuilder = new StarshipBuilder(provider, gameConf);
            starshipBuilder.gameObjectConfType(enemyConf.getType())
                    .weaponType(WeaponType.YELLOW_BULLET)
                    .fireStrategyType(FireStrategy.Type.SINGLE)
                    .position(enemyConf.getStart());
            starshipBuilder.direction(enemyConf.getDirection());
            Starship starship = starshipBuilder.build();
            starship.updateAttackState(true);
            for (int direction : enemyConf.getDirection()) starship.updateDirection(direction, true);
            starships.add(starship);
        }
        enemies = starships;
        LOG.info("Create commands");
    }

    protected void frameToSleepState() {
        try {
            wait(TimeUnit.MILLISECONDS.convert(frame.getSleep(), TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            LOG.info("Thread was interrupted on level: {} at frame {}", currentLevel, frame);
            interrupt();
        }
    }

    public void run() {
        while (true && !isInterrupted()) {
            Iterator<LevelFrame> frameIterator = currentLevel.iterator();
            for (frame = frameIterator.next();
                 frameIterator.hasNext() && !isInterrupted();
                 frame = frameIterator.next()) {
                LOG.info("Current frame: {}", frame);
                synchronized (this) {
                    interpreterCreateCommands();
                    interpreterMoveCommands();
                    frameToSleepState();
                }
            }
            synchronized (currentLevel) {
                try {
                    currentLevel.wait();
                } catch (InterruptedException e) {
                    LOG.info("Thread was interrupted on level: {}", currentLevel);
                    interrupt();
                }
            }
        }
    }

    public synchronized Map<Integer, Set<Integer>> getMoveCommandsAndToEmpty() {
        Map<Integer, Set<Integer>> ret = moveCommands;
        moveCommands = Collections.emptyMap();
        return ret;
    }

    public synchronized Set<Starship> getEnemiesAndToEmpty() {
        Set<Starship> ret = enemies;
        enemies = Collections.emptySet();
        return ret;
    }

    public synchronized Set<GameObject> getObjectsAndToEmpty() {
        Set<GameObject> ret = objects;
        objects = Collections.emptySet();
        return ret;
    }
}
