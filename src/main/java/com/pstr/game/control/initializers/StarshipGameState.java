package com.pstr.game.control.initializers;

import com.google.common.collect.*;
import com.pstr.game.control.ControllerCommand;
import com.pstr.game.control.actions.Action;
import com.pstr.game.main.*;
import com.pstr.game.object.*;
import com.pstr.game.object.GameObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.OperationNotSupportedException;
import java.util.*;


public class StarshipGameState implements GameState {
    private static final Logger LOG = LoggerFactory.getLogger(StarshipGameState.class);
    private boolean initialized = false;
    private final GameConf gameConf;

    private Map<Class<? extends GameObject>, Set<GameObject>> objects = Maps.newHashMap();
    {
        objects.put(Starship.class, new HashSet<GameObject>());
        objects.put(Bullet.class, new HashSet<GameObject>());
        objects.put(EnemyStarship.class, new HashSet<GameObject>());
    }

    public StarshipGameState(GameConf gameConf) {
        this.gameConf = gameConf;
    }

    @Override
    public void init() {
        LOG.info("Initialization start");
        if (!initialized) {
            objects.get(Starship.class).add(Starship.create(gameConf));
            objects.get(EnemyStarship.class).add(EnemyStarship.create(gameConf, 50, 50));
            objects.get(EnemyStarship.class).add(EnemyStarship.create(gameConf, 200, 50));
        }
        initialized = true;
        LOG.info("Initialization successfully completed");
    }

    @Override
    public Set<GameObject> getObjects() {
        if (!initialized) return ImmutableSet.of();
        ImmutableSet.Builder<GameObject> builder = ImmutableSet.builder();
        for (Set<GameObject> o : objects.values()) {
            builder.addAll(o);
        }
        return builder.build();
    }

    @Override
    public void addObject(GameObject object) {
        objects.get(object.getClass()).add(object);
    }

    @Override
    public GameObject getPlayer() {
        return objects.get(Starship.class).iterator().next();
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
    public Set<? extends GameObject> getObjects(Class<? extends GameObject> objectClass) {
        return objects.get(objectClass);
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
                    getPlayer().setAttackState(true);
                    break;
                case RELEASED:
                    getPlayer().setAttackState(false);
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
            FireStrategy weapon = getPlayer().getWeapon();
            if (weapon instanceof  DoubleBulletFireStategy) {
                getPlayer().setWeapon(new SingleBulletFireStrategy());
            }
            else {
                getPlayer().setWeapon(new DoubleBulletFireStategy());
            }
        }
    }

}
