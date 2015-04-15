package com.pstr.game.object.attack;

import com.google.common.collect.ImmutableSet;
import com.pstr.game.object.attack.damage.Ammo;
import com.pstr.game.object.attack.strategy.FireStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Rectangle;
import java.util.Set;

public class DefaultAttacker implements Attacker {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultAttacker.class);

    private int attackTime = 0;
    private FireStrategy strategy;
    private boolean attackState = false;

    public DefaultAttacker(FireStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public Set<Ammo> attack(Rectangle scope, int direction) {
//        LOG.info("Provided new attack from point: " + from);
        if (attackState) {
            if (attackTime <= 0) {
                attackTime = getStrategy().getAttackSpeed();
                return getStrategy().fire(scope, direction);
            } else {
                --attackTime;
            }
        } else {
            attackTime = getStrategy().getAttackSpeed();
        }
        return ImmutableSet.of();
    }

    @Override
    public boolean isInAttackState() {
        return attackState;
    }

    @Override
    public void updateAttackState(boolean isAttackPerformed) {
        this.attackState = isAttackPerformed;
    }

    @Override
    public void setStrategy(FireStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public FireStrategy getStrategy() {
        return strategy;
    }

    @Override
    public WeaponType getWeaponType() {
        return getStrategy().getWeaponType();
    }

    @Override
    public void setWeaponType(WeaponType weaponType) {
        getStrategy().setWeaponType(weaponType);
    }

}
