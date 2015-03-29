package com.pstr.game.control.actions.none;

import com.pstr.game.control.actions.Action;
import com.pstr.game.control.actions.ActionFactory;
import com.pstr.game.control.actions.ActionPressEvent;

import java.awt.event.KeyEvent;

public class NoneActionFactory extends ActionFactory {

    public static NoneAction NONE_ACTION = new NoneAction();

    @Override
    public Action create(KeyEvent event, ActionPressEvent pressEvent) {
        return NONE_ACTION;
    }
}
