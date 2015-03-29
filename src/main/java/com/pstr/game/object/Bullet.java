package com.pstr.game.object;

import com.pstr.game.utils.Files;

import java.awt.Point;
import java.util.Set;

public class Bullet extends AbstractGameObject implements GameObject {

    public Bullet(String bullet, Point center, int speed) {
        super(Files.asBufferedImage(bullet), center, speed);
    }

    @Override
    public Set<GameObject> fire() {
        throw new RuntimeException("NOT IMPLEMENTED!!!");
    }

    @Override
    public GameObjectType type() {
        return GameObjectType.BULLET;
    }

    @Override
    public void setWeapon(FireStrategy weapon) {
        throw new RuntimeException("NOT IMPLEMENTED!!!");
    }

    // candidates to another interface
    @Override
    public boolean isAttackState() {
        throw new RuntimeException("NOT IMPLEMENTED!!!");
    }

    @Override
    public void setAttackState(boolean isAttackPerformed) {
        throw new RuntimeException("NOT IMPLEMENTED!!!");
    }
}
