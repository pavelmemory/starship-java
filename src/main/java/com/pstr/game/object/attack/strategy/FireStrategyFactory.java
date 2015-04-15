package com.pstr.game.object.attack.strategy;

import com.pstr.game.main.configs.GameConf;
import com.pstr.game.main.configs.WeaponConfProvider;
import com.pstr.game.main.configs.YamlWeaponConfProvider;
import com.pstr.game.object.attack.WeaponType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FireStrategyFactory {

    private static final Logger LOG = LoggerFactory.getLogger(FireStrategyFactory.class);

    public static FireStrategy create(FireStrategy.Type type, GameConf gameConf, WeaponType weaponType) {
        LOG.info("Fire strategy creation: " + type);
        WeaponConfProvider provider = new YamlWeaponConfProvider(gameConf);
        FireStrategy strategy;
        switch (type) {
            case SINGLE:
                strategy = new SingleAmmoFireStrategy(provider, weaponType);
                break;
            case DOUBLE:
                strategy = new DoubleAmmoFireStrategy(provider, weaponType);
                break;
            case TRIPLE:
                strategy = new TripleAmmoFireStrategy(provider, weaponType);
                break;
            default:
                strategy = new SingleAmmoFireStrategy(provider, weaponType);
        }
        LOG.info("Fire strategy created: " + strategy);
        return strategy;
    }

}
