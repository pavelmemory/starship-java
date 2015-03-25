package com.pstr.game.control.actions;

import com.pstr.game.control.Controller;
import com.pstr.game.object.GameObject;

public class AttackCommand implements ActionCommand {

    @Override
    public void actionPress(Controller controller, int pushedButtons) {
        controller.getState().getPlayer().automateFire(controller.getState());
    }

    @Override
    public void actionReleased(Controller controller, int pushedButtons) {
        controller.getState().getPlayer().automateFire(null);
    }

    @Override
    public void actionTyped(Controller controller) {
        GameObject bullet = controller.getState().getPlayer().fire();
        controller.getState().addObject(bullet);
    }
}
