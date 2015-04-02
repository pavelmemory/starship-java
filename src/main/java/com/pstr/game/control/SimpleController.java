package com.pstr.game.control;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.pstr.game.com.pstr.game.draw.Drawer;
import com.pstr.game.com.pstr.game.draw.DrawerCommand;
import com.pstr.game.control.actions.Action;
import com.pstr.game.control.actions.ActionFactory;
import com.pstr.game.control.actions.ActionPressEvent;
import com.pstr.game.control.initializers.*;
import com.pstr.game.main.GameConf;
import com.pstr.game.object.Bullet;
import com.pstr.game.object.EnemyStarship;
import com.pstr.game.object.GameObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.JFrame;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.*;

public class SimpleController implements Controller, ActionListener {
    private static Logger LOG = LoggerFactory.getLogger(SimpleController.class);

    private final GameConf gameConf;
    private final Drawer drawer;
    private Timer timer;
    private final GameAreaInitializer areaInitializer;
    private final GameObjectsInitializer objectsInitializer;
    private final GameState state;
    private final Set<Initializer> initializers;

    private Map<ActionPressEvent, Queue<KeyEvent>> commands = Maps.newHashMap();
    {
        commands.put(ActionPressEvent.PRESSED, new LinkedList<KeyEvent>());
        commands.put(ActionPressEvent.RELEASED, new LinkedList<KeyEvent>());
        commands.put(ActionPressEvent.TYPED, new LinkedList<KeyEvent>());
    }

    public SimpleController(JFrame frame, GameConf gameConf, Drawer drawer) {
        this.gameConf = gameConf;
        this.drawer = drawer;
        registerControllerListener(frame);

        areaInitializer = new StarsLightGameAreaInitializer(gameConf);
        objectsInitializer = new StarshipGameObjectsInitializer(gameConf);
        state = new StarshipGameState(gameConf);
        initializers = ImmutableSet.of(areaInitializer, objectsInitializer);
    }

    private void registerControllerListener(JFrame frame) {
        new KeyboardController(this, frame);
    }

    @Override
    public void start() {
        LOG.info("Start game");
        if (timer == null) timer = new Timer(gameConf.window.updateSpeed, this);
        if (!timer.isRunning()) timer.start();

        for (Initializer initializer : initializers) initializer.init();
    }

    @Override
    public void stop() {
        if (timer == null) {
            LOG.info("Resume game");
            start();
        } else {
            LOG.info("Stop game");
            if (timer != null && timer.isRunning()) timer.stop();
            timer = null;
        }
    }

    @Override
    public GameState getState() {
        return state;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        updateStatePhase();
        if (getState().isOnAir()) {
            movePhase();
            attackPhase();
        }
        redrawPhase();
    }

    private void updateStatePhase() {
        updateStatePhaseByCommand(ActionPressEvent.RELEASED);
        updateStatePhaseByCommand(ActionPressEvent.PRESSED);
        updateStatePhaseByCommand(ActionPressEvent.TYPED);
        calculateDamage();
    }

    private void calculateDamage() {
        Set<? extends GameObject> bullets = getState().getObjects(Bullet.class);
        Set<? extends GameObject> enemies = getState().getObjects(EnemyStarship.class);
        Iterator<? extends GameObject> bulletIterator = bullets.iterator();

        while(bulletIterator.hasNext()) {
            GameObject bullet = bulletIterator.next();
            Iterator<? extends GameObject> enemiesIterator = enemies.iterator();
            while (enemiesIterator.hasNext()) {
                GameObject enemy = enemiesIterator.next();
                if (bullet.damage(enemy)) {
                    bulletIterator.remove();
                    if (!enemy.isAlive()) {
                        enemiesIterator.remove();
                    }
                    break;
                }
            }
        }
    }

    private void updateStatePhaseByCommand(ActionPressEvent actionPressEvent) {
        Queue<KeyEvent> keyEvents = commands.get(actionPressEvent);
        while(!keyEvents.isEmpty()) {
            KeyEvent event = keyEvents.poll();
            ControllerCommand command = ControllerCommand.defineAction(event);
            ActionFactory factory = ActionFactory.getFor(command);
            Action action = factory.create(event, actionPressEvent);
            getState().changeBy(action);
        }
    }

    private void attackPhase() {
        GameObject player = state.getPlayer();
        Set<GameObject> bullets = player.fire();
        state.addObjects(bullets);
    }

    private void movePhase() {
        for (GameObject object : state.getObjects()) {
            object.move();
        }
    }

    private void redrawPhase() {
        drawer.setObjects(state.getObjects());
        drawer.action(DrawerCommand.REDRAW);
    }

    @Override
    public void newEvent(KeyEvent event, ActionPressEvent type) {
        commands.get(type).add(event);
    }
}
