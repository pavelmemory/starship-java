package com.pstr.game.control.actions.weapon;

import com.pstr.game.control.ControllerCommand;
import com.pstr.game.control.actions.Action;
import com.pstr.game.control.actions.ActionPressEvent;

import java.awt.event.KeyEvent;

public class WeaponChangedAction implements Action {

    private final ActionPressEvent pressEvent;
    private final KeyEvent event;

    @Override
    public ControllerCommand getControllerCommand() {
        return ControllerCommand.WEAPON_CHANGED;
    }

    public WeaponChangedAction(KeyEvent event, ActionPressEvent pressEvent) {
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