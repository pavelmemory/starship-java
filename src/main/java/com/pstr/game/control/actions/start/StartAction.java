package com.pstr.game.control.actions.start;

import com.pstr.game.control.ControllerCommand;
import com.pstr.game.control.actions.Action;
import com.pstr.game.control.actions.ActionPressEvent;

import java.awt.event.KeyEvent;

public class StartAction implements Action {

    private final ActionPressEvent pressEvent;
    private final KeyEvent event;

    @Override
    public ControllerCommand getControllerCommand() {
        return ControllerCommand.START;
    }

    public StartAction(KeyEvent event, ActionPressEvent pressEvent) {
        this.event = event;
        this.pressEvent = pressEvent;
    }

    @Override
    public ActionPressEvent getPressEvent() {
        return pressEvent;
    }

    @Override
    public KeyEvent event() {
        return event;
    }
}
