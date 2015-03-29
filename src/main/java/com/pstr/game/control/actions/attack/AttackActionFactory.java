package com.pstr.game.control.actions.attack;

import com.pstr.game.control.actions.Action;
import com.pstr.game.control.actions.ActionFactory;
import com.pstr.game.control.actions.ActionPressEvent;

import java.awt.event.KeyEvent;

public class AttackActionFactory extends ActionFactory {

    @Override
    public Action create(KeyEvent event, ActionPressEvent pressEvent) {
        return new AttackAction(event, pressEvent);
    }
}
