package com.pstr.game.control.actions;

import com.pstr.game.control.Controller;

public class MoveCommand implements ActionCommand {

    @Override
    public void actionPress(Controller controller, int pushedButton) {
        controller.getState().getPlayer().move(pushedButton, true);
    }

    @Override
    public void actionReleased(Controller controller, int pushedButton) {
        controller.getState().getPlayer().move(pushedButton, false);
    }

    @Override
    public void actionTyped(Controller controller) {

    }
}
