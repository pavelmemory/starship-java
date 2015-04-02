package com.pstr.game.control.initializers;

import com.pstr.game.control.actions.Action;
import com.pstr.game.object.GameObject;

import java.util.Set;

public interface GameState extends Initializer {

    Set<GameObject> getObjects();

    void addObject(GameObject object);

    void addObjects(Set<GameObject> objects);

    GameObject getPlayer();

    void changeBy(Action action);

    boolean isOnAir();

    Set<? extends GameObject> getObjects(Class<? extends GameObject> bulletClass);
}
