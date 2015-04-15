package com.pstr.game;

import com.pstr.game.object.ObjectModel;
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

    ObjectModel objectModel = new ObjectModel(INITIAL_CENTER, WIDTH, HIGHT, 0);

    @Test
    public void gObjectAlwaysHasDefinedCenterPoint() {
        assertNotNull(objectModel.center());
        assertEquals(INITIAL_CENTER, objectModel.center());
    }

    @Test
    public void gObjectCanChangeCenter() {
        Point oldCenter = objectModel.center(FIRST_MOVE_NEW_CENTER);
        assertNotNull(objectModel.center());
        assertEquals(INITIAL_CENTER, oldCenter);
        assertEquals(FIRST_MOVE_NEW_CENTER, objectModel.center());
    }

    @Test
    public void gObjectAlwaysHasDefinedScope() {
        assertNotNull(objectModel.scope());
        Point center = objectModel.center();
        assertEquals(new Rectangle(center.x - WIDTH / 2, center.y - HIGHT / 2, WIDTH, HIGHT), objectModel.scope());
    }

    @Test
    public void intersectWithOtherObjects() {
        Point point = new Point(objectModel.center().x + 1, objectModel.center().y + 1);
        assertTrue(objectModel.intersect(point));
        Rectangle rectangle = new Rectangle(point, new Dimension(10, 20));
        assertTrue(objectModel.intersect(rectangle));
        ObjectModel object = new ObjectModel(point, 10, 102, 0);
        assertTrue(objectModel.intersect(object));
        assertFalse(objectModel.intersect(new Point(-10, 30)));
        Object nul = null;
        assertFalse(objectModel.intersect((Point) nul));
        assertFalse(objectModel.intersect((Rectangle) nul));
        assertFalse(objectModel.intersect((ObjectModel) nul));
    }

    @Test
    public void scopeIsProperlyCalculated() {
        Point initial = new Point(10, 10);
        ObjectModel object = new ObjectModel(initial, 5, 5, 0);
        assertEquals(new Rectangle(8, 8, 5, 5), object.scope());
        assertEquals(initial, object.center(new Point(4, 9)));
        assertEquals(new Rectangle(2, 7, 5, 5), object.scope());
    }


}
