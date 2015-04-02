package com.pstr.game.object;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.pstr.game.main.GameConf;
import com.pstr.game.utils.Files;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Set;

public class Starship extends AbstractGameObject implements GameObject {

    private final GameConf gameConf;
    private int attackTime = 3;
    private boolean attackState = false;
    private int life = 0;

    private FireStrategy fireStrategy = new SingleBulletFireStrategy();

    public static Starship create(GameConf gameConf) {
        BufferedImage image = Files.asBufferedImage(gameConf.game.player.image);
        return new Starship(image, gameConf.game.startX, gameConf.game.startY, gameConf.game.player.speed, gameConf);
    }

    protected Starship(BufferedImage image, int x, int y, int speed, GameConf gameConf) {
        super(image, new Point(x, y), speed);
        this.gameConf = gameConf;
        this.life = gameConf.game.player.life;
    }

    @Override
    public Set<GameObject> fire() {
        if (attackState) {
            if (attackTime <= 0) {
                attackTime = 3;
                return fireStrategy.fire(this);
            } else {
                --attackTime;
            }
        } else {
            attackTime = 3;
        }
        return ImmutableSet.of();
    }

    @Override
    public GameObjectType type() {
        return GameObjectType.PLAYER;
    }

    @Override
    public void setWeapon(FireStrategy weapon) {
        fireStrategy = weapon;
    }

    public boolean isAttackState() {
        return attackState;
    }

    public void setAttackState(boolean isAttackPerformed) {
        attackState = isAttackPerformed;
    }

    public GameConf getGameConf() {
        return gameConf;
    }

    @Override
    public FireStrategy getWeapon() {
        return fireStrategy;
    }

    @Override
    public boolean damage(GameObject enemy) {
        return false;
    }

    @Override
    public boolean intersect(GameObject object) {
        return super.intersect(object.scope());
    }

    @Override
    public boolean damageOn(int damage) {
        Preconditions.checkArgument(damage > 0);
        life -= damage;
        if (life <= 0) {
            destroy();
            return true;
        }
        return false;
    }
}
