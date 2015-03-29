package com.pstr.game.control.initializers;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import com.google.common.collect.Sets;
import com.pstr.game.control.actions.Action;
import com.pstr.game.main.GameConf;
import com.pstr.game.object.GameObject;
import com.pstr.game.object.Starship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.OperationNotSupportedException;
import java.util.List;
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
        if (!initialized) return ImmutableList.<GameObject>of();
        if (!objects.isEmpty()) {
            objects.retainAll(Sets.newHashSet(Iterators.filter(objects.iterator(), GameObject.alivePredicate)));
        }
        if (!enemies.isEmpty()) {
            enemies.retainAll(Sets.newHashSet(Iterators.filter(enemies.iterator(), GameObject.alivePredicate)));
        }
        List<GameObject> gameObjects = ImmutableList.<GameObject>builder().add(player).addAll(objects).addAll(enemies).build();
//        LOG.info("Current state objects size: {} element(s)", gameObjects.size());
        return gameObjects;
    }

    @Override
    public void addObject(GameObject object) {
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
        switch (action.getControllerCommand()) {
            case MOVE:
                moveCase(action);
                break;

            case ATTACK:
                attackCase(action);
                break;

            case START:
                LOG.info("START!");
                init();
                break;

            case STOP:
                break;

            case NONE:
                LOG.warn("State not changed because of undefined action type: " + action);
                break;
            default:
                throw new RuntimeException(new OperationNotSupportedException());
        }
    }

    @Override
    public boolean isOnAir() {
        return initialized;
    }

    private void moveCase(Action action) {
        switch (action.getPressEvent()) {
            case PRESSED:
                getPlayer().move(action.event().getKeyCode(), true);
                break;
            case RELEASED:
                getPlayer().move(action.event().getKeyCode(), false);
                break;
            case TYPED:
                break;
            default:
                throw new RuntimeException(new OperationNotSupportedException());
        }
    }


    private void attackCase(Action action) {
        switch (action.getPressEvent()) {
            case PRESSED:
                player.setAttackState(true);
                break;
            case RELEASED:
                player.setAttackState(false);
                break;
            case TYPED:
//                player.setAttackState(false);
                GameObject fire = player.fire();
                addObject(fire);
                break;
            default:
                throw new RuntimeException(new OperationNotSupportedException());
        }
    }
}
