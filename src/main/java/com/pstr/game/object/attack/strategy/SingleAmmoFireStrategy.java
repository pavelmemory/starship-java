package com.pstr.game.object.attack.strategy;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.pstr.game.main.configs.WeaponConfProvider;
import com.pstr.game.object.attack.WeaponType;
import com.pstr.game.object.attack.damage.Ammo;
import com.pstr.game.object.attack.damage.AmmoBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Set;

public class SingleAmmoFireStrategy implements FireStrategy {

    private static final Logger LOG = LoggerFactory.getLogger(SingleAmmoFireStrategy.class);
    private final WeaponConfProvider provider;
    private WeaponType weaponType;
    private Integer damage = null;

    public SingleAmmoFireStrategy(WeaponConfProvider provider, WeaponType weaponType) {
        this.weaponType = weaponType;
        this.provider = provider;
    }

    public Set<Ammo> fire(Rectangle scope, int direction) {
        Preconditions.checkNotNull(scope);
        Preconditions.checkArgument(scope.x > 0);
        Preconditions.checkArgument(scope.y > 0);
        Preconditions.checkState(getWeaponType() != null, "Weapon type not specified!");

        int x = scope.x + scope.width / 2;
        int y = scope.y;
        Ammo object = new AmmoBuilder(provider)
                .weaponType(getWeaponType())
                .damage(damage)
                .direction(direction)
                .position(new Point(x, y)).build();
        object.center(new Point(x, direction == KeyEvent.VK_UP ? scope.y - object.scope().height - 2 : scope.y + scope.height + 5));
                LOG.info("New DamageDealer[Bullet] created: " + object);
        return ImmutableSet.of(object);
    }

    @Override
    public int getAttackSpeed() {
        return provider.get(weaponType).getAttackSpeed();
    }

    public void setWeaponType(WeaponType weaponType) {
        this.weaponType = weaponType;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    @Override
    public void setDamage(int damage) {
        this.damage = damage;
    }

    public FireStrategy.Type getType() {
        return FireStrategy.Type.SINGLE;
    }
}
