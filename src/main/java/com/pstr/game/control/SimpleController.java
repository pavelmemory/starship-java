package com.pstr.game.control;

import com.google.common.collect.ImmutableSet;
import com.pstr.game.com.pstr.game.draw.Drawer;
import com.pstr.game.com.pstr.game.draw.DrawerCommand;
import com.pstr.game.control.initializers.*;
import com.pstr.game.main.GameConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.JFrame;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

public class SimpleController implements Controller, ActionListener {
    private static Logger LOG = LoggerFactory.getLogger(SimpleController.class);

    private final GameConf gameConf;
    private final Drawer drawer;
    private Timer timer;
    private final GameAreaInitializer areaInitializer;
    private final GameObjectsInitializer objectsInitializer;
    private final GameState state;
    private final Set<Initializer> initializers;

    public SimpleController(JFrame frame, GameConf gameConf, Drawer drawer) {
        frame.addKeyListener(new KeyboardController(this));
        this.gameConf = gameConf;
        this.drawer = drawer;

        areaInitializer = new StarsLightGameAreaInitializer(gameConf);
        objectsInitializer = new StarshipGameObjectsInitializer(gameConf);
        state = new StarshipGameState(gameConf);
        initializers = ImmutableSet.of(areaInitializer, objectsInitializer, state);
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
        drawer.setObjects(state.getObjects());
        drawer.action(DrawerCommand.REDRAW);
    }
}
