package com.pstr.game.object;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

public interface GameObject {

    void draw(Graphics2D g2d);

    void updateDirection(int direction, boolean pressed);

    void move();

    boolean isAlive();

    void destroy();

    Point center();

    Rectangle scope();

    boolean intersect(GameObject object);
}
