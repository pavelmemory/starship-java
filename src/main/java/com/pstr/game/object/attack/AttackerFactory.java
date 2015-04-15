package com.pstr.game.object.attack;

import com.pstr.game.main.configs.GameConf;
import com.pstr.game.object.attack.strategy.FireStrategy;
import com.pstr.game.object.attack.strategy.FireStrategyFactory;

public class AttackerFactory {

    public static Attacker create(FireStrategy.Type fireStrategyType, GameConf gameConf, WeaponType weaponType) {
        FireStrategy fireStrategy = FireStrategyFactory.create(fireStrategyType, gameConf, weaponType);
        fireStrategy.setWeaponType(weaponType);
        return new DefaultAttacker(fireStrategy);
    }

}
