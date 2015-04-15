package com.pstr.game.object.attack.strategy;

import com.google.common.collect.ImmutableSet;
import com.pstr.game.main.configs.WeaponConfProvider;
import com.pstr.game.object.attack.WeaponType;
import com.pstr.game.object.attack.damage.Ammo;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Set;

public class TripleAmmoFireStrategy extends SingleAmmoFireStrategy {

    public TripleAmmoFireStrategy(WeaponConfProvider provider, WeaponType weaponType) {
        super(provider, weaponType);
    }

    @Override
    public Set<Ammo> fire(Rectangle scope, int direction) {
        Set<Ammo> ammos1 = super.fire(scope, direction);
        Ammo ammo1 = ammos1.iterator().next();
        ammo1.center().x -= 13;
        ammo1.updateDirection(KeyEvent.VK_LEFT, true);

        Set<Ammo> ammos2 = super.fire(scope, direction);
        Ammo ammo2 = ammos2.iterator().next();
        ammo2.center().x += 13;
        ammo2.updateDirection(KeyEvent.VK_RIGHT, true);

        Set<Ammo> ammo3 = super.fire(scope, direction);

        return ImmutableSet.<Ammo>builder().add(ammo1).add(ammo2).addAll(ammo3).build();
    }

    @Override
    public FireStrategy.Type getType() {
        return FireStrategy.Type.TRIPLE;
    }
}
