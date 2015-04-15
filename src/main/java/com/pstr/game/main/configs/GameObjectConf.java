package com.pstr.game.main.configs;

import com.google.common.collect.ComparisonChain;
import com.pstr.game.object.GameObjectType;

public class GameObjectConf {

    private GameObjectType type;
    private int live;
    private int speed;
    private String image;

    public int getLive() {
        return live;
    }

    public int getSpeed() {
        return speed;
    }

    public String getImage() {
        return image;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this.getClass().isAssignableFrom(obj.getClass())) {
            GameObjectConf conf = (GameObjectConf) obj;
            return ComparisonChain.start()
                    .compare(getType(), conf.getType())
                    .compare(getLive(), conf.getLive())
                    .compare(getSpeed(), conf.getSpeed())
                    .compare(getImage(), conf.getImage()).result() == 0;
         } else return false;
    }

    public GameObjectType getType() {
        return type;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setLive(int live) {
        this.live = live;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setType(GameObjectType type) {
        this.type = type;
    }
}

