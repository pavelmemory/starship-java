package com.pstr.game.control.actions;

import com.pstr.game.control.Controller;

/**
 * Created by pstr on 3/22/2015.
 */
public class StopGameCommand implements ActionCommand {

    @Override
    public void actionPress(Controller controller, int pushedButtons) {
        controller.stop();
    }

    @Override
    public void actionReleased(Controller controller, int pushedButtons) {

    }

    @Override
    public void actionTyped(Controller controller) {

    }
}
