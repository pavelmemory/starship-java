package com.pstr.game.object;

import com.google.common.collect.ImmutableSet;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.Set;

public class DoubleBulletFireStategy implements FireStrategy {
    @Override
    public Set<GameObject> fire(Starship starship) {
        Point center = starship.center();
        int verticalDelta = starship.scope().height / 2;
        Bullet bullet1 = new Bullet(starship.gameConf.game.bullet, new Point(center.x - 10, center.y - verticalDelta), starship.gameConf.game.bulletSpeed);
        bullet1.setDirection(KeyEvent.VK_UP, true);
        Bullet bullet2 = new Bullet(starship.gameConf.game.bullet, new Point(center.x + 10, center.y - verticalDelta), starship.gameConf.game.bulletSpeed);
        bullet2.setDirection(KeyEvent.VK_UP, true);
        return ImmutableSet.<GameObject>of(bullet1, bullet2);
    }
}
