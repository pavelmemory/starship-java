package com.pstr.game.control.actions;

import com.pstr.game.control.ControllerCommand;

import java.awt.event.KeyEvent;

public interface Action {

    ControllerCommand getControllerCommand();

    ActionPressEvent getPressEvent();

    KeyEvent event();
}
