package com.pstr.game.object;

import com.google.common.collect.ImmutableSet;
import com.pstr.game.main.GameConf;
import com.pstr.game.utils.Files;

import java.awt.Point;
import java.util.Set;

public class Starship extends AbstractGameObject implements GameObject {

    private int attackTime = 3;
    private boolean attackState = false;
    public final GameConf gameConf;
    private FireStrategy fireStrategy = new SingleBulletFireStrategy();

    public Starship(GameConf gameConf) {
        super(Files.asBufferedImage(gameConf.game.image),
                new Point(gameConf.game.startX, gameConf.game.startY),
                gameConf.game.speed);
        this.gameConf = gameConf;
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
}
