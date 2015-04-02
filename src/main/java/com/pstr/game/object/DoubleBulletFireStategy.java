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
        Bullet bullet1 = Bullet.create(starship.getGameConf().game.player.bullet,
                new Point(center.x - 10, center.y - verticalDelta),
                starship.getGameConf().game.player.bulletSpeed, starship.getGameConf().game.player.damage);
        bullet1.setDirection(KeyEvent.VK_UP, true);
        Bullet bullet2 = Bullet.create(starship.getGameConf().game.player.bullet,
                new Point(center.x + 10, center.y - verticalDelta),
                starship.getGameConf().game.player.bulletSpeed, starship.getGameConf().game.player.damage);
        bullet2.setDirection(KeyEvent.VK_UP, true);
        return ImmutableSet.<GameObject>of(bullet1, bullet2);
    }
}
