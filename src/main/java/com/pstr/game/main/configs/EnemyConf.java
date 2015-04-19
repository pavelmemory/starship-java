package com.pstr.game.main.configs;

import com.google.common.base.MoreObjects;
import com.pstr.game.object.GameObjectType;

import java.awt.Point;
import java.util.Set;

public class EnemyConf {

    private Point start;
    private GameObjectType type;
    private Set<Integer> direction;

    public void setDirection(Set<Integer> direction) {
        this.direction = direction;
    }

    public void setStart(Point start) {
        this.start = start;
    }

    public void setType(GameObjectType type) {
        this.type = type;
    }

    public GameObjectType getType() {
        return type;
    }

    public Point getStart() {
        return start;
    }

    public Set<Integer> getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("type", type).add("start", start).add("direction", direction).toString();
    }
}
