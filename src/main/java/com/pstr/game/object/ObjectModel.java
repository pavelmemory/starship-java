package com.pstr.game.object;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Set;

import static java.awt.event.KeyEvent.*;

public class ObjectModel {
    private static final Logger LOG = LoggerFactory.getLogger(ObjectModel.class);

    private Point center;
    private int width;
    private int height;
    private Rectangle scope;
    protected int speed;
    private final static Set<Integer> MOVE_KEY_CODES = ImmutableSet.of(VK_UP, VK_DOWN, VK_LEFT, VK_RIGHT);
    private final Set<Integer> direction;

    public ObjectModel(Point center, int width, int height, int speed) {
        center(center);
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.direction = Sets.newHashSetWithExpectedSize(2);
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

    public boolean intersect(ObjectModel object) {
        return object != null && scope().intersects(object.scope());
    }

    public void setDirection(int direction, boolean pressed) {
        if (!MOVE_KEY_CODES.contains(direction)) throw new IllegalArgumentException("Does not support specified move direction: " + direction);
        if (pressed) {
            this.direction.add(direction);
        } else {
            this.direction.remove(direction);
        }
    }

    public void move() {
        scope = null;
//        LOG.info("Object setDirection: " + Joiner.on(",").join(direction));
        for (Integer mDirection : direction) {
            completeDirectionPositionChange(mDirection, direction.size() > 1);
        }
    }

    private void completeDirectionPositionChange(int direction, boolean multiDirection) {
        int speed = multiDirection ? (int) (this.speed / 3.0 * 2) : this.speed;
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
                LOG.error("Unknown direction: [" + direction + "]");
        }
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).omitNullValues()
                .add("center", center())
                .add("scope", scope())
                .add("width", width)
                .add("height", height)
                .add("speed", speed)
                .add("direction", direction).toString();
    }
}
