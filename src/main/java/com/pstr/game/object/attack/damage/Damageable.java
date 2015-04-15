package com.pstr.game.object.attack.damage;

import java.awt.Rectangle;

public interface Damageable {

    void damageOn(int damageAmount);

    Rectangle scope();

}
