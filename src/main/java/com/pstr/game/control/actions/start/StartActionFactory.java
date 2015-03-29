package com.pstr.game.control.actions.start;

import com.pstr.game.control.actions.Action;
import com.pstr.game.control.actions.ActionFactory;
import com.pstr.game.control.actions.ActionPressEvent;

import java.awt.event.KeyEvent;

public class StartActionFactory extends ActionFactory {

    @Override
    public Action create(KeyEvent event, ActionPressEvent pressEvent) {
        return new StartAction(event, pressEvent);
    }
}