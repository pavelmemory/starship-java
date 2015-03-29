package com.pstr.game.control.actions.none;

import com.pstr.game.control.ControllerCommand;
import com.pstr.game.control.actions.Action;
import com.pstr.game.control.actions.ActionPressEvent;

import java.awt.event.KeyEvent;

public class NoneAction implements Action {

    @Override
    public ControllerCommand getControllerCommand() {
        return ControllerCommand.NONE;
    }

    @Override
    public ActionPressEvent getPressEvent() {
        return null;
    }

    @Override
    public KeyEvent event() {
        return null;
    }
}
