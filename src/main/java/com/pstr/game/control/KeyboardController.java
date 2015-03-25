package com.pstr.game.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardController implements KeyListener {

    private static final Logger LOG = LoggerFactory.getLogger(KeyboardController.class);
    private final Controller controller;

    public KeyboardController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        LOG.info("Event keyTyped: " + e);
        int keyCode = e.getKeyCode();
        ControllerCommand actionCommand = ControllerCommand.getActionCommand(keyCode);
        if (actionCommand != null) actionCommand.actionTyped(controller);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        LOG.info("Event keyPressed: " + e);
        int keyCode = e.getKeyCode();
        ControllerCommand actionCommand = ControllerCommand.getActionCommand(keyCode);
        if (actionCommand != null) actionCommand.actionPress(controller, keyCode);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        LOG.info("Event keyReleased: " + e);
        int keyCode = e.getKeyCode();
        ControllerCommand actionCommand = ControllerCommand.getActionCommand(keyCode);
        if (actionCommand != null) actionCommand.actionReleased(controller, keyCode);
    }
}
