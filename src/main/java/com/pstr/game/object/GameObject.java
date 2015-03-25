package com.pstr.game.object;

import com.google.common.base.Predicate;
import com.pstr.game.control.initializers.GameState;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

public interface GameObject {

    final static Point DEATH_POINT = new Point(-100, -100);

    void move(int direction, boolean pressed);

    GameObject fire();

    boolean isAlive();

    void destroy();

    Point center();

    Rectangle scope();

    void update(Graphics2D g);

    GameObjectType type();

    void automateFire(GameState gameState);

    public final Predicate<GameObject> alivePredicate = new Predicate<GameObject>() {

        @Override
        public boolean apply(GameObject object) {
            return object.isAlive();
        }
    };

    public final Predicate<GameObject> destroyedPredicate = new Predicate<GameObject>() {

        @Override
        public boolean apply(GameObject object) {
            return !object.isAlive();
        }
    };
}
