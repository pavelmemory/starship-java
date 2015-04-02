package com.pstr.game.object;

import com.google.common.base.Preconditions;
import com.pstr.game.utils.Files;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Set;

public class Bullet extends AbstractGameObject implements GameObject {

    private final int damage;

    private Bullet(BufferedImage image, Point center, int speed, int damage) {
        super(image, center, speed);
        this.damage = damage;
    }

    public static Bullet create(String image, Point center, int speed, int damage) {
        BufferedImage bImage = Files.asBufferedImage(image);
        return new Bullet(bImage, center, speed, damage);
    }

    @Override
    public GameObjectType type() {
        return GameObjectType.BULLET;
    }

    // candidates to another interface
    @Override
    public boolean isAttackState() {
        throw new RuntimeException("NOT IMPLEMENTED!!!");
    }

    @Override
    public void setWeapon(FireStrategy weapon) {
        throw new RuntimeException("NOT IMPLEMENTED!!!");
    }

    @Override
    public FireStrategy getWeapon() {
        return null;
    }

    // return 'true' if target destroyed
    @Override
    public boolean damage(GameObject target) {
        if (intersect(target)) {
            destroy();
            target.damageOn(damage);
            return true;
        }
        return false;
    }

    @Override
    public boolean intersect(GameObject object) {
        return super.intersect(object.scope());
    }

    @Override
    public boolean damageOn(int damage) {
        Preconditions.checkArgument(damage > 0);
        destroy();
        return true;
    }

    @Override
    public Set<GameObject> fire() {
        throw new RuntimeException("NOT IMPLEMENTED!!!");
    }

    @Override
    public void setAttackState(boolean isAttackPerformed) {
        throw new RuntimeException("NOT IMPLEMENTED!!!");
    }
}
