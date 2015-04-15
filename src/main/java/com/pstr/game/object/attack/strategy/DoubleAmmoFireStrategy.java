package com.pstr.game.object.attack.strategy;

import com.google.common.collect.ImmutableSet;
import com.pstr.game.main.configs.WeaponConfProvider;
import com.pstr.game.object.attack.WeaponType;
import com.pstr.game.object.attack.damage.Ammo;

import java.awt.Rectangle;
import java.util.Set;

public class DoubleAmmoFireStrategy extends SingleAmmoFireStrategy {

    public DoubleAmmoFireStrategy(WeaponConfProvider provider, WeaponType weaponType) {
        super(provider, weaponType);
    }

    @Override
    public Set<Ammo> fire(Rectangle scope, int direction) {
        Set<Ammo> ammo1 = super.fire(scope, direction);
        ammo1.iterator().next().center().x -= 13;
        Set<Ammo> ammo2 = super.fire(scope, direction);
        ammo2.iterator().next().center().x += 13;
        return ImmutableSet.<Ammo>builder().addAll(ammo1).addAll(ammo2).build();
    }

    @Override
    public Type getType() {
        return Type.DOUBLE;
    }
}
