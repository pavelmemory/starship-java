package com.pstr.game.object;

import com.google.common.collect.ImmutableSet;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.Set;

public class SingleBulletFireStrategy implements FireStrategy {
    @Override
    public Set<GameObject> fire(Starship starship) {
        Point center = starship.center();
        int verticalDelta = starship.scope().height / 2;
        Bullet bullet = new Bullet(starship.gameConf.game.bullet, new Point(center.x, center.y - verticalDelta), starship.gameConf.game.bulletSpeed);
        bullet.setDirection(KeyEvent.VK_UP, true);
        return ImmutableSet.<GameObject>of(bullet);
    }
}
