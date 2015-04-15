package com.pstr.game.object.attack.damage;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Sets;
import com.pstr.game.main.configs.WeaponConf;
import com.pstr.game.main.configs.WeaponConfProvider;
import com.pstr.game.object.Visible2DObjectModel;
import com.pstr.game.object.attack.WeaponType;

import java.awt.Point;
import java.util.Set;

public class AmmoBuilder {

    private final WeaponConfProvider provider;
    private Point position;
    private Set<Integer> directions = Sets.newHashSet();
    private Integer speed;
    private Integer damage;
    private String image;
    private WeaponType weaponType;

    public AmmoBuilder(WeaponConfProvider provider) {
        this.provider = provider;
    }

    public AmmoBuilder direction(int direction) {
        directions.add(direction);
        return this;
    }

    public AmmoBuilder speed(Integer speed) {
        this.speed = speed;
        return this;
    }

    public AmmoBuilder damage(Integer points) {
        this.damage = points;
        return this;
    }

    public AmmoBuilder image(String image) {
        this.image = image;
        return this;
    }

    public AmmoBuilder weaponType(WeaponType weaponType) {
        this.weaponType = weaponType;
        return this;
    }

    public AmmoBuilder position(Point position) {
        this.position = position;
        return this;
    }

    public Ammo build() {
        WeaponConf weaponConf = provider.get(weaponType);
        Visible2DObjectModel visible2DObjectModel = Visible2DObjectModel.create(
                MoreObjects.firstNonNull(image, weaponConf.getImage()),
                position,
                MoreObjects.firstNonNull(speed, weaponConf.getSpeed()));
        DefaultAmmo ammo = new DefaultAmmo(visible2DObjectModel, MoreObjects.firstNonNull(damage, weaponConf.getDamage()));
        for (int direction : directions) {
            ammo.updateDirection(direction, true);
        }
        return ammo;
    }
}