package com.pstr.game.object.attack;

import com.pstr.game.object.attack.damage.Ammo;
import com.pstr.game.object.attack.strategy.FireStrategy;

import java.awt.Rectangle;
import java.util.Set;

public interface Attacker {

    Set<Ammo> attack(Rectangle scope, int direction);

    boolean isInAttackState();
    void updateAttackState(boolean isAttackPerformed);

    FireStrategy getStrategy();
    void setStrategy(FireStrategy weapon);

    WeaponType getWeaponType();
    void setWeaponType(WeaponType weaponType);

}
