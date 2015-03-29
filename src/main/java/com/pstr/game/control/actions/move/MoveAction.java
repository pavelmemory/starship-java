package com.pstr.game.control.actions.move;

import com.pstr.game.control.ControllerCommand;
import com.pstr.game.control.actions.Action;
import com.pstr.game.control.actions.ActionPressEvent;

import java.awt.event.KeyEvent;

public class MoveAction implements Action {

    private final ActionPressEvent pressEvent;
    private final KeyEvent event;

    @Override
    public ControllerCommand getControllerCommand() {
        return ControllerCommand.MOVE;
    }

    public MoveAction(KeyEvent event, ActionPressEvent pressEvent) {
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
