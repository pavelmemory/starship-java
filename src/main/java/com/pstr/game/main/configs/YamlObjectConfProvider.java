package com.pstr.game.main.configs;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.pstr.game.object.GameObjectType;

import java.util.List;
import java.util.Map;

public class YamlObjectConfProvider implements ObjectConfProvider {

    private Map<GameObjectType, GameObjectConf> confs = Maps.newEnumMap(GameObjectType.class);

    public YamlObjectConfProvider(GameConf gameConf) {
        List<GameObjectConf> objects = gameConf.getGameInitialConf().getObjects();
        for (GameObjectConf conf : objects) {
            confs.put(conf.getType(), conf);
        }
    }

    @Override
    public GameObjectConf get(GameObjectType type) {
        return confs.get(type);
    }

    @Override
    public List<GameObjectConf> getAll() {
        return ImmutableList.copyOf(confs.values());
    }
}
