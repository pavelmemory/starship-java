package com.pstr.game.control.initializers;

import com.pstr.game.object.GameObject;

import java.util.List;

public interface GameState extends Initializer {

    List<GameObject> getObjects();

    void addObject(GameObject object);

    GameObject getPlayer();
}
