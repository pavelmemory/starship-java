package com.pstr.game.main.configs;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.pstr.game.object.attack.WeaponType;

import java.util.List;
import java.util.Map;

public class YamlWeaponConfProvider implements WeaponConfProvider {

    private final Map<WeaponType, WeaponConf> weaponConfs = Maps.newEnumMap(WeaponType.class);

    public YamlWeaponConfProvider(GameConf gameConf) {
        for (WeaponConf weaponConf : gameConf.getGameInitialConf().getWeapons()) {
            weaponConfs.put(weaponConf.getType(), weaponConf);
        }
    }

    @Override
    public WeaponConf get(WeaponType weaponType) {
        return Preconditions.checkNotNull(weaponConfs.get(weaponType),
                "Configuration for weapon type '%s' is absent in game.yaml file", weaponType);
    }

    @Override
    public List<WeaponConf> getAll() {
        return ImmutableList.copyOf(weaponConfs.values().iterator());
    }
}
