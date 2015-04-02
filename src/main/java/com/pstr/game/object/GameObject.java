package com.pstr.game.object;

import com.google.common.base.Predicate;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Set;

public interface GameObject {

    final static Point DEATH_POINT = new Point(-100, -100);

    boolean setDirection(int direction, boolean pressed);

    void move();

    void draw(Graphics2D g2d);

    Set<GameObject> fire();

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

    void setWeapon(FireStrategy weapon);

    FireStrategy getWeapon();

    boolean damage(GameObject enemy);

    boolean intersect(GameObject object);

    // return 'true' if destroyed
    boolean damageOn(int damage);
}
