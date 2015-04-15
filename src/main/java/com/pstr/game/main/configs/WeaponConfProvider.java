package com.pstr.game.main.configs;

import com.pstr.game.object.attack.WeaponType;

import java.util.List;

public interface WeaponConfProvider {

    WeaponConf get(WeaponType weaponType);

    List<WeaponConf> getAll();

}
