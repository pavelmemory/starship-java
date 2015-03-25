package com.pstr.game.control.actions;

import com.pstr.game.control.Controller;

import java.util.Set;

/**
 * Created by pstr on 3/22/2015.
 */
public interface ActionCommand {

    void actionPress(Controller controller, int pushedButtons);
    void actionReleased(Controller controller, int pushedButtons);
    void actionTyped(Controller controller);

}
