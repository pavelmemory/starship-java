package com.pstr.game.object;

import com.pstr.game.object.attack.Attacker;
import com.pstr.game.object.attack.damage.Damageable;

public interface Starship extends GameObject, Attacker, Damageable {

    void setAttacker(Attacker attacker);


}
