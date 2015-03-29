package com.pstr.game.control;

import com.pstr.game.control.actions.ActionPressEvent;
import com.pstr.game.control.initializers.GameState;

import java.awt.event.KeyEvent;

public interface Controller {

    void start();

    void stop();

    GameState getState();

    void newEvent(KeyEvent event, ActionPressEvent type);
}
