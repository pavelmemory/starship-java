package com.pstr.game.control;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.pstr.game.com.pstr.game.draw.Drawer;
import com.pstr.game.com.pstr.game.draw.DrawerCommand;
import com.pstr.game.control.actions.Action;
import com.pstr.game.control.actions.ActionFactory;
import com.pstr.game.control.actions.ActionPressEvent;
import com.pstr.game.control.ii.LevelInterpreter;
import com.pstr.game.control.initializers.*;
import com.pstr.game.main.configs.GameConf;
import com.pstr.game.main.configs.YamlLevelConfProvider;
import com.pstr.game.main.configs.YamlObjectConfProvider;
import com.pstr.game.object.GameObject;
import com.pstr.game.object.Starship;
import com.pstr.game.object.attack.damage.Ammo;
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
    private final LevelInterpreter levelInterpreter;
    private final GameAreaInitializer areaInitializer;
    private final GameObjectsInitializer objectsInitializer;
    private final Set<Initializer> initializers;
    private final GameState state;
    private final YamlLevelConfProvider levelConfProvider;

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
        levelConfProvider = new YamlLevelConfProvider("levels");
        levelInterpreter = new LevelInterpreter(levelConfProvider.next(), new YamlObjectConfProvider(gameConf), gameConf);
    }

    private void registerControllerListener(JFrame frame) {
        new KeyboardController(this, frame);
    }

    @Override
    public void start() {
        LOG.info("Start game");
        if (timer == null) timer = new Timer(gameConf.getWindow().updateSpeed, this);
        if (!timer.isRunning()) timer.start();

        for (Initializer initializer : initializers) initializer.init();
        levelInterpreter.start();
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
            levelInterpreter.interrupt();
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

        getState().getEnemies().addAll(levelInterpreter.getEnemiesAndToEmpty());
        getState().getObjects().addAll(levelInterpreter.getObjectsAndToEmpty());
        calculateDamage();
    }

    private void calculateDamage() {
        Set<Ammo> ammos = getState().getAmmos();
        Set<Starship> enemies = getState().getEnemies();
        Iterator<Ammo> ammoIterator = ammos.iterator();

        while(ammoIterator.hasNext()) {
            boolean ammoRemoved = false;
            Ammo ammo = ammoIterator.next();
            Iterator<Starship> enemiesIterator = enemies.iterator();
            while (enemiesIterator.hasNext()) {
                Starship enemy = enemiesIterator.next();
                if (ammo.damage(enemy)) {
                    ammoIterator.remove();
                    ammoRemoved = true;
                    if (!enemy.isAlive()) {
                        enemiesIterator.remove();
                    }
                    break;
                }
            }
            if (!ammoRemoved && ammo.damage(state.getPlayer())) {
                ammoIterator.remove();
            }
        }
    }

    private void updateStatePhaseByCommand(ActionPressEvent actionPressEvent) {
        Queue<KeyEvent> keyEvents = commands.get(actionPressEvent);
        while(!keyEvents.isEmpty()) {
            KeyEvent event = keyEvents.poll();
            ControllerCommand command = ControllerCommand.defineCommand(event);
            Action action = ActionFactory.create(command, event, actionPressEvent);
            getState().changeBy(action);
        }
    }

    private void attackPhase() {
        for (Starship enemy : getState().getEnemies()) {
            starshipAttack(enemy, KeyEvent.VK_DOWN);
        }
        starshipAttack(state.getPlayer(), KeyEvent.VK_UP);
    }

    private void starshipAttack(Starship starship, int direction) {
        if (starship.isInAttackState()) {
            Set<Ammo> ammos = starship.attack(direction);
            state.addAllAmmo(ammos);
        }
    }

    private void movePhase() {
        for (GameObject object : state.getAllGameObjects()) {
            object.move();
        }
    }

    private void redrawPhase() {
        drawer.setObjects(state.getAllGameObjects());
        drawer.action(DrawerCommand.REDRAW);
    }

    @Override
    public void newEvent(KeyEvent event, ActionPressEvent type) {
        commands.get(type).add(event);
    }
}
