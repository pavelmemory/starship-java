package com.pstr.game.object.attack.damage;

import com.pstr.game.object.GameObject;
import com.pstr.game.object.Visible2DObjectModel;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

public class DefaultAmmo implements Ammo {

    private int damagePoints;
    private boolean live;
    private Visible2DObjectModel visible2DObjectModel;

    public DefaultAmmo(Visible2DObjectModel visible2DObjectModel, int damagePoints) {
        this.visible2DObjectModel = visible2DObjectModel;
        this.damagePoints = damagePoints;
        this.live = true;
    }

    @Override
    public boolean damage(Damageable target) {
        if (visible2DObjectModel.intersect(target.scope())) {
            destroy();
            target.damageOn(damagePoints);
            return true;
        }
        return false;
    }


    @Override
    public void draw(Graphics2D g2d) {
        visible2DObjectModel.draw(g2d);
    }

    @Override
    public void updateDirection(int direction, boolean pressed) {
        visible2DObjectModel.setDirection(direction, pressed);
    }

    @Override
    public void move() {
        visible2DObjectModel.move();
    }

    @Override
    public boolean isAlive() {
        return live;
    }

    @Override
    public void destroy() {
        live = false;
    }

    @Override
    public Point center() {
        return visible2DObjectModel.center();
    }

    @Override
    public Rectangle scope() {
        return visible2DObjectModel.scope();
    }

    @Override
    public boolean intersect(GameObject object) {
        return visible2DObjectModel.intersect(object.scope());
    }
}
