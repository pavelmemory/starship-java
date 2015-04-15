package com.pstr.game.main.configs;

import com.pstr.game.object.GameObjectType;

import java.util.List;

public interface ObjectConfProvider {

    GameObjectConf get(GameObjectType type);

    List<GameObjectConf> getAll();
}
