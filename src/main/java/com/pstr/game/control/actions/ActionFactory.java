package com.pstr.game.control.actions;

import com.google.common.base.Preconditions;
import com.pstr.game.control.ControllerCommand;

import java.awt.event.KeyEvent;

public class ActionFactory {

    public static Action create(ControllerCommand command, KeyEvent event, ActionPressEvent pressEvent) {
        Preconditions.checkNotNull(command);
        Preconditions.checkNotNull(event);
        Preconditions.checkNotNull(pressEvent);
        return new BaseAction(command, event, pressEvent);
    }

}
