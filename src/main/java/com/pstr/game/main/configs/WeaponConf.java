package com.pstr.game.main.configs;

import com.google.common.collect.ComparisonChain;
import com.pstr.game.object.attack.WeaponType;

public class WeaponConf {
    private int speed;
    private int damage;
    private String image;
    private int attackSpeed;
    private WeaponType type;

    public int getAttackSpeed() {
        return attackSpeed;
    }

    public int getDamage() {
        return damage;
    }

    public int getSpeed() {
        return speed;
    }

    public String getImage() {
        return image;
    }

    public WeaponType getType() {
        return type;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this.getClass().isAssignableFrom(obj.getClass())) {
            WeaponConf conf = (WeaponConf) obj;
            return ComparisonChain.start()
                    .compare(speed, conf.speed)
                    .compare(damage, conf.damage)
                    .compare(image, conf.image)
                    .compare(attackSpeed, conf.attackSpeed).result() == 0;
        } else return false;
    }

    public void setAttackSpeed(int attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setType(WeaponType type) {
        this.type = type;
    }
}
