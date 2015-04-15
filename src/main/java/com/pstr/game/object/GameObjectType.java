package com.pstr.game.object;

import com.google.common.collect.Sets;

import java.util.Set;

public enum GameObjectType {

    STARSHIP,
    ENEMY_STARSHIP_L1;

    public static Set<GameObjectType> enemies = Sets.immutableEnumSet(ENEMY_STARSHIP_L1);

}
