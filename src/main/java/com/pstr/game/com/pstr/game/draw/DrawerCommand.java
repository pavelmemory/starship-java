package com.pstr.game.com.pstr.game.draw;

import java.awt.event.ActionEvent;

public enum DrawerCommand {

    REDRAW {
        public void action(Drawer drawer) {
            drawer.repaint();
        }
    };

    abstract public void action(Drawer drawer);
}
