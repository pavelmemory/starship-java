package com.pstr.game.object;

import com.pstr.game.control.initializers.GameState;
import com.pstr.game.main.GameConf;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Starship implements GameObject {

    private VisibleObject visibleObject;
    private boolean isInMove = false;
    private GameState gameState = null;
    private int attackTime = 3;

    public Starship(GameConf gameConf) {
        visibleObject = VisibleObject.create(
                gameConf.game.image,
                new Point(gameConf.game.startX, gameConf.game.startY),
                gameConf.game.speed);
    }

    @Override
    public void move(int direction, boolean pressed) {
        isInMove = visibleObject.direction(direction, pressed);
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
    public void update(Graphics2D g) {
        visibleObject.draw(g, isInMove);
        if (gameState != null && isAttackTime()) {
            gameState.addObject(fire());
        }
    }

    @Override
    public GameObjectType type() {
        return GameObjectType.PLAYER;
    }

    @Override
    public void automateFire(GameState gameState) {
        this.gameState = gameState;
    }

    public boolean isAttackTime() {
        if (attackTime == 0) {
            attackTime = 3;
            return true;
        }
        else {
            attackTime -= 1;
            return false;
        }
    }
}
