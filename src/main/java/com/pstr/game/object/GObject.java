package com.pstr.game.object;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Set;

import static java.awt.event.KeyEvent.*;

public class GObject {
    private static final Logger LOG = LoggerFactory.getLogger(GObject.class);

    private Point center;
    private int width;
    private int height;
    private Rectangle scope;
    private int speed;
    private final static Set<Integer> MOVE_KEY_CODES = ImmutableSet.of(VK_UP, VK_DOWN, VK_LEFT, VK_RIGHT);
    private final Set<Integer> direction;

    public GObject(Point center, int width, int height, int speed) {
        center(center);
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.direction = Sets.newHashSet();
    }

    public Point center() {
        return center;
    }

    public Point center(Point point) {
        Preconditions.checkNotNull(point, "Not properly initialized");
        Point oldCenter = center();
        center = point;
        scope = null;
        return oldCenter;
    }

    public Rectangle scope() {
        if (scope == null) {
            scope = new Rectangle(center().x - width / 2, center().y - height / 2, width, height);
        }
        return scope;
    }

    public boolean intersect(Point point) {
        return point != null && scope().contains(point);
    }

    public boolean intersect(Rectangle rectangle) {
        return rectangle != null && scope().intersects(rectangle);
    }

    public boolean intersect(GObject object) {
        return object != null && scope().intersects(object.scope());
    }

    public boolean direction(int direction, boolean pressed) {
        if (MOVE_KEY_CODES.contains(direction)) {
            if (pressed) {
                this.direction.add(direction);
            } else {
                this.direction.remove(direction);
            }
        }
        return !this.direction.isEmpty();
    }

    public void move() {
        scope = null;
        LOG.info("Object move: " + Joiner.on(",").join(direction));
        if (direction.size() == 2) {
            for (Integer mDirection : direction) {
                completeDirectionPositionChange(mDirection, true);
            }
        } else if (direction.size() == 1) {
            completeDirectionPositionChange(direction.iterator().next(), false);
        }
    }

    private GObject completeDirectionPositionChange(int direction, boolean multiDirection) {
        int speed = multiDirection ? (int) (this.speed / 3.0 * 2): this.speed;
        switch (direction) {
            case VK_UP:
                center().y = center().y - speed;
                break;
            case VK_DOWN:
                center().y = center().y + speed;
                break;
            case VK_LEFT:
                center().x = center().x - speed;
                break;
            case VK_RIGHT:
                center().x = center().x + speed;
                break;
            default:
                LOG.error("Unknown move direction [" + direction + "]");
        }
        return this;
    }
}
