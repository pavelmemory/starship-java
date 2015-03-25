package com.pstr.game.control;

import com.pstr.game.control.initializers.GameState;

public interface Controller {

    void start();

    void stop();

    GameState getState();
}
