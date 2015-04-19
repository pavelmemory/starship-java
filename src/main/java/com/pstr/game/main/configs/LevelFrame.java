package com.pstr.game.main.configs;

import com.google.common.base.MoreObjects;

import java.util.List;

public class LevelFrame {

    private List<EnemyConf> enemiesConf;
    private int sleep;

    public int getSleep() {
        return sleep;
    }

    public void setSleep(int sleep) {
        this.sleep = sleep;
    }

    public List<EnemyConf> getEnemiesConf() {
        return enemiesConf;
    }

    public void setEnemiesConf(List<EnemyConf> enemiesConf) {
        this.enemiesConf = enemiesConf;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("sleep", sleep).add("enemiesConf", enemiesConf).toString();
    }
}
