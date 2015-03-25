package com.pstr.game;

import com.pstr.game.object.GObject;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

/**
 * Created by pstr on 3/18/2015.
 */

public class TestGObject {
    final Point INITIAL_CENTER = new Point(10, 123);
    final int WIDTH = 25;
    final int HIGHT = 40;
    final Point FIRST_MOVE_NEW_CENTER = new Point(30, 20);

    GObject gObject = new GObject(INITIAL_CENTER, WIDTH, HIGHT, 0);

    @Test
    public void gObjectAlwaysHasDefinedCenterPoint() {
        assertNotNull(gObject.center());
        assertEquals(INITIAL_CENTER, gObject.center());
    }

    @Test
    public void gObjectCanChangeCenter() {
        Point oldCenter = gObject.center(FIRST_MOVE_NEW_CENTER);
        assertNotNull(gObject.center());
        assertEquals(INITIAL_CENTER, oldCenter);
        assertEquals(FIRST_MOVE_NEW_CENTER, gObject.center());
    }

    @Test
    public void gObjectAlwaysHasDefinedScope() {
        assertNotNull(gObject.scope());
        Point center = gObject.center();
        assertEquals(new Rectangle(center.x - WIDTH / 2, center.y - HIGHT / 2, WIDTH, HIGHT), gObject.scope());
    }

    @Test
    public void intersectWithOtherObjects() {
        Point point = new Point(gObject.center().x + 1, gObject.center().y + 1);
        assertTrue(gObject.intersect(point));
        Rectangle rectangle = new Rectangle(point, new Dimension(10, 20));
        assertTrue(gObject.intersect(rectangle));
        GObject object = new GObject(point, 10, 102, 0);
        assertTrue(gObject.intersect(object));
        assertFalse(gObject.intersect(new Point(-10, 30)));
        Object nul = null;
        assertFalse(gObject.intersect((Point) nul));
        assertFalse(gObject.intersect((Rectangle) nul));
        assertFalse(gObject.intersect((GObject) nul));
    }

    @Test
    public void scopeIsProperlyCalculated() {
        Point initial = new Point(10, 10);
        GObject object = new GObject(initial, 5, 5, 0);
        assertEquals(new Rectangle(8, 8, 5, 5), object.scope());
        assertEquals(initial, object.center(new Point(4, 9)));
        assertEquals(new Rectangle(2, 7, 5, 5), object.scope());
    }


}
