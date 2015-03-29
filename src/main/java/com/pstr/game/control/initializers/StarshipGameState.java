package com.pstr.game.control.initializers;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterators;
import com.google.common.collect.Sets;
import com.pstr.game.control.ControllerCommand;
import com.pstr.game.control.actions.Action;
import com.pstr.game.main.GameConf;
import com.pstr.game.object.DoubleBulletFireStategy;
import com.pstr.game.object.GameObject;
import com.pstr.game.object.Starship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.OperationNotSupportedException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StarshipGameState implements GameState {
    private static final Logger LOG = LoggerFactory.getLogger(StarshipGameState.class);
    private boolean initialized = false;
    private final GameConf gameConf;
    private GameObject player;
    private Set<GameObject> objects;
    private Set<GameObject> enemies;

    public StarshipGameState(GameConf gameConf) {
        this.gameConf = gameConf;
    }

    @Override
    public void init() {
        LOG.info("Initialization start");
        if (!initialized) {
            player = new Starship(gameConf);
            objects = Sets.newHashSet();
            enemies = Sets.newHashSet();
        }
        initialized = true;
        LOG.info("Initialization successfully completed");
    }

    @Override
    public List<GameObject> getObjects() {
        if (!initialized) return ImmutableList.of();
        if (!objects.isEmpty()) {
            objects.retainAll(Sets.newHashSet(Iterators.filter(objects.iterator(), GameObject.alivePredicate)));
        }
        if (!enemies.isEmpty()) {
            enemies.retainAll(Sets.newHashSet(Iterators.filter(enemies.iterator(), GameObject.alivePredicate)));
        }
        List<GameObject> gameObjects = ImmutableList.<GameObject>builder().add(player).addAll(objects).addAll(enemies).build();
        return gameObjects;
    }

    @Override
    public void addObject(GameObject object) {
        if (object == null) return;
        switch (object.type()) {
            case STARSHIP:
                enemies.add(object);
                break;
            case BULLET:
                objects.add(object);
                break;
            default:
        }
    }

    @Override
    public GameObject getPlayer() {
        return player;
    }

    @Override
    public void changeBy(Action action) {
        if (!initialized && !action.getControllerCommand().equals(ControllerCommand.START)) return;
        actionCases.get(action.getControllerCommand()).action(action);
    }

    @Override
    public boolean isOnAir() {
        return initialized;
    }

    @Override
    public void addObjects(Set<GameObject> objects) {
        for (GameObject object : objects) {
            addObject(object);
        }
    }

    private interface ActionCase {
        void action(Action action);
    }

    private Map<ControllerCommand, ActionCase> actionCases = ImmutableMap.<ControllerCommand, ActionCase>builder()
            .put(ControllerCommand.ATTACK, new AttackCase())
            .put(ControllerCommand.MOVE, new MoveCase())
            .put(ControllerCommand.START, new StartCase())
            .put(ControllerCommand.STOP, new StopCase())
            .put(ControllerCommand.NONE, new NoneCase())
            .put(ControllerCommand.WEAPON_CHANGED, new WeaponChangedCase())
            .build();

    private class AttackCase implements ActionCase {
        @Override
        public void action(Action action) {
            switch (action.getPressEvent()) {
                case PRESSED:
                    player.setAttackState(true);
                    break;
                case RELEASED:
                    player.setAttackState(false);
                    break;
                case TYPED: break;
                default:
                    throw new RuntimeException(new OperationNotSupportedException());
            }
        }
    }

    private class MoveCase implements ActionCase {
        @Override
        public void action(Action action) {
            switch (action.getPressEvent()) {
                case PRESSED:
                    getPlayer().setDirection(action.event().getKeyCode(), true);
                    break;
                case RELEASED:
                    getPlayer().setDirection(action.event().getKeyCode(), false);
                    break;
                case TYPED: break;
                default:
                    throw new RuntimeException(new OperationNotSupportedException());
            }
        }
    }

    private class StartCase implements ActionCase {
        @Override
        public void action(Action action) {
            StarshipGameState.this.init();
        }
    }

    private class NoneCase implements ActionCase {
        @Override
        public void action(Action action) {
            LOG.warn("State not changed because of undefined action type: " + action);
        }
    }

    private class StopCase implements ActionCase {
        @Override
        public void action(Action action) {
            throw new RuntimeException("YOU NEED TO IMPLEMENT STOP CASE");
        }
    }

    private class WeaponChangedCase implements ActionCase {
        @Override
        public void action(Action action) {
            getPlayer().setWeapon(new DoubleBulletFireStategy());
        }
    }

}
