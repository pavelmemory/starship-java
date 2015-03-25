package com.pstr.game.object;

import com.pstr.game.control.initializers.GameState;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

public class Bullet implements GameObject {

    private VisibleObject visibleObject;

    public Bullet(Point center, int speed) {
        visibleObject = VisibleObject.create("starship/bullet.png", center, speed);
    }

    @Override
    public void move(int direction, boolean pressed) {
        visibleObject.direction(direction, pressed);
    }

    @Override
    public GameObject fire() {
        throw new IllegalStateException("Bullet object can not to do fire action");
    }

    @Override
    public boolean isAlive() {
        if (scope().y + scope().height < 0) return false;
        return true;
    }

    @Override
    public void destroy() {
        visibleObject.center(GameObject.DEATH_POINT);
    }

    @Override
    public Point center() {
        return visibleObject.center();
    }

    @Override
    public Rectangle scope() {
        return visibleObject.scope();
    }

    @Override
    public void update(Graphics2D g) {
        visibleObject.draw(g, true);
    }

    @Override
    public GameObjectType type() {
        return GameObjectType.BULLET;
    }

    @Override
    public void automateFire(GameState gameState) {
        throw new IllegalStateException("Bullet can't to fire, even automate!");
    }
}
