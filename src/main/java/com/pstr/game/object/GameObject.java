package com.pstr.game.object;

import com.google.common.base.Predicate;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

public interface GameObject {

    final static Point DEATH_POINT = new Point(-100, -100);

    void move(int direction, boolean pressed);

    void move();

    void draw(Graphics2D g2d);

    GameObject fire();

    boolean isAttackState();

    void setAttackState(boolean isAttackPerformed);

    boolean isAlive();

    void destroy();

    Point center();

    Rectangle scope();

    GameObjectType type();

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
