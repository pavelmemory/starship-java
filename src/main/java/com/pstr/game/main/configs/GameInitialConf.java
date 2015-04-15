package com.pstr.game.main.configs;

import java.util.List;

public class GameInitialConf {

    private int startX;
    private int startY;
    private List<GameObjectConf> objects;
    private List<WeaponConf> weapons;

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public List<GameObjectConf> getObjects() {
        return objects;
    }

    public List<WeaponConf> getWeapons() {
        return weapons;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public void setObjects(List<GameObjectConf> objects) {
        this.objects = objects;
    }

    public void setWeapons(List<WeaponConf> weapons) {
        this.weapons = weapons;
    }
}
