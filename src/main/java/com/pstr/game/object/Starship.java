package com.pstr.game.object;

import com.pstr.game.main.GameConf;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Starship implements GameObject {

    private VisibleObject visibleObject;
    private int attackTime = 3;
    private boolean attackState = false;

    public Starship(GameConf gameConf) {
        visibleObject = VisibleObject.create(
                gameConf.game.image,
                new Point(gameConf.game.startX, gameConf.game.startY),
                gameConf.game.speed);
    }

    @Override
    public void move(int direction, boolean pressed) {
        visibleObject.direction(direction, pressed);
    }

    public void move() {
        visibleObject.move();
    }

    @Override
    public void draw(Graphics2D g2d) {
        visibleObject.draw(g2d);
    }

    @Override
    public GameObject fire() {
        Point center = visibleObject.center();
        int verticalDelta = visibleObject.scope().height / 2;
        Bullet bullet = new Bullet(new Point(center.x, center.y - verticalDelta), 5);
        bullet.move(KeyEvent.VK_UP, true);
        return bullet;
    }

    @Override
    public boolean isAlive() {
        return false;
    }

    @Override
    public void destroy() {

    }

    @Override
    public Point center() {
        return null;
    }

    @Override
    public Rectangle scope() {
        return null;
    }

    @Override
    public GameObjectType type() {
        return GameObjectType.PLAYER;
    }

    public boolean isAttackState() {
        if (attackState && attackTime == 0) {
            attackTime = 3;
            return true;
        }
        else {
            attackTime -= 1;
            return false;
        }
    }

    public void setAttackState(boolean isAttackPerformed) {
        attackState = isAttackPerformed;
    }
}
