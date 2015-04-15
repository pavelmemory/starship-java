package com.pstr.game.control.initializers;

import com.pstr.game.control.actions.Action;
import com.pstr.game.object.GameObject;
import com.pstr.game.object.Starship;
import com.pstr.game.object.attack.damage.Ammo;

import java.util.Set;

public interface GameState extends Initializer {

    Set<GameObject> getObjects();

    void addAllAmmo(Iterable<Ammo> ammos);

    void addObject(GameObject object);

    Set<GameObject> getAllGameObjects();

    Set<Starship> getEnemies();

    Set<Ammo> getAmmos();

    void addEnemy(Starship enemy);

    void addAllEnemy(Iterable<Starship> enemies);

    void addAmmo(Ammo ammo);

    void addAllObject(Iterable<GameObject> objects);

    Starship getPlayer();

    void changeBy(Action action);

    boolean isOnAir();

}
