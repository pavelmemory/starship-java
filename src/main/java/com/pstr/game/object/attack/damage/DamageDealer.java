package com.pstr.game.object.attack.damage;

import com.pstr.game.object.GameObject;

public interface DamageDealer extends GameObject {

    boolean damage(Damageable enemy);

}
