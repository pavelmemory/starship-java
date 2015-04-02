package com.pstr.game.object;

import com.pstr.game.main.GameConf;
import com.pstr.game.utils.Files;

import java.awt.image.BufferedImage;

public class EnemyStarship extends Starship {

    public static EnemyStarship create(GameConf gameConf, int x, int y) {
        BufferedImage image = Files.asBufferedImage(gameConf.game.enemy1level.image);
        return new EnemyStarship(image, x, y, gameConf.game.enemy1level.speed, gameConf);
    }

    protected EnemyStarship(BufferedImage image, int x, int y, int speed, GameConf gameConf) {
        super(image, x, y, speed, gameConf);
    }
}
