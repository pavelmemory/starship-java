package com.pstr.game.object;

import com.pstr.game.object.attack.WeaponType;
import com.pstr.game.object.attack.strategy.FireStrategy;

import java.awt.Point;
import java.util.Set;

public interface Builder {

    Builder position(Point point);

    Builder direction(int to);

    Builder direction(Set<Integer> to);

    Builder speed(Integer speed);

    Builder live(Integer points);

    Builder image(String image);

    StarshipBuilder fireStrategyType(FireStrategy.Type strategyType);

    StarshipBuilder weaponType(WeaponType weaponType);

    Builder gameObjectConfType(GameObjectType objectType);

    <E> E build();
}
