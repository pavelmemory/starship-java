package com.pstr.game.control.actions.weapon;

import com.pstr.game.control.actions.Action;
import com.pstr.game.control.actions.ActionFactory;
import com.pstr.game.control.actions.ActionPressEvent;

import java.awt.event.KeyEvent;

public class WeaponChangedActionFactory extends ActionFactory {

    @Override
    public Action create(KeyEvent event, ActionPressEvent pressEvent) {
        return new WeaponChangedAction(event, pressEvent);
    }
}
