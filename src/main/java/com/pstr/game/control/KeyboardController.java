package com.pstr.game.control;

import com.pstr.game.control.actions.ActionPressEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardController implements KeyListener {

    private static final Logger LOG = LoggerFactory.getLogger(KeyboardController.class);
    private final Controller controller;

    public KeyboardController(Controller controller, Frame frame) {
        frame.addKeyListener(this);
        this.controller = controller;
    }

    @Override
    public void keyTyped(KeyEvent event) {
        LOG.info("Event keyTyped: " + event);
        controller.newEvent(event, ActionPressEvent.TYPED);
    }

    @Override
    public void keyPressed(KeyEvent event) {
        LOG.info("Event keyPressed: " + event);
        controller.newEvent(event, ActionPressEvent.PRESSED);
    }

    @Override
    public void keyReleased(KeyEvent event) {
        LOG.info("Event keyReleased: " + event);
        controller.newEvent(event, ActionPressEvent.RELEASED);
    }
}
