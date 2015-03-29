package com.pstr.game.object;

import java.awt.Point;
import java.awt.image.BufferedImage;

public abstract class AbstractGameObject extends VisibleObject {

    public AbstractGameObject(BufferedImage image, Point center, int speed) {
        super(image, center, speed);
    }

    public boolean isAlive() {
        if (scope().y + scope().height < 0) return false;
        return true;
    }

    public void destroy() {
        center(GameObject.DEATH_POINT);
    }
}
