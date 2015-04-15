package com.pstr.game.control.actions;

import com.pstr.game.control.ControllerCommand;

import java.awt.event.KeyEvent;

public class BaseAction implements Action {

    private final ActionPressEvent pressEvent;
    private final KeyEvent event;
    private final ControllerCommand command;

    public BaseAction(ControllerCommand command, KeyEvent event, ActionPressEvent pressEvent) {
        this.event = event;
        this.command = command;
        this.pressEvent = pressEvent;
    }

    public ActionPressEvent getPressEvent() {
        return pressEvent;
    }

    public KeyEvent event() {
        return event;
    }

    public ControllerCommand getControllerCommand() {
        return command;
    }

}
